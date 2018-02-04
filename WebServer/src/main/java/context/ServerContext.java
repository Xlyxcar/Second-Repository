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
	//保存URI对应servlet类信息
	static Map<String, String> servletMapping = new HashMap<String,String>();
	//配置信息(端口号,协议版本,线程池容量,编码格式)
	public static String port,protocol,threadPoolSum,URIEncoding;
	
	static{
		initServletMapping();
		initServerConfig();
	}
	/**
	 * 从.xml文件中获取配置信息并写入静态区
	 */
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
	/**
	 * 解析config.xml并将其中的配置信息载入程序静态区
	 */
	private static void initServerConfig() {
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new File("conf/config.xml"));
			Element root = doc.getRootElement();
			Element e = root.element("Connect");
			port = e.attributeValue("port");
			protocol = e.attributeValue("protocol");
			URIEncoding = e.attributeValue("URIEncoding");
			threadPoolSum = e.attributeValue("threadPool");
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 通过URI获取对应servlet类名
	 * @param uri
	 * @return
	 */
	public static String getServletNameByURI(String uri){
		return servletMapping.get(uri);
	}
}
