package servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import dao.UserInfoDAO;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpServlet;
import vo.UserInfo;

public class RegServlet extends HttpServlet{

	public void service(HttpRequest request,HttpResponse response){
		try(
			//使用RandomAccessFile可以直接对文件执行读写,不必使用两条流,并且能控制指针
			RandomAccessFile raf = new RandomAccessFile("user.dat", "rw");
		){
			System.out.println("进入注册流程");
			//获取用户名
			String username = request.getParameter("username");
			
			//用户存在则返回用户信息
			UserInfo user = UserInfoDAO.findByUsername(username);
			
			if(user!=null){
				//响应注册失败页面
				forward("webapps/myweb/reg_haveUser.html",request,response);
			}else{
				
				user = new UserInfo();
				user.setUsername(username);
				user.setPassword(request.getParameter("password"));
				user.setNickname(request.getParameter("nickname"));
				user.setPhonenumber(request.getParameter("phonenumber"));
				//保存到文件中
				UserInfoDAO.save(user);
				//响应注册成功页面
				forward("webapps/myweb/reg_success.html", request, response);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}


}
