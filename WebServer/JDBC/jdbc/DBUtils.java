package jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

//导入数据库连接池可以导入commons-dbcp包(包含dbcp+pool)或者分别导入dbcp和pool包
import org.apache.commons.dbcp.BasicDataSource;

public class DBUtils {
	//数据库引擎/用户名/密码/地址
	public static String driver,username,password,url;
	
	//BasicDataSource配置参数
	public static int initialSize,maxActive;
	
	//类似线程池的数据库连接池
	public static BasicDataSource ds;
	static {
		try {
			//Properties将文件内固定格式转为String键值对
			Properties conf = new Properties();
			//获取上级目录中db.properties文件输入流
			InputStream in = DBUtils.class.getClassLoader().getResourceAsStream("db.properties");
			//读取
			conf.load(in);
			//为静态变量/配置参数赋值
			driver = conf.getProperty("jdbc.driver");
			username = conf.getProperty("jdbc.username");
			password = conf.getProperty("jdbc.password");
			url = conf.getProperty("jdbc.url");
			initialSize = Integer.parseInt(conf.getProperty("initialSize"));
			maxActive =Integer.parseInt(conf.getProperty("maxActive"));
			//创建数据库连接池并设定参数
			ds = new BasicDataSource();
			ds.setDriverClassName(driver);
			ds.setUsername(username);
			ds.setPassword(password);
			ds.setUrl(url);
			ds.setInitialSize(initialSize);
			ds.setMaxActive(maxActive);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//获取数据库连接
	public static Connection getConnection(){
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	//关闭数据库连接
	public static void closeConnection(Connection conn){
		try {
			if(conn!=null){
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
