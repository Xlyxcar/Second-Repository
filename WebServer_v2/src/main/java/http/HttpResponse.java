package http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import context.HttpContext;

/**
 * 负责处理响应客户端请求
 * @author asd99
 *
 */
/*
 * HTTP/1.1 200 OK(CRLF)
Content-Type:text/html(CRLF)
Content-Length:325(CRLF)(CRLF)
101010111010100101....<响应正文中的二进制数据,325个字节>

 */
public class HttpResponse {
	private OutputStream out;
	private File entity;
	private String ContentType;
	public HttpResponse(OutputStream out) {
		this.out = out;
	}
	/** 响应客户端 */
	public void flush(){
		sendStatusLine();
		sendHeaders();
		sendContent();
	}
	/** 发送响应正文 */
	private void sendContent() {
		try (FileInputStream fis = new FileInputStream(entity)){
			byte[] data = new byte[100*1024];
			int len = -1;
			while((len = fis.read(data, 0, data.length)) != -1){
				out.write(data, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/** 发送响应头 */
	private void sendHeaders() {
		print(HttpContext.CONTENT_TYPE+":text/html");
		print(HttpContext.CONTENT_LENGTH+":"+entity.length());
		print("");
	}
	/** 发送状态行 */
	private void sendStatusLine() {
		String line = "HTTP/1.1 200 OK";
		print(line);
	}
	/** 发送一行数据 */
	private void print(String line){
		try {
			byte[] data = line.getBytes();
			out.write(data, 0, data.length);
			out.write(HttpContext.CR);
			out.write(HttpContext.LF);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setEntity(File entity) {
		this.entity = entity;
	}
	public void setContentType(String contentType) {
		ContentType = contentType;
	}
}
