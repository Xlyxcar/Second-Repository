package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

import http.EnptyRequestException;
import http.HttpRequest;
import http.HttpResponse;
import servlet.RegServlet;

public class ClientHandler implements Runnable{
	Socket socket;
	public ClientHandler(Socket socket){
		this.socket = socket;
	}
	public void run() {
		try {
			//解析对象
			InputStream in = socket.getInputStream();
			HttpRequest request = new HttpRequest(in);
			//响应对象
			OutputStream out = socket.getOutputStream();
			HttpResponse response = new HttpResponse(out);
			
			File f = new File("webapps/"+request.getUrl());
			System.out.println("文件存在:"+f.exists());
			System.out.println(request.getRequestURI());
			if("/myweb/reg".equals(request.getRequestURI())){
				RegServlet servlet = new RegServlet();
				servlet.service(request, response);
				
			}else if(f.exists()){
				
				String extension = request.getUrl().substring(request.getUrl().indexOf(".")+1);
				System.out.println("请求文件后缀:"+extension);
				
				//设置响应头中文件类型映射
				response.setContentType(extension);
				//设置响应头中文件长度
				response.setContentLength(f.length());
				
				response.setEntity(f);
				response.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EnptyRequestException e) {
			System.out.println("空请求:"+e.getMessage());
		}
	}
	
}
