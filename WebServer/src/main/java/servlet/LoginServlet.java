package servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import http.HttpRequest;
import http.HttpResponse;

public class LoginServlet {
	public void service(HttpRequest request,HttpResponse response){
		try(
			RandomAccessFile raf = new RandomAccessFile("user.dat", "r");
		){
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			byte[] data = new byte[32];
			String name,pwd;
			boolean success = false;
			for(int i=0;i<raf.length()/116;i++){
				raf.seek(i*116);
				raf.read(data);
				name = new String(data).trim();
				if(name.equals(username)){
					raf.read(data);
					pwd = new String(data).trim();
					if(pwd.equals(password)) success = true;
					break;
				}
			}
			if(success){
				response.setContentType("html");
				File file = new File("webapps/myweb/login_suc.html");
				response.setContentLength(file.length());
				response.setEntity(file);
				response.flush();
			}else{
				response.setContentType("html");
				File file = new File("webapps/myweb/login_fail.html");
				response.setContentLength(file.length());
				response.setEntity(file);
				response.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
