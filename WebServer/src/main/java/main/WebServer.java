package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(8088);
			System.out.println("创建服务端成功,开始等待客户端连接...");
			while(true){
				Socket socket = server.accept();
				ClientHandler handler = new ClientHandler(socket);
				Thread t = new Thread(handler);
				t.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
