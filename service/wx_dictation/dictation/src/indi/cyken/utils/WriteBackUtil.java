package indi.cyken.utils;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import indi.cyken.domain.FEIPResult;
import net.sf.json.JSONObject;


/**
 * 写回数据到前端的工具类
 * @author Yong
 *
 */

public class WriteBackUtil {

	/**
	 * 将对象转换为json字符串写回前端
	 * @param result
	 * @param response
	 * @throws Exception
	 */
	public static void WriteBackJsonStr(FEIPResult result, HttpServletResponse response) throws Exception{
		response.setContentType("text/json");
		response.setHeader("Cache-Control", "no-cache");
	    response.setCharacterEncoding("UTF-8");
	    PrintWriter writer = response.getWriter();
	    JSONObject jsonObject = JSONObject.fromObject(result);
		String jsonstr=jsonObject.toString();
    	writer.write(jsonstr);   	
    	writer.close();
	}
	
	/**
	 * 将json字符串写回前端
	 * @param jsonstr
	 * @param response
	 * @throws Exception
	 */
	public static void WriteBackJsonStr(String jsonstr,HttpServletResponse response) throws Exception{
		response.setContentType("text/json");
		response.setHeader("Cache-Control", "no-cache");
	    response.setCharacterEncoding("UTF-8");
	    PrintWriter writer = response.getWriter();
    	writer.write(jsonstr);   	
    	writer.close();
	}

}
