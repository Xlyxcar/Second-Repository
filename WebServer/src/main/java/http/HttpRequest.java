package http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

//HTTP请求的格式分为三个部分组成:
//1:请求行
//2:请求头(消息头)
//3:请求正文(消息正文)

public class HttpRequest {
	InputStream in;
	//请求方法,链接,协议版本
	private String method,url,protocol;
	
	public HttpRequest(InputStream in) {
		this.in = in;
		parseRequestLine();
	}
	//解析请求行
	public void parseRequestLine(){
		String line = readLine();
		String[] arr = line.split("\\s");
		method = arr[0];
		url = arr[1];
		protocol = arr[2];
		System.out.println("请求方法:"+method+",请求链接:"+url+",协议版本:"+protocol);
	}
	//解析消息头
	//解析消息正文
	//读取一行消息
	private String readLine() {
		StringBuilder data = new StringBuilder();
		try{
			//读取字节,回车判断,换行判断
			int i,c1,c2=-1;
			while((i=in.read())!=-1){
				c1 = i;
				if(c2==13 && c1==10){
					break;
				}
				data.append((char)i);
				c2 = c1;
			}
			System.out.println("读了一行:"+data);
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
}
