package indi.cyken.web.servlet.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import indi.cyken.domain.Book;
import indi.cyken.domain.Unit;
import indi.cyken.dto.Result;
import indi.cyken.service.UnitService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.UUIDUtils;
import indi.cyken.utils.WriteBackUtil;
import indi.cyken.web.servlet.BaseServlet;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class UnitManageServlet
 */

public class UnitManageServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * 为一本课本添加一个单元
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebAddOneUnit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.获取参数
		String bookid=request.getParameter("bookid");
		String unitname=request.getParameter("unitname");

		Unit unit=new Unit(UUIDUtils.getCode(),unitname,new Book(bookid));
		//2.添加单元
		UnitService us = (UnitService) BeanFactory.getBean("UnitService");
		int ret=us.addOneUnit(unit);
		Result result = new Result(0, "添加单元成功");
		if(ret>0) {
			//添加成功
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("添加单元失败");
			WriteBackUtil.WriteBackJsonStr(result, response);	
		}
	
		return null;

	}
	
	/**
	 * 管理员获取某一课本下的所有单元
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebGetAllUnitByBid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.获取参数
		String bookid=request.getParameter("bookid");
		System.out.println("获取到的课本id: "+bookid);
		
		UnitService us = (UnitService) BeanFactory.getBean("UnitService");
		List<Unit> blist =us.getByBidForWeb(bookid);
		Result result = new Result(0, "获取单元成功");
		JSONObject data=new JSONObject();
		if(blist!=null) {
			data.put("list", blist);
			result.setResult(data);
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("获取单元失败");
		}
		return null;
	}
	
	
	/**
	 * 管理员获取某一课本下的所有单元(为网页端下拉框使用）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebGetAllUnitByBidForDropList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.获取参数
		String bookid=request.getParameter("bookid");
		System.out.println("获取到的课本id: "+bookid);
		
		UnitService us = (UnitService) BeanFactory.getBean("UnitService");
		List<Unit> blist =us.getByBidForWeb(bookid);
		Result result = new Result(0, "获取单元成功");
		if(blist!=null) {
			result.setResult(blist);
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("获取单元失败");
		}
		return null;
	}
	
	/**
	 * 管理员网页删除单元
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebDelOneUnit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.获取参数
		String unitid = request.getParameter("unitid");	//课时编号
		
		UnitService us=(UnitService) BeanFactory.getBean("UnitService");
		int ret=us.delOneUnit(unitid);
		Result result=new Result(0,"删除单元成功");
		if(ret>0) {
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("删除单元失败");
			WriteBackUtil.WriteBackJsonStr(result, response);
		}

		return null;
	}
	   

}
