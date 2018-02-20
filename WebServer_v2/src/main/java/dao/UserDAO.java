package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DBUtils.DBUtils;
import vo.UserInfo;

/**
 * 负责执行对用户信息的处理操作
 * @author asd99
 *
 */
public class UserDAO {
	Connection conn = null;
	/**
	 * 用户数据持久化(存入数据库)
	 * @param user
	 */
	public void saveUser(UserInfo user) {
		try {
			conn = DBUtils.getConnection();
			String sql = "insert into user(username,password,nickname,phone) values(?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getNickname());
			ps.setString(4, user.getPhone());
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtils.closeConnection(conn);
		}
	}

	/** 
	 * 通过用户名查找用户是否存在
	 * 存在则返回用户信息
	 * 否则返回null
	 */
	public UserInfo findUserByUsername(String username) {
		UserInfo user = null;
		try {
			conn = DBUtils.getConnection();
			String sql = "select username,password,nickname,phone from user where username=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				user = new UserInfo();
				
				user.setUsername(rs.getString(1));
				user.setPassword(rs.getString(2));
				user.setNickname(rs.getString(3));
				user.setPhone(rs.getString(4));
				System.out.println(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtils.closeConnection(conn);
		}
		return user;
	}

}
