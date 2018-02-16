package http;

import java.io.OutputStream;
import java.net.Socket;

/**
 * 负责处理响应客户端请求
 * @author asd99
 *
 */
public class HttpResponse {
	OutputStream out;
	public HttpResponse(OutputStream out) {
		this.out = out;
	}
}
