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
	private Map<String,String> headers = new HashMap<String, String>(); //请求行信息以键值对存放
	private String requestURI,queryString; //分别存储地址栏请求的地址与参数(如果存在)
	private Map<String,String> parameters = new HashMap<String,String>();//保存地址栏参数(如果存在)
	
	public HttpRequest(InputStream in) throws EnptyRequestException {
		this.in = in;
		parseRequest();
	}

	/** 解析客户端请求 
	 * @throws EnptyRequestException
	 */
	private void parseRequest() throws EnptyRequestException{
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
			headers.put(key, value);
		}
	}

	/** 
	 * 解析请求行
	 * 读取第一行请求,根据" "拆分,将拆分后的字符串赋值给对应变量
	 * @throws EnptyRequestException 
	 */
	private void parseRequestLine() throws EnptyRequestException {
		String line = readLine();
		if(line.length()<1) throw new EnptyRequestException("空请求");
		String[] arr = line.split(" ");
		method = arr[0];
		url = arr[1];
		if(url.indexOf("?")!=-1){
			parseUrl();
		}else{
			requestURI = url;
		}
		protocol = arr[2];
	}
	
	/**
	 * 解析Url(如果请求链接后附带参数)
	 * 将参数放入parameters中
	 */
	private void parseUrl() {
		requestURI = url.substring(0,url.indexOf("?"));
		queryString = url.substring(url.indexOf("?")+1);
		String[] arr = queryString.split("&");
		for(String str:arr){
			String key = str.substring(0,str.indexOf("="));
			String value = str.substring(str.indexOf("=")+1);
			parameters.put(key, value);
		}
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
	/** 获取uri */
	public String getUri() {
		return requestURI;
	}
	/** 获取地址栏参数 */
	public String getParater(String key) {
		return parameters.get(key)!=null?parameters.get(key):"";
	}
	/** 获取协议版本 */
	public String getProtocol() {
		return protocol;
	}
	/** 获取指定请求行的对应值 */
	public String getParamenter(String name){
		return headers.get(name);
	}
}
