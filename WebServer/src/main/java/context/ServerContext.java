package context;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ServerContext {
	static Map<String, String> servletMapping = new HashMap<String,String>();
	static{
		initServletMapping();
	}
	private static void initServletMapping() {
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new File("conf/servlets.xml"));
			Element root = doc.getRootElement();
			List<Element> els = root.elements("servlet");
			for(Element e:els){
				String uri = e.attributeValue("uri");
				String cls = e.attributeValue("class");
				servletMapping.put(uri, cls);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	public static String getServletNameByURI(String uri){
		return servletMapping.get(uri);
	}
}
