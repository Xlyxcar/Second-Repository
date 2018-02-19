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
 * 有关xml配置文件的配置信息
 * @author asd99
 *
 */
public class ServerContext {
	private static Map<String, String> servletMapping = new HashMap<String,String>(); //存放访问uri对应的servlet
	//端口号,协议版本,线程池数,字符集编码
	private static String port,protocol,threadPoolSum,URIEncoding;
	static{
		init();
		initServletConfig();
	}
	/**
	 * 读取servlet.xml中的数据(uri,class),存入servletMapping
	 */
	private static void init() {
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new File("conf/servlets.xml"));
			Element root = doc.getRootElement();
			List<Element>arr = root.elements();
			for(Element e:arr){
				String uri = e.attributeValue("uri");
				String cls = e.attributeValue("class");
				servletMapping.put(uri, cls);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 读取配置文件(servlets.xml)并为服务端配置信息赋值
	 */
	private static void initServletConfig() {
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new File("conf/conf.xml"));
			Element root = doc.getRootElement();
			Element e = root.element("Connector");
			port = e.attributeValue("port");
			protocol = e.attributeValue("protocol");
			threadPoolSum = e.attributeValue("poolSum");
			URIEncoding = e.attributeValue("URIEncoding");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** 通过uri获取对应的Servlet */
	public static String getServletNameByURI(String uri){
		return servletMapping.get(uri);
	}
	/** 获取端口号 */
	public static String getPort() {
		return port;
	}
	/** 协议版本 */
	public static String getProtocol() {
		return protocol;
	}
	/** 获取线程池数 */
	public static String getThreadPoolSum() {
		return threadPoolSum;
	}
	/** 获取解码字符集 */
	public static String getURIEncoding() {
		return URIEncoding;
	}
}
