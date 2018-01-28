package main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import http.HttpRequest;
import http.HttpResponse;

public class ClientHandler implements Runnable{
	Socket socket;
	public ClientHandler(Socket socket){
		this.socket = socket;
	}
	public void run() {
		try {
			InputStream in = socket.getInputStream();
			HttpRequest request = new HttpRequest(in);
			OutputStream out = socket.getOutputStream();
			HttpResponse response = new HttpResponse(out);
			File f = new File("webapps/"+request.getUrl());
			System.out.println("文件存在:"+f.exists());
			if(f.exists()){
				response.setEntity(f);
				response.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
