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

	public static final int CR = 13;
	public static final int LF = 10;
	
	public static Map<String,String> mimeTypeMapping = new HashMap<String, String>();
	static{
		init();
	}
	private static void init() {
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
	public static String getMimeType(String extension){
		return mimeTypeMapping.get(extension);
	}
	public static void main(String[] args) {
		System.out.println(mimeTypeMapping.toString());
	}
}
