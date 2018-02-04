package servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import context.HttpConText;
import dao.UserInfoDAO;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpServlet;
import vo.UserInfo;
/**
 * 处理客户端登录业务
 * @author asd99
 *
 */
public class LoginServlet extends HttpServlet{
	public void service(HttpRequest request,HttpResponse response) throws IOException{
		//获取客户端提交的用户名和密码
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//通过用户名查询用户信息
		UserInfo user = UserInfoDAO.findByUsername(username);
		if(user!=null && user.getPassword().equals(password)){
			//用户存在并且密码匹配,响应登录成功
			response.sendRedirect("login_suc.html");
		}else{
			//否则,响应登录失败
			response.sendRedirect("login_fail.html");
		}
	}
}
