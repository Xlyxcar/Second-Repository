package http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import context.HttpConText;
import context.ServerContext;

//HTTP请求的格式分为三个部分组成:
//1:请求行
//2:请求头(消息头)
//3:请求正文(消息正文)

public class HttpRequest {
	private InputStream in;
	//请求方法,链接,协议版本
	private String method,url,protocol;
	//存放消息头信息
	private Map<String, String> headers = new HashMap<String,String>();
	
	//链接本体,链接附带参数
	private String requestURI,queryString;
	//附带参数分组
	private Map<String, String> paramenters = new HashMap<String, String>();
	
	/**
	 * 解析请求
	 * @param in
	 * @throws EnptyRequestException
	 */
	public HttpRequest(InputStream in) throws EnptyRequestException {
		this.in = in;
		parseRequestLine();
		parseHeaders();
		parseContent();
	}
	/**
	 * 解析消息正文
	 */
	private void parseContent() {
		try {
			System.out.println("进入读取消息正文:"+headers.get("Content-Length"));
			//如果存在正文长度,则正文存在
			if(headers.containsKey(HttpConText.HEADER_CONTENT_LENGTH)){
				//获取消息正文长度
				int length = Integer.valueOf(headers.get(HttpConText.HEADER_CONTENT_LENGTH));
				//读取消息正文并转为字符串
				byte[] data = new byte[length];
				in.read(data);
				String line = new String(data);
				//解码
				line = URLDecoder.decode(line, ServerContext.URIEncoding);
				parseQuery(line);
				System.out.println("解析完毕");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 解析请求行
	 * @throws EnptyRequestException
	 */
	private void parseRequestLine() throws EnptyRequestException{
		String line = readLine();
		//请求行长度为0,抛出空请求异常
		if(line.length()==0){
			throw new EnptyRequestException();
		}
		String[] arr = line.split("\\s");
		method = arr[0];
		url = arr[1];
		if(url.indexOf("?")!=-1){
			parseUrl();
		}else{
			//如请求链接不带参数,则uri=url
			requestURI = url;
		}
		protocol = arr[2];
		System.out.println("请求方法:"+method+",请求链接:"+url+",协议版本:"+protocol);
	}
	/**
	 * 解析URL,将请求参数和链接分离
	 */
	private void parseUrl() {
		try {
			//解码,防止传入非欧洲字符集参数显示16进制码
			url = URLDecoder.decode(url,ServerContext.URIEncoding);
			System.out.println("url::"+url);
			//获取URI
			requestURI = url.substring(0, url.indexOf("?"));
			//获取请求参数
			queryString = url.substring(url.indexOf("?")+1, url.length());				
			parseQuery(queryString);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 解析请求参数
	 * @param str
	 */
	private void parseQuery(String str) {
		String[] querys = str.split("&");
		for(String s:querys){
			int index = s.indexOf("=");
			//将解析后的参数放入paramenters
			paramenters.put(s.substring(0,index), s.substring(index+1, s.length()));
			System.out.println("参数:"+s.substring(0,index)+",值:"+s.substring(index+1, s.length()));
		}
	}
	/**
	 * 解析消息头
	 */
	private void parseHeaders(){
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
	/**
	 * 读取一行消息
	 * @return
	 */
	private String readLine() {
		StringBuilder data = new StringBuilder();
		try{
			//读取字节,回车判断,换行判断
			int i,c1,c2=-1;
			while((i=in.read())!=-1){
				c1 = i;
				//若读到换行符(LF),并且前一个字符为回车(CR),表示一行读取完毕
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
