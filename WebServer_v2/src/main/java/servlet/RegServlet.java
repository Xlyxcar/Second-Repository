package servlet;

import java.io.File;

import dao.UserDAO;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpServlet;
import vo.UserInfo;

/**
 * 处理用户注册业务
 * @author asd99
 *
 */
public class RegServlet extends HttpServlet{
	public void service(HttpRequest request, HttpResponse response) {
		String username = request.getParater("username");
		UserDAO dao = new UserDAO();
		UserInfo user = dao.findUserByUsername(username);
		if(user==null){
			user = new UserInfo();
			user.setUsername(request.getParater("username"));
			user.setPassword(request.getParater("password"));
			user.setNickname(request.getParater("nickname"));
			user.setPhone(request.getParater("phone"));
			dao.saveUser(user);
			
			response.sendRedirect("../prompt/ok.html");
		}else{
			response.sendRedirect("../prompt/no.html");
		}
	}

}
