package indi.cyken.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 实体工厂类
 * @author Administrator
 *
 */
public class BeanFactory {
	/**
	 * 通过给定一个id返回一个指定的实现类
	 * @param id
	 * @return
	 */
	public static Object getBean(String id){
		//通过id获取一个指定的实现类
		
		try {
			//1.获取document对象
			Document doc=new SAXReader().read(BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml"));
			//2.获取指定的bean对象 xpath
			Element ele=(Element) doc.selectSingleNode("//bean[@id='"+id+"']");
			
			//3.获取bean对象的class属性
			String value = ele.attributeValue("class");
			
			//4.反射	
			return Class.forName(value).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	public static void main(String[] args) {
//		System.out.println(getBean("ProductDao"));;
//	}
}
