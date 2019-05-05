package indi.cyken.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import indi.cyken.constant.FEIPCodeEnum;
import indi.cyken.domain.Category;
import indi.cyken.service.CategoryService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.JsonUtil;
import indi.cyken.utils.WriteBackUtil;
import net.sf.json.JSONObject;


/**
 * 课本分类
 */
public class CategoryServlet extends BaseServlet {

	/**
	 * 查询所有的分类
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.调用categoryservice 查询所有的分类 返回值list
		System.out.println("调用了CategoryServlet findAll");
		String sessionId = request.getHeader("Cookie");
		System.out.println("传到后端的登录态"+sessionId);
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> clist = null;
		try {
			clist = cs.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 2.将返回值转成json格式 返回到页面上
		//request.setAttribute("clist", clist);
		String json = JsonUtil.list2json(clist);
		
		//3.写回去
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println(json);
		
		return null;
	}
	

	/**
	 * 微信端获取所有课本分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String WXGetAllCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.调用categoryservice 查询所有的分类 返回值list
		System.out.println("调用了CategoryServlet WXGetAllCategory");
		String userid = request.getParameter("userid");
		System.out.println("用户ID"+userid);
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> clist = null;
		try {
			clist = cs.findAll();
			String json = JsonUtil.list2json(clist);
			JSONObject data=new JSONObject();
			data.put("categoryList", json);
			WriteBackUtil.WriteBackJsonStr(1, FEIPCodeEnum.OK.getCode(), FEIPCodeEnum.OK.getMsg(), data, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	

}
