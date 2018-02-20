package http;

import java.io.File;

import context.HttpContext;

/**
 * Servlet主类,共享共用方法
 * @author asd99
 *
 */
public class HttpServlet {
	/**
	 * 供子类继承后重写,执行对应业务逻辑
	 * @param request
	 * @param response
	 */
	public void service(HttpRequest request,HttpResponse response){}
	
	/**
	 * 跳转到指定页面
	 * @param url 页面链接
	 * @param request
	 * @param response
	 */
	public void forward(String url,HttpRequest request,HttpResponse response){
		response.setStatusCode(HttpContext.STATE_CODE_OK);
		response.setContentLength(response.getEntity().length());
		response.setContentType("html");
		response.setEntity(new File(url));
		response.flush();
	}
}
