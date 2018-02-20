package http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Base64.Decoder;
import java.util.HashMap;
import java.util.Map;

import context.HttpContext;
import context.ServerContext;

/**
 * 负责解析客户端请求
 * @author asd99
 *
 */
public class HttpRequest {
	//输入流接收客户端发送信息
	private InputStream in;
	
	//请求类型,地址,协议
	private String method,url,protocol;
	
	//消息头信息以键值对存放
	private Map<String,String> headers = new HashMap<String, String>();
	
	//分别存储地址栏具体请求路径与参数(如果存在)
	private String requestURI,queryString; 
	
	//保存地址栏参数(如果存在)
	private Map<String,String> parameters = new HashMap<String,String>();
	
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
		parseContent();
	}
	/**
	 * 解析消息正文
	 */
	private void parseContent() {
		//如果存在正文长度,并且正文类型为表单
		if(headers.containsKey(HttpContext.CONTENT_LENGTH) && "application/x-www-form-urlencoded".equals(headers.get(HttpContext.CONTENT_TYPE))){
			try {
				int len = Integer.parseInt(headers.get(HttpContext.CONTENT_LENGTH));
				byte[] data = new byte[len];
				in.read(data);
				String query = new String(data);
				parseQueryString(query);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/** 
	 * 解析消息头 
	 * 循环读取(直到读取到空字符串),以":"拆分字符串,并将":"两端的值去除空字符串后,作为键值对放入数组
	 */
	private void parseHeaders() {
		while(true){
			String line = readLine();
			if(line.equals("")) break;
			int index = line.indexOf(":");
			String key = line.substring(0, index).trim();
			String value = line.substring(index+1).trim();
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
		
		String[] arr = line.split("\\s");
		method = arr[0];
		url = arr[1];
		protocol = arr[2];
		
		if(url.indexOf("?")!=-1){
			parseUrl();
		}else{
			requestURI = url;
		}
	}
	
	/**
	 * 解析Url(如果请求链接后附带参数)
	 * 将参数放入parameters中
	 */
	private void parseUrl() {
		requestURI = url.substring(0,url.indexOf("?"));
		queryString = url.substring(url.indexOf("?")+1);
		parseQueryString(queryString);
	}
	/**
	 * 解析表单提交数据并放入parameters中
	 * @param query
	 */
	private void parseQueryString(String query){
		try {
			query = URLDecoder.decode(query,ServerContext.getURIEncoding());
			String[] arr = query.split("&");
			for(String str:arr){
				int index = str.indexOf("=");
				String key = str.substring(0,index);
				String value = str.substring(index+1);
				parameters.put(key, value);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
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
	public String getHeader(String name){
		return headers.get(name);
	}
}
