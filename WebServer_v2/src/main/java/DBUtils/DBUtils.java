package DBUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

public class DBUtils {
	//数据库地址,mysql用户名,密码,驱动
	private static String url,username,password,driver;
	
	//连接池
	private static BasicDataSource bds = null;
	
	//连接池大小,最大活动数
	private static int initialSize,maxActive;
	
	static{
		init();
	}
	
	/** 
	 * 参数初始化 
	 */
	private static void init(){
		Properties conf = new Properties();
		try {
			InputStream in = new FileInputStream("conf/db.properties");
			conf.load(in);

			url = conf.getProperty("jdbc.url");
			username = conf.getProperty("jdbc.username");
			password = conf.getProperty("jdbc.password");
			driver = conf.getProperty("jdbc.driver");
			
			initialSize = Integer.parseInt(conf.getProperty("initialSize"));
			maxActive = Integer.parseInt(conf.getProperty("maxActive"));
			
			bds = new BasicDataSource();
			bds.setUrl(url);
			bds.setUsername(username);
			bds.setPassword(password);
			bds.setDriverClassName(driver);
			bds.setInitialSize(initialSize);
			bds.setMaxActive(maxActive);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取一个数据库连接对象
	 * @return
	 */
	public static Connection getConnection(){
		Connection conn = null;
		try{
			conn = bds.getConnection();
		}catch(Exception e){
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 关闭数据库连接对象
	 */
	public static void closeConnection(Connection conn)	{
		try {
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Connection conn = getConnection();
		System.out.println(conn);
	}
}
