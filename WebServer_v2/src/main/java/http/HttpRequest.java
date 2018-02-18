package http;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import context.HttpContext;

/**
 * 负责解析客户端请求
 * @author asd99
 *
 */
public class HttpRequest {
	private InputStream in; //输入流接收客户端发送信息
	private String method,url,protocol; //请求类型,地址,协议
	private Map<String,String> requestMapping = new HashMap<String, String>(); //请求行信息以键值对存放
	
	public HttpRequest(InputStream in) {
		this.in = in;
		parseRequest();
	}

	/** 解析客户端请求 */
	private void parseRequest(){
		parseRequestLine();
		parseHeaders();
	}
	
	/** 
	 * 解析消息头 
	 * 循环读取(直到读取到空字符串),以":"拆分字符串,并将":"两端的值去除空字符串后,作为键值对放入数组
	 */
	private void parseHeaders() {
		while(true){
			String line = readLine();
			if(line.equals("")) break;
			String key = line.substring(0, line.indexOf(":")).trim();
			String value = line.substring(line.indexOf(":")+1).trim();
			requestMapping.put(key, value);
		}
	}

	/** 
	 * 解析请求行
	 * 读取第一行请求,根据" "拆分,将拆分后的字符串赋值给对应变量
	 */
	private void parseRequestLine() {
		String line = readLine();
		String[] arr = line.split(" ");
		method = arr[0];
		url = arr[1];
		protocol = arr[2];
	}
	
	/** 
	 * 读取一行请求信息
	 * 循环读取单个字符添加到StringBuffere,如果连续读取到CR(13)LF(10),跳出并返回之前读取到的字符串.
	 */
	private String readLine() {
		StringBuffer line = new StringBuffer();
		try{
			int c1 = -1;
			int c2 = -1;
			int i = -1;
			while((i=in.read()) != -1){
				c1 = i;
				if(c1==HttpContext.LF && c2==HttpContext.CR) break;
				line.append((char)i);
				c2 = c1;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("读了一行:"+line.toString().trim());
		return line.toString().trim();
	}
	
	/** 获取请求方式 */
	public String getMethod() {
		return method;
	}
	/** 获取url */
	public String getUrl() {
		return url;
	}
	/** 获取协议版本 */
	public String getProtocol() {
		return protocol;
	}
	/** 获取指定名字请求行的对应值 */
	public String getParamenter(String name){
		return requestMapping.get(name);
	}
}
