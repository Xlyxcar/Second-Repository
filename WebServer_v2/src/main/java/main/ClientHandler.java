package main;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

import http.EnptyRequestException;
import http.HttpRequest;
import http.HttpResponse;
import servlet.RegServlet;
import servlet.Servlet;

/**
 * 负责接收和响应客户端
 * @author asd99
 *
 */
public class ClientHandler implements Runnable{
	Socket socket;
	
	public ClientHandler(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		try {
			HttpRequest request = new HttpRequest(socket.getInputStream()); //解析客户端请求
			HttpResponse response = new HttpResponse(socket.getOutputStream()); //响应客户端
			File file = new File("webapps/"+request.getUrl());
			System.out.println("URI:"+request.getUri());
			if("/user/reg".equals(request.getUri())){
				Servlet reg = new RegServlet();
				reg.service(request,response);
			}else if("/user/login".equals(request.getUri())){
//				Servlet reg = new LoginServlet();
//				reg.service(request,response);
			}
			else if(file.exists()){
				String extension = file.getName().substring(file.getName().lastIndexOf(".")+1);
				response.setEntity(file);
				response.setContentLength(file.length());
				response.setContentType(extension);
				response.flush();
			}else{
				response.setEntity(new File("webapps/prompt/notFound.html"));
				response.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EnptyRequestException e) {
			System.out.println("空请求");
		}
	}
}
