package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Demo {
	public static void main(String[] args) {
		Connection conn = null;
		try {
			//获取数据库连接
			conn = DBUtils.getConnection();
			//创建表
//			create(conn);
			//插入数据
//			insert(conn,4,"Jessica","JJjj");
			//登录流程(获取用户名/密码)
			Scanner scan = new Scanner(System.in);
			System.out.println("请输入用户名:");
			String name = scan.nextLine();
			System.out.println("请输入密码:");
			String password = scan.nextLine();
			//判定
			boolean flag = login(conn,name,password);
			//结果反馈
			if(flag){
				System.out.println("登录成功");
			}else{
				System.out.println("登录失败");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			//使用完毕,关闭数据库连接
			DBUtils.closeConnection(conn);
		}
	}
	/**
	 * 登录判断
	 * @param conn
	 * @param name
	 * @param password
	 * @return
	 */
	public static boolean login(Connection conn,String name,String password){
		try {
			//查询语句,若存在用户名和密码匹配的数据,count(*)列为1
			String sql = "select count(*) from t1 where name=? and password=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			//向查询语句传入参数
			ps.setString(1, name);
			ps.setString(2, password);
			//执行查询语句,并返回结果集
			ResultSet rs = ps.executeQuery();
			//返回结果集是否存在相匹配的数据
			if(rs.next()){
				return rs.getInt(1)==1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 插入数据
	 * @param conn
	 * @param id
	 * @param name
	 * @param password
	 */
	public static void insert(Connection conn,int id,String name,String password){
		try {
			//插入语句
			String sql = "insert into t1 values(?,?,?);";
			PreparedStatement ps = conn.prepareStatement(sql);
			//设定插入语句
			ps.setInt(1, id);
			ps.setString(2, name);
			ps.setString(3, password);
			//执行插入语句
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 创建表
	 * @param conn
	 */
	public static void create(Connection conn){
		try {
			//创建表t1(int,name,password)
			String sql = "create table t1(id int,name varchar(12),password(16)";
			Statement sta = conn.createStatement();
			//执行创建语句
			sta.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
