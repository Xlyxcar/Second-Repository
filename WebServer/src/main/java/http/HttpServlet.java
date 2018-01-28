package http;

import java.io.File;

public class HttpServlet {
	public void service(){
		
	}
	
	public void forward(String str,HttpRequest request,HttpResponse response){
		response.setContentType("html");
		File file = new File(str);
		response.setContentLength(file.length());
		response.setEntity(file);
		response.flush();
	}
}
