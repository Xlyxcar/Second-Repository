package http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import context.HttpConText;

//HTTP请求的格式分为三个部分组成:
//1:请求行
//2:请求头(消息头)
//3:请求正文(消息正文)

public class HttpRequest {
	InputStream in;
	//请求方法,链接,协议版本
	private String method,url,protocol;
	//存放消息头信息
	private Map<String, String> headers = new HashMap<String,String>();
	
	//链接本体,链接附带参数
	private String requestURI,queryString;
	//附带参数分组
	private Map<String, String> paramenters = new HashMap<String, String>();
	
	public HttpRequest(InputStream in) throws EnptyRequestException {
		this.in = in;
		parseRequestLine();
		parseHeaders();
	}
	//解析请求行
	public void parseRequestLine() throws EnptyRequestException{
		String line = readLine();
		if(line.length()==0){
			throw new EnptyRequestException();
		}
		String[] arr = line.split("\\s");
		method = arr[0];
		url = arr[1];
		if(url.indexOf("?")!=-1){
			parseUrl();
		}
		protocol = arr[2];
		System.out.println("请求方法:"+method+",请求链接:"+url+",协议版本:"+protocol);
	}
	/**
	 * 解析URL,将请求参数和链接分离
	 */
	private void parseUrl() {
		requestURI = url.substring(0, url.indexOf("?"));
		queryString = url.substring(url.indexOf("?")+1, url.length());
		String[] querys = queryString.split("&");
		for(String s:querys){
			int index = s.indexOf("=");
			paramenters.put(s.substring(0,index), s.substring(index+1, s.length()));
			System.out.println("参数:"+s.substring(0,index)+",值:"+s.substring(index+1, s.length()));
		}
	}
	//解析消息头
	public void parseHeaders(){
		String line = readLine();
		//消息头名,值
		String name,value;
		while(line.length()>1){
			int index = line.indexOf(":");
			name = line.substring(0,index);
			value = line.substring(index+1, line.length()).trim();
			headers.put(name, value);
			System.out.println("存入:"+name+"和"+value);
			line = readLine();
		}
		
		
	}
	//解析消息正文
	//读取一行消息
	private String readLine() {
		StringBuilder data = new StringBuilder();
		try{
			//读取字节,回车判断,换行判断
			int i,c1,c2=-1;
			while((i=in.read())!=-1){
				c1 = i;
				if(c2==HttpConText.CR && c1==HttpConText.LF){
					break;
				}
				data.append((char)i);
				c2 = c1;
			}
//			System.out.println("读了一行:"+data);
		}catch(IOException e){
			e.printStackTrace();
		}
		return data.toString();
	}
	public String getMethod() {
		return method;
	}
	public String getUrl() {
		return url;
	}
	public String getProtocol() {
		return protocol;
	}
	public String getRequestURI() {
		return requestURI;
	}
	public String getParameter(String name){
		return paramenters.get(name);
	}
}
