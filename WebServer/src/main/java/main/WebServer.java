package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import context.ServerContext;

public class WebServer {
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(Integer.valueOf(ServerContext.port));
			System.out.println("创建服务端成功,开始等待客户端连接...");
			ExecutorService threadPool = Executors.newFixedThreadPool(Integer.valueOf(ServerContext.threadPoolSum));
			
			while(true){
				Socket socket = server.accept();
				ClientHandler handler = new ClientHandler(socket);
				Thread t = new Thread(handler);
				threadPool.execute(t);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
