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

//HTTP响应分为三部分:
//1,状态行
//2.响应头
//3.响应正文

public class HttpResponse {
	private OutputStream out;
	//响应文件
	private File entity;
	//响应头信息
	private Map<String, String> headers = new HashMap<String, String>();
	
	
	public HttpResponse(OutputStream out) {
		this.out = out;
	}
	//响应
	public void flush(){
		//发送状态行
		sendStatusLine();
		//发送响应头
		sendHeaders();
		//发送响应正文
		sendContent();
	}
	//发送响应正文
	private void sendContent() {
		FileInputStream fis;
		//读取需要发送的文件,写入数组并发送
		try {
			fis = new FileInputStream(entity);
			byte[] data = new byte[1024*10];
			int len = -1;
			while((len = fis.read(data))!=-1){
				out.write(data,0,len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//发送响应头
	private void sendHeaders() {
		for(Entry<String, String>e:headers.entrySet()){
			sendStr(e.getKey()+":"+e.getValue());
		}
		sendStr("");
	}
	//发送状态行
	private void sendStatusLine() {
		String statusLine = "HTTP/1,1 200 OK";
		sendStr(statusLine);
	}
	//负责发送一行数据
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
	//设置响应头类型
	public void setContentType(String extension){
		String value = HttpConText.getMimeType(extension);
		headers.put(HttpConText.HEADER_CONTENT_TYPE, value);
	}
	//设置响应文件长度
	public void setContentLength(long length){
		headers.put(HttpConText.HEADER_CONTENT_LENGTH, ""+length);
	}
	//设定相应文件
	public void setEntity(File entity) {
		this.entity = entity;
	}
	
}
