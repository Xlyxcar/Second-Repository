package servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpServlet;

public class LoginServlet extends HttpServlet{
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
				forward("webapps/myweb/login_suc.html",request,response);
			}else{
				forward("webapps/myweb/login_fail.html", request, response);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
