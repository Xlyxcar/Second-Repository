package servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import dao.UserInfoDAO;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpServlet;
import vo.UserInfo;

public class LoginServlet extends HttpServlet{
	public void service(HttpRequest request,HttpResponse response){
		try(
			RandomAccessFile raf = new RandomAccessFile("user.dat", "r");
		){
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			UserInfo user = UserInfoDAO.findByUsername(username);
			if(user.getPassword().equals(password)){
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
