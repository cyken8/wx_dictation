package indi.cyken.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import indi.cyken.domain.Course;
import indi.cyken.service.CourseService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.JsonUtil;
import indi.cyken.utils.WriteBackUtil;

/**
 * 课时Servlet
 */

public class CourseServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * 微信端根据unitid查询所有的课时
	 */
	public String WXGetBookByUnitId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取课本的id
		String unitid=request.getParameter("unitid");
		System.out.println("后端接收到的单元id: "+unitid);
		//2.调用service
		System.out.println("调用了CourseServlet中的 WXGetBookByUnitId方法");
		CourseService bs = (CourseService) BeanFactory.getBean("CourseService");
		List<Course> clist = null;
		try {
			clist = bs.getByUid(unitid);
			if(clist!=null) {
				String json = JsonUtil.list2json(clist);
				WriteBackUtil.WriteBackJsonStr(json, response);
				return "success:根据unitid查询所有的课时成功";

			}
		} catch (Exception e) {
			e.printStackTrace();
			return "fail:根据unitid查询所有的课时出现异常";

		}
		return "fail:根据unitid查询所有的课时失败";
	} 

}
