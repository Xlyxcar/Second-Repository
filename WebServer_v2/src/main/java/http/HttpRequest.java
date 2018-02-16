package http;

import java.io.IOException;
import java.io.InputStream;

/**
 * 负责解析客户端请求
 * @author asd99
 *
 */
public class HttpRequest {
	InputStream in;
	public HttpRequest(InputStream in) {
		this.in = in;
	}
	/**
	 * 打印客户端请求
	 */
	public void print(){
		try {
			int i = -1;
			while((i=in.read())!=-1){
				System.out.print((char)i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
