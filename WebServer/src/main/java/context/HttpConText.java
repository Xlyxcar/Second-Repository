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
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new File("conf/web.xml"));
			Element root = doc.getRootElement();
			List<Element> elements = root.elements("mime-mapping");
			for(Element e:elements){
				mimeTypeMapping.put(e.elementText("extension"), e.elementText("mime-type"));
			}
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
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
