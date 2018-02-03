
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import jdbc.DBUtils1;

public class DBUtils {
	public static String driver,username,password,url;
	
	public static int initialSize,maxActive;
	static {
		try {
			Properties conf = new Properties();
			InputStream in = DBUtils.class.getClassLoader().getResourceAsStream("db.properties");
			conf.load(in);
			driver = conf.getProperty("jdbc.driver");
			username = conf.getProperty("jdbc.username");
			password = conf.getProperty("jdbc.password");
			url = conf.getProperty("jdbc.url");
			initialSize = Integer.parseInt(conf.getProperty("initialSize"));
			maxActive =Integer.parseInt(conf.getProperty("maxActive"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
