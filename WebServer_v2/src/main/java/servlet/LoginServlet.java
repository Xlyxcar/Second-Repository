package servlet;

import java.awt.SecondaryLoop;
import java.io.File;

import context.HttpServlet;
import dao.UserDAO;
import http.HttpRequest;
import http.HttpResponse;
import vo.UserInfo;

/**
 * 处理用户登录业务
 * @author asd99
 *
 */
public class LoginServlet extends HttpServlet{
	public void service(HttpRequest request, HttpResponse response) {
		UserDAO dao = new UserDAO();
		UserInfo user = dao.findUserByUsername(request.getParater("username"));
		if(user!=null && request.getParater("password").equals(user.getPassword())){
			response.sendRedirect("../prompt/ok.html");
		}else{
			response.sendRedirect("../prompt/no.html");
		}
	}

}
