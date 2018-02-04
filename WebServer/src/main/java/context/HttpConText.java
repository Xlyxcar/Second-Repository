package context;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class HttpConText {
	/*
	 * HTTP头信息
	 */
	//数据数据类型
	public static final String HEADER_CONTENT_TYPE = "Content-Type";
	//数据长度(大小)
	public static final String HEADER_CONTENT_LENGTH = "Content-Length";
	//重定向位置
	public static final String HEADER_LOCATION = "Location";
	
	//回车&换行字符编码
	public static final int CR = 13;
	public static final int LF = 10;
	
	//存储不同介质类型对应的介质信息(如:html=text/html)
	public static Map<String, String> mimeTypeMapping = new HashMap<String, String>();
	
	//状态码信息
	public static final int STATUS_CODE_OK = 200;
	public static final int STATUS_CODE_NOT_FOUND = 404;
	public static final int STATUS_CODE_ERROR = 500;
	public static final int STATUS_CODE_REDIRECT = 302;
	
	//状态码对应描述信息
	static Map<Integer,String> codeReasonMap = new HashMap<Integer,String>();
	
	static{
		init();
	}
	private static void init() {
		initMimeTypeMapping();
		initCodeReason();
	}
	/**
	 * 添加不同介质类型对应的介质信息
	 */
	private static void initMimeTypeMapping() {
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new File("conf/web.xml"));
			//获取根节点
			Element root = doc.getRootElement();
			//获取所有介质信息节点
			List<Element> elements = root.elements("mime-mapping");
			for(Element e:elements){
				//读取并保存介质信息节点内容
				mimeTypeMapping.put(e.elementText("extension"), e.elementText("mime-type"));
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 添加状态码对应描述信息
	 */
	private static void initCodeReason() {
		codeReasonMap.put(200, "OK");
		codeReasonMap.put(404, "NOT_FOUND");
		codeReasonMap.put(500, "ERROR");
		codeReasonMap.put(302, "MOVED PERMANENTLY");
	}
	/**
	 * 获取介质对应的映射信息
	 * @param extension
	 * @return
	 */
	public static String getMimeType(String extension){
		return mimeTypeMapping.get(extension);
	}
	/**
	 * 获取状态码对应描述信息
	 * @param code
	 * @return
	 */
	public static String getStatusReasonByStatusCode(int code){
		return codeReasonMap.get(code);
	}
}
