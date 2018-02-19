package context;

import java.io.File;

import http.HttpRequest;
import http.HttpResponse;

/**
 * Servlet主类,共享共用方法
 * @author asd99
 *
 */
public class HttpServlet {
	public void service(HttpRequest request,HttpResponse response){}
	
	public void forward(String url,HttpRequest request,HttpResponse response){
		response.setEntity(new File(url));
		response.setContentLength(response.getEntity().length());
		response.setContentType("html");
		response.flush();
	}
}
