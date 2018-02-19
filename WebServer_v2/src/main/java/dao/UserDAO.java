package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import vo.UserInfo;

/**
 * 负责执行对用户信息的处理操作
 * @author asd99
 *
 */
public class UserDAO {
	/**
	 * 用户数据持久化
	 * @param user
	 */
	public void saveUser(UserInfo user) {
		try (RandomAccessFile raf = new RandomAccessFile("user.dat", "rw"))
		{
			raf.seek(raf.length());
			save(raf,32,user.getUsername());
			save(raf,32,user.getPassword());
			save(raf,32,user.getNickname());
			save(raf,20,user.getPhone());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void save(RandomAccessFile raf, int len, String str) throws IOException {
		byte[] data = str.getBytes();
		data = Arrays.copyOf(data, len);
		raf.write(data);
	}
	/** 
	 * 通过用户名查找用户是否存在
	 * 存在则返回用户信息
	 * 否则返回null
	 */
	public UserInfo findUserByUsername(String username) {
		UserInfo user = null;
		try (RandomAccessFile raf = new RandomAccessFile("user.dat", "r"))
		{
			for(int i=0;i<raf.length()/116;i++){
				raf.seek(i*116);
				String name = readString(raf,32);
				if(name.equals(username)){
					user = new UserInfo();
					user.setUsername(username);
					user.setPassword(readString(raf,32));
					user.setNickname(readString(raf,32));
					user.setPhone(readString(raf,20));
					break;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	/**
	 * 读取指定长度字节,转为字符串并返回
	 * @param raf
	 * @param len 字节长度
	 * @return
	 */
	private String readString(RandomAccessFile raf,int len){
		byte[] data = new byte[len];
		String str = "";
		try{
			raf.read(data);
			str = new String(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str.trim();
	}

}
