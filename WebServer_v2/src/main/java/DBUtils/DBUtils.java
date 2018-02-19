package DBUtils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtils {
	private static String url,username,password,driver;
	static{
		url = "";
		username = "root";
		password = "123456";
		driver = 
	}
	public static Connection getConnection(){
		Connection conn = null;
		try{
			conn = DriverManager.getConnection(url, username, password);
		}catch(Exception e){
			e.printStackTrace();
		}
		return conn;
	}
}
