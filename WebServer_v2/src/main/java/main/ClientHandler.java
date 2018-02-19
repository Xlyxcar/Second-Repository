package main;

import java.io.File;
import java.lang.reflect.Method;
import java.net.Socket;

import context.HttpServlet;
import context.ServerContext;
import http.HttpRequest;
import http.HttpResponse;

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
			String servletName = ServerContext.getServletNameByURI(request.getUri());
			System.out.println("cls:"+servletName);
			if(servletName!=null){
				Class cla = Class.forName(servletName);
				//方式1
//				Object obj = cla.newInstance(); //实例化类对象
//				Method method = cla.getMethod("service", new Class[]{HttpRequest.class,HttpResponse.class}); //创建类方法
//				method.invoke(obj, new Object[]{request,response}); //指定对象执行类方法
				
				//方式2
				HttpServlet servlet = (HttpServlet)cla.newInstance(); //实例化对象并强转为HttpServlet
				servlet.service(request, response); //执行HttpServlet的service()方法
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
		} catch (Exception e) {
			e.printStackTrace();
		}
//		catch (EnptyRequestException e) {
//			System.out.println("空请求");
//		}
	}
}
