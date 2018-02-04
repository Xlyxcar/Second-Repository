package http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import context.HttpConText;
import context.ServerContext;

/*
 * HTTP响应分为三部分:
 * 1,状态行
 * 2.响应头
 * 3.响应正文
 */
/**
 * 负责响应客户端
 * @author asd99
 *
 */
public class HttpResponse {
	private OutputStream out;
	//响应文件
	private File entity;
	//响应头信息
	private Map<String, String> headers = new HashMap<String, String>();
	//状态码
	private int statusCode;
	
	public HttpResponse(OutputStream out) {
		this.out = out;
	}
	/**
	 * 发送所有响应信息
	 */
	public void flush(){
		//发送状态行
		sendStatusLine();
		//发送响应头
		sendHeaders();
		//发送响应正文
		sendContent();
	}
	/**
	 * 发送响应正文
	 */
	private void sendContent() {
		//读取需要发送的文件,写入数组并发送
		try (
			FileInputStream fis = new FileInputStream(entity);	
			){
			byte[] data = new byte[1024*10];
			int len = -1;
			while((len = fis.read(data))!=-1){
				out.write(data,0,len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 发送响应头
	 * 读取headers中每个响应头键值对,并在最后发送CRLF表示响应头发送完毕
	 */
	private void sendHeaders() {
		for(Entry<String, String>e:headers.entrySet()){
			sendStr(e.getKey()+":"+e.getValue());
		}
		sendStr("");
	}
	/**
	 * 发送状态行
	 */
	private void sendStatusLine() {
		String statusLine = ServerContext.protocol+" "+statusCode+" "+HttpConText.getStatusReasonByStatusCode(statusCode);
		sendStr(statusLine);
	}
	/**
	 * 负责发送一行数据
	 * @param str
	 */
	private void sendStr(String str) {
		byte[] data = str.getBytes();
		try {
			out.write(data, 0, data.length);
			out.write(HttpConText.CR);
			out.write(HttpConText.LF);
			System.out.println("发了一行:"+str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 设置响应头类型
	 * @param extension
	 */
	public void setContentType(String extension){
		String value = HttpConText.getMimeType(extension);
		headers.put(HttpConText.HEADER_CONTENT_TYPE, value);
	}
	/**
	 * 设置响应文件长度
	 * @param length
	 */
	public void setContentLength(long length){
		headers.put(HttpConText.HEADER_CONTENT_LENGTH, ""+length);
	}
	/**
	 * 设定相应文件
	 * @param entity
	 */
	public void setEntity(File entity) {
		this.entity = entity;
	}
	/**
	 * 设定状态码
	 * @param code
	 */
	public void setStatusCode(int code){
		statusCode = code;
	}
	/**
	 * 重定向发送
	 * 设定状态代码为302(重定向),设定重定向链接并响应客户端
	 * 客户端收到后,重新请求指定页面,完成跳转
	 * @param url
	 */
	public void sendRedirect(String url){
		//设定状态码为重定向
		this.setStatusCode(HttpConText.STATUS_CODE_REDIRECT);
		//添加重定向位置
		this.headers.put(HttpConText.HEADER_LOCATION, url);
		//发送响应
		this.flush();
	}
}
