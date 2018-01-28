package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import vo.UserInfo;

public class UserInfoDAO {
	private static final RandomAccessFile RandomAccessFile = null;
	public static void save(UserInfo user){
		try(RandomAccessFile raf = new RandomAccessFile("user.dat", "rw")){
			//跳到文件末尾
			raf.seek(raf.length());
			//写入用户名
			byte[] data = user.getUsername().getBytes();
			data = Arrays.copyOf(data, 32);
			raf.write(data);
			//写入密码
			data = user.getPassword().getBytes();
			data = Arrays.copyOf(data, 32);
			raf.write(data);
			//写入昵称
			data = user.getNickname().getBytes();
			data = Arrays.copyOf(data, 32);
			raf.write(data);
			//写入手机号
			data = user.getPhonenumber().getBytes();
			data = Arrays.copyOf(data, 20);
			raf.write(data);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static UserInfo findByUsername(String username){
		try(
			//使用RandomAccessFile可以直接对文件执行读写,不必使用两条流,并且能控制指针
			RandomAccessFile raf = new RandomAccessFile("user.dat", "rw");
		){
			//文件中昵称字符串
			String name;
			for(int i=0;i<raf.length()/116;i++){
				raf.seek(i*116);
				name = readString(raf,32);
				if(name.equals(username)){
					//读取其他信息
					String pwd = readString(raf,32);
					String nick = readString(raf,32);
					String phone = readString(raf,20);
					//用户信息保存到user对象中
					UserInfo user = new UserInfo();
					user.setUsername(name);
					user.setPassword(pwd);
					user.setNickname(nick);
					user.setPhonenumber(phone);
					return user;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String readString(RandomAccessFile raf,int len) throws IOException{
		byte[] data = new byte[len];
		raf.read(data);
		return new String(data).trim();
	}
}
