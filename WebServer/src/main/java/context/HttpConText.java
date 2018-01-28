package context;

import java.util.HashMap;
import java.util.Map;

public class HttpConText {
	public static final String HEADER_CONTENT_TYPE = "Content-Type";
	public static final String HEADER_CONTENT_LENGTH = "Content-Length";
	
	//回车,换行
	public static final int CR = 13;
	public static final int LF = 10;
	
	public static Map<String, String> mimeTypeMapping = new HashMap<String, String>();
	
	static{
		init();
	}
	//添加不同介质类型对应的介质信息
	private static void init() {
		mimeTypeMapping.put("html","text/html");
		mimeTypeMapping.put("css", "text/css");
		mimeTypeMapping.put("jpg", "image/jpg");
		mimeTypeMapping.put("png", "image/png");
		mimeTypeMapping.put("js", "application/javascript");
	}
	/**
	 * 获取介质对应的映射信息
	 * @param extension
	 * @return
	 */
	public static String getMimeType(String extension){
		return mimeTypeMapping.get(extension);
	}
}
