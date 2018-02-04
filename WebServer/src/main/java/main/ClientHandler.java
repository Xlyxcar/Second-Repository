package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Arrays;

import context.ServerContext;
import http.EnptyRequestException;
import http.HttpRequest;
import http.HttpResponse;
import servlet.LoginServlet;
import servlet.RegServlet;

public class ClientHandler implements Runnable{
	private Socket socket;
	public ClientHandler(Socket socket){
		this.socket = socket;
	}
	public void run() {
		try {
			//获取对象输入流,解析请求
			InputStream in = socket.getInputStream();
			HttpRequest request = new HttpRequest(in);
			//获取对象输出流,发送响应
			OutputStream out = socket.getOutputStream();
			HttpResponse response = new HttpResponse(out);
			
			File f = new File("webapps/"+request.getUrl());
			System.out.println("文件存在:"+f.exists());
			//通过请求URI获取对应的Servlet类名
			String servletName = ServerContext.getServletNameByURI(request.getRequestURI());
			System.out.println("servletName"+servletName);
			if(servletName!=null){
				//获得对应Servlet的Class对象
				Class cls = Class.forName(servletName);
				//通过Class对象获得实例对象
				Object obj = cls.newInstance();
				//通过Class对象指定方法名,参数的方法获得方法
				Method method = cls.getDeclaredMethod("service", new Class[]{HttpRequest.class,HttpResponse.class});
				//执行方法,指定执行对象及参数
				method.invoke(obj, new Object[]{request,response});

			}else if(f.exists()){
				
				String extension = request.getUrl().substring(request.getUrl().indexOf(".")+1);
				System.out.println("请求文件后缀:"+extension);
				
				//设置响应头中文件类型映射
				response.setContentType(extension);
				//设置响应头中文件长度
				response.setContentLength(f.length());
				//设置响应文件
				response.setEntity(f);
				//发送响应
				response.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EnptyRequestException e) {
			System.out.println("空请求:"+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
