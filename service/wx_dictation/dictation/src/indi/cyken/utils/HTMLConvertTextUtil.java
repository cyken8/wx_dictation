package indi.cyken.utils;

import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 提取HTML标签中的文字
 * @author Administrator
 *
 */
public class HTMLConvertTextUtil {

	/**
	 * 将多个相同标签里的文字分别存进List
	 * @param text
	 * @return
	 */
	public static List<String> convertManyText(String text){
		if (text != null) {
			List<String> list=new LinkedList<>();

		//将string文本转换成html文本
			Document doc = Jsoup.parse(text);
			//获得所有p标签
			Elements links = doc.getElementsByTag("p");
			//实例化stringbuffer
			StringBuffer buffer =new StringBuffer();
			for (Element link : links) {
				list.add(link.text().trim());
				//将文本提前出来
				buffer.append(link.text().trim());
			}
				//return buffer.toString().trim();
			return list;
		}
		return null;
	}
	
	/**
	 * 将多个相同标签里的文字组合成字符串
	 * @param text
	 * @return
	 */
	public static String convertOneText(String text){
		if (text != null) {
		//将string文本转换成html文本
		Document doc = Jsoup.parse(text);
		//获得所有p标签
		Elements links = doc.getElementsByTag("p");
		//实例化stringbuffer
		StringBuffer buffer =new StringBuffer();
		for (Element link : links) {
		//将文本提前出来
		buffer.append(link.text().trim());
		}
		return buffer.toString().trim();
		}
		return null;
	}


}
