package http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import context.HttpContext;
import context.ServerContext;

/**
 * 负责处理响应客户端请求
 * @author asd99
 *
 */
public class HttpResponse {
	//输出流
	private OutputStream out;
	
	//响应文件
	private File entity;
	
	//响应头信息
	private Map<String,String> headers = new HashMap<String, String>();
	
	//状态码
	private int statusCode; 
	public HttpResponse(OutputStream out) {
		this.out = out;
	}
	/** 响应客户端 */
	public void flush(){
		sendStatusLine();
		sendHeaders();
		sendContent();
	}
	/** 
	 * 发送响应正文
	 * FileInputSrtream获取文件数据并读入数组,输出数组数据
	 * 若一次无法读取所有字节则循环读取并输出,直到读取结束(len==-1)
	 */
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
		Set<Entry<String, String>> entrys = headers.entrySet();
		for(Entry<String, String> e: entrys){
			print(e.getKey()+":"+e.getValue());
		}
		print("");
	}
	/** 发送状态行 */
	private void sendStatusLine() {
		String line = ServerContext.getProtocol()+" "+statusCode+" "+HttpContext.getStatusReasonByStatusCode(statusCode);
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
	/**
	 * 让客户端重定向到指定地址
	 * @param url
	 */
	public void sendRedirect(String url){
		setStatusCode(HttpContext.STATE_CODE_REDIRECT);
		headers.put(HttpContext.LOCATION, url);
		sendStatusLine();
		sendHeaders();
	}
	/** 设置响应文件 */
	public void setEntity(File entity) {
		this.entity = entity;
	}
	/** 设置响应文件类型 */
	public void setContentType(String contentType) {
		headers.put(HttpContext.CONTENT_TYPE, contentType);
	}
	/** 设置响应文件长度 */
	public void setContentLength(Long contentLength) {
		headers.put(HttpContext.CONTENT_LENGTH, contentLength.toString());
	}
	/** 设置状态码 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	/** 获取响应文件 */
	public File getEntity() {
		return entity;
	}
}
