package DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * 创建用户表(如果用户表不存在)
 * @author asd99
 *
 */
public class CreateTable {
	/** 创建用户表 */
	public static void createTableOfUser(){
		Connection conn = null;
		try {
			conn = DBUtils.getConnection();
			String sql = "create table user("
					+ "id int auto_increment primary key,"
					+ "username varchar(32) unique,"
					+ "password varchar(32),"
					+ "nickname varchar(32))";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtils.closeConnection(conn);
		}
	}
}
