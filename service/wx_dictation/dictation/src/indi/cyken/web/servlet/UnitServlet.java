package indi.cyken.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import indi.cyken.domain.Book;
import indi.cyken.domain.Unit;
import indi.cyken.service.BookService;
import indi.cyken.service.UnitService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.JsonUtil;
import indi.cyken.utils.WriteBackUtil;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * Servlet implementation class UnitServlet
 */

public class UnitServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 微信端根据bookid查询所有的单元(也可用于web端）
	 */
	public String WXGetBookDetailByBookId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取课本的id
		String bookid=request.getParameter("bookid");
		System.out.println("后端接收到的课本id: "+bookid);
		//2.调用service
		System.out.println("调用了UnitServlet中的 WXGetBookByBookId方法");
		UnitService bs = (UnitService) BeanFactory.getBean("UnitService");
		List<Unit> blist = null;
		try {
			blist = bs.getByBid(bookid);
			if(blist!=null) {
				JsonConfig config1 = new JsonConfig();
				config1.setExcludes(new String[]{"unit"}); 
				//String json = JsonUtil.list2json(blist);
				String json=JSONArray.fromObject(blist, config1).toString();
				WriteBackUtil.WriteBackJsonStr(json, response);
				return "success:根据bookid查询所有的单元成功";

			}
		} catch (Exception e) {
			e.printStackTrace();
			return "fail:根据bookid查询所有的单元出现异常";

		}
		return "fail:根据bookid查询所有的单元失败";
	} 
       
 
}
