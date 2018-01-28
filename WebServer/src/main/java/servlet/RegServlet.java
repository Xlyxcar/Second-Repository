package servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpServlet;

public class RegServlet extends HttpServlet{

	public void service(HttpRequest request,HttpResponse response){
		try(
			//使用RandomAccessFile可以直接对文件执行读写,不必使用两条流,并且能控制指针
			RandomAccessFile raf = new RandomAccessFile("user.dat", "rw");
		){
			System.out.println("进入注册流程");
			
			//判断用户名是否存在
			String username = request.getParameter("username");
			
			byte[] data = new byte[32];
			String name;
			boolean have = false;
			for(int i=0;i<raf.length()/116;i++){
				raf.seek(i*116);
				
				raf.read(data);
				name = new String(data).trim();
				if(name.equals(username)){
					have = true;
					break;
				}
			}
			if(have){
				forward("webapps/myweb/reg_haveUser.html",request,response);
			}else{
				raf.seek(raf.length());
				String password = request.getParameter("password");
				String nickname = request.getParameter("nickname");
				String phonenumber = request.getParameter("phonenumber");
				
				data = username.getBytes();
				data = Arrays.copyOf(data, 32);
				raf.write(data);
				data = password.getBytes();
				data = Arrays.copyOf(data, 32);
				raf.write(data);
				data = nickname.getBytes();
				data = Arrays.copyOf(data, 32);
				raf.write(data);
				data = phonenumber.getBytes();
				data = Arrays.copyOf(data, 32);
				raf.write(data);
				
				
				forward("webapps/myweb/reg_success.html", request, response);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}


}
