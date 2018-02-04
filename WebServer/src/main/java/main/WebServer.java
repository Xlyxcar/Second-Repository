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
			//创建服务端
			ServerSocket server = new ServerSocket(Integer.valueOf(ServerContext.port));
			System.out.println("创建服务端成功,开始等待客户端连接...");
			//创建线程池
			ExecutorService threadPool = Executors.newFixedThreadPool(Integer.valueOf(ServerContext.threadPoolSum));
			
			while(true){
				//服务端开始等待客户端连接
				Socket socket = server.accept();
				//将链接交由ClientHandler
				ClientHandler handler = new ClientHandler(socket);
				//创建新线程处理客户端连接
				Thread t = new Thread(handler);
				//新线程放入线程池启动
				threadPool.execute(t);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
