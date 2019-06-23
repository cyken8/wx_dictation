package indi.cyken.web.servlet.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import indi.cyken.domain.Course;
import indi.cyken.domain.Unit;
import indi.cyken.dto.Result;
import indi.cyken.service.CourseService;
import indi.cyken.service.UnitService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.UUIDUtils;
import indi.cyken.utils.WriteBackUtil;
import indi.cyken.web.servlet.BaseServlet;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class CourseManageServlet
 */

public class CourseManageServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * 为一个单元添加一个课时
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebAddOneCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.获取参数
		String unitid=request.getParameter("unitid");
		String coursename=request.getParameter("coursename");

		Course course=new Course(UUIDUtils.getCode(),coursename,new Unit(unitid));
		//2.添加课时
		CourseService us = (CourseService) BeanFactory.getBean("CourseService");
		int ret=us.addOneCourse(course);
		Result result = new Result(0, "添加课时成功");
		JSONObject data = new JSONObject();
		if(ret>0) {
			//添加成功
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("添加课时失败");
			WriteBackUtil.WriteBackJsonStr(result, response);	
		}
	
		return null;

	}  
	
	
	/**
	 * 管理员获取某一单元下的所有课时
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebGetAllCourseByUid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.获取参数
		String unitid=request.getParameter("unitid");
		System.out.println("获取到的单元id: "+unitid);
		
		CourseService cs = (CourseService) BeanFactory.getBean("CourseService");
		List<Course> clist = cs.getByUid(unitid);
		Result result = new Result(0, "获取课时成功");
		JSONObject data=new JSONObject();
		if(clist!=null) {
			data.put("list", clist);
			result.setResult(data);
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("获取课时失败");
		}
		return null;
	}
	
	/**
	 * 管理员获取某一单元下的所有课时
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebGetAllCourseByUidForDropList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.获取参数
		String unitid=request.getParameter("unitid");
		System.out.println("获取到的单元id: "+unitid);
		
		CourseService cs = (CourseService) BeanFactory.getBean("CourseService");
		List<Course> clist = cs.getByUid(unitid);
		Result result = new Result(0, "获取课时成功");
		if(clist!=null) {
			result.setResult(clist);
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("获取课时失败");
		}
		return null;
	}
	
	/**
	 * 管理员网页删除课时
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebDelOneCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.获取参数
		String courseid = request.getParameter("courseid");	//课时编号
		
		CourseService cs=(CourseService) BeanFactory.getBean("CourseService");
		int ret=cs.delOneCourse(courseid);
		Result result=new Result(0,"删除课时成功");
		if(ret>0) {
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("删除课时失败");
			WriteBackUtil.WriteBackJsonStr(result, response);
		}

		return null;
	}

}
