package main;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import context.ServerContext;

/**
 * 启动服务端,等待客户端连接并交给ClientHandler处理
 * @author asd99
 *
 */
public class WebServler {
	public static void main(String[] args) {
		checkFile();
		try {
			ServerSocket server = new ServerSocket(Integer.parseInt(ServerContext.getPort())); //建立服务端
			ExecutorService pool = Executors.newFixedThreadPool(Integer.parseInt(ServerContext.getThreadPoolSum())); //创建线程池
			while(true){
				Socket socket = server.accept(); //等待客户端连接
				ClientHandler handler = new ClientHandler(socket); //将连接交由ClientHandler处理
				Thread t = new Thread(handler); //创建新线程处理连接
				pool.execute(t); //将线程放入线程池中执行
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/** 检查存储用户信息的文件是否存在,如果不存在则创建一个 */
	private static void checkFile(){
			try {
				File file = new File("user.dat");
				if(!file.exists()) file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
