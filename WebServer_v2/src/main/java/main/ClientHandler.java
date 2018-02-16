package main;

import java.io.IOException;
import java.net.Socket;

import http.HttpRequest;
import http.HttpResponse;

/**
 * 负责接收和响应连接的客户端
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
			HttpResponse response = new HttpResponse(socket.getOutputStream()); //响应客户端请求
			request.print();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
