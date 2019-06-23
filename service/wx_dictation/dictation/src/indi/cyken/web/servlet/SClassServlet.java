package indi.cyken.web.servlet;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import indi.cyken.constant.FEIPCodeEnum;
import indi.cyken.domain.SClass;
import indi.cyken.dto.Result;
import indi.cyken.dto.UserClassMapDto;
import indi.cyken.service.SClassService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.JsonUtil;
import indi.cyken.utils.WriteBackUtil;
import net.sf.json.JSONObject;

/**
 * 班级Servlet
 */

public class SClassServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	
	/**
	 * 根据教师id获取教师下的所有班级
	 */
	public String WXGetAllClassByUid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取课本的id
		String userid=request.getParameter("userid");
		//2.调用service
		SClassService ss = (SClassService) BeanFactory.getBean("SClassService");
		try {
			List<SClass> clist = ss.getAllClassByUid(userid);
			List<UserClassMapDto> retList=new LinkedList<>();
			JSONObject data=new JSONObject();
			if(clist!=null) {
				String jsonstr = JsonUtil.list2json(clist);
				data.put("classList",jsonstr );
				WriteBackUtil.WriteBackJsonStr(1, FEIPCodeEnum.OK.getCode(), FEIPCodeEnum.OK.getMsg(), data, response);
			}else {
				WriteBackUtil.WriteBackJsonStr(0, FEIPCodeEnum.OK.getCode(), FEIPCodeEnum.OK.getMsg(), data, response);

			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	} 
	
	
	/**
	 * 网页端根据教师id获取教师下的所有班级（与WXGetAllClassByUid知识通信格式不一样）
	 */
	public String WebGetAllClassByUidForDropList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取课本的id
		HttpSession session = request.getSession();
		String userid = (String)session.getAttribute("userid");
		//String userid=request.getParameter("userid");
		//2.调用service
		SClassService ss = (SClassService) BeanFactory.getBean("SClassService");
		Result result = new Result(0, "获取教师所有班级成功");
		try {
			List<SClass> clist = ss.getAllClassByUid(userid);
			if(clist!=null) {
				result.setResult(clist);
				WriteBackUtil.WriteBackJsonStr(result, response);
			}else {
				result.setErrorNo(1);
				result.setErrorInfo("获取教师所有班级失败");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	} 
 
 
}
