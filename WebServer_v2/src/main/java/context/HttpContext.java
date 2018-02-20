package context;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 存储各种信息供其他类调用
 * @author asd99
 *
 */
public class HttpContext {
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String CONTENT_LENGTH = "Content-Length";
	public static final String LOCATION = "Location";

	public static final int CR = 13; //回车符
	public static final int LF = 10; //换行符
	
	//介质类型映射信息
	public static Map<String,String> mimeTypeMapping = new HashMap<String, String>();
	
	public static final int STATE_CODE_OK = 200;
	public static final int STATE_CODE_NOT_FOUND = 404;
	public static final int STATE_CODE_ERROR = 500;
	public static final int STATE_CODE_REDIRECT = 302;
	private static Map<Integer,String> codeReasonMap = new HashMap<Integer,String>();
	static{
		initMimeTypeMapping();
		initCodeReason();
	}
	/**
	 * 读取web.xml,初始化介质类型映射信息
	 */
	private static void initMimeTypeMapping() {
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(new File("conf/web.xml"));
			Element root = doc.getRootElement();
			List<Element> list = root.elements("mime-mapping");
			for(Element e:list){
				String key = e.elementText("extension");
				String value = e.elementText("mime-type");
				mimeTypeMapping.put(key, value);
			}
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化状态代码描述信息
	 */
	private static void initCodeReason() {
		codeReasonMap.put(200, "OK");
		codeReasonMap.put(404, "NOT_FOUND");
		codeReasonMap.put(500, "ERROR");
		codeReasonMap.put(302, "REDIRECT");
	}
	/**
	 * 根据资源后缀获取HTTP规定对应的介质类型
	 * @param extension
	 * @return
	 */
	public static String getMimeType(String extension){
		return mimeTypeMapping.get(extension);
	}
	/**
	 * 根据给定状态码获取对应的状态描述
	 * @param code
	 * @return
	 */
	public static String getStatusReasonByStatusCode(int code){
		return codeReasonMap.get(code);
	}
}
