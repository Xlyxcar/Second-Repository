package servlet;

import dao.UserInfoDAO;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpServlet;
import vo.UserInfo;

public class RegServlet extends HttpServlet{

	public void service(HttpRequest request,HttpResponse response){
		try{
			System.out.println("进入注册流程");
			//获取用户名
			String username = request.getParameter("username");
			
			//查询并返回用户信息
			UserInfo user = UserInfoDAO.findByUsername(username);
			
			if(user!=null){
				//用户存在,响应注册失败页面
				response.sendRedirect("reg_haveUser.html");
			}else{
				//用户不存在,写入注册信息并保存
				user = new UserInfo();
				user.setUsername(username);
				user.setPassword(request.getParameter("password"));
				user.setNickname(request.getParameter("nickname"));
				user.setPhonenumber(request.getParameter("phonenumber"));
				//将用户信息存入文件
				UserInfoDAO.save(user);
				//响应注册成功页面
				response.sendRedirect("reg_success.html");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}


}
