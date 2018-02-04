package http;

import java.io.File;
/**
 * 所有Servlet父类,负责Servlet共有方法
 * @author asd99
 *
 */
public class HttpServlet {
	public void service(){
		
	}
	/**
	 * 响应指定页面
	 * 设置页面类型,设定响应信息并响应客户端
	 * @param str
	 * @param request
	 * @param response
	 */
	public void forward(String url,HttpRequest request,HttpResponse response){
		response.setContentType("html");
		File file = new File(url);
		response.setContentLength(file.length());
		response.setEntity(file);
		response.flush();
	}
}
