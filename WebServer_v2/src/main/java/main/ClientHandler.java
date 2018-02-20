package main;

import java.io.File;
import java.net.Socket;

import context.HttpContext;
import context.ServerContext;
import http.EnptyRequestException;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpServlet;

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
			//解析客户端请求
			HttpRequest request = new HttpRequest(socket.getInputStream()); 
			//响应客户端
			HttpResponse response = new HttpResponse(socket.getOutputStream());
			
			String servletName = ServerContext.getServletNameByURI(request.getUri());

			if(servletName!=null){
				Class cla = Class.forName(servletName);
				//方式1
//				Object obj = cla.newInstance(); //实例化类对象
//				Method method = cla.getMethod("service", new Class[]{HttpRequest.class,HttpResponse.class}); //创建类方法
//				method.invoke(obj, new Object[]{request,response}); //指定对象执行类方法
				
				//方式2
				HttpServlet servlet = (HttpServlet)cla.newInstance(); //实例化对象并强转为HttpServlet
				servlet.service(request, response); //执行HttpServlet的service()方法
			}else{
				File file = new File("webapps/"+request.getUrl());
				
				if(file.exists()){ //文件存在,响应对应文件
					String extension = file.getName().substring(file.getName().lastIndexOf(".")+1);
					//设置状态码
					response.setStatusCode(HttpContext.STATE_CODE_OK);
					//设置响应头
					response.setEntity(file);
					response.setContentLength(file.length());
					//设置响应文件
					response.setContentType(extension);
					response.flush();
				}else{ //否则,响应文件未找到
					file = new File("webapps/prompt/notFound.html");
					String extension = file.getName().substring(file.getName().lastIndexOf(".")+1);
					//设置状态码
					response.setStatusCode(HttpContext.STATE_CODE_OK);
					//设置响应头
					response.setEntity(file);
					response.setContentLength(file.length());
					//设置响应文件
					response.setContentType(extension);
					response.flush();
				}
			}
		} catch(EnptyRequestException e){
			System.out.println("空请求");
		} catch (Exception e) {
			e.printStackTrace();
		}
//		catch (EnptyRequestException e) {
//			System.out.println("空请求");
//		}
	}
}
