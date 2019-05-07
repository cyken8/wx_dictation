package indi.cyken.web.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import indi.cyken.constant.Constant;
import indi.cyken.constant.FEIPCodeEnum;
import indi.cyken.converter.MyConventer;
import indi.cyken.domain.User;
import indi.cyken.domain.UserTwo;
import indi.cyken.service.BookService;
import indi.cyken.service.SessionService;
import indi.cyken.service.UserService;
import indi.cyken.service.impl.UserServiceImpl;
import indi.cyken.utils.AesCbcUtil;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.HttpRequest;
import indi.cyken.utils.JsonUtil;
import indi.cyken.utils.MD5Utils;
import indi.cyken.utils.UUIDUtils;
import indi.cyken.utils.WriteBackUtil;
import net.sf.json.JSONObject;

/**
 * 用户注册教师版
 */

public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;


	
	/**
	 * 通过sessionId获取用户信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String getBySessionId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("调用了getBySessionId Servlet");
		String sessionId=request.getParameter("sessionId");
		System.out.println("接收到的session_key: "+ sessionId);
		UserService us = (UserService) BeanFactory.getBean("UserService");

		try {
			User user = us.getUserBySessionId(sessionId);
			if(user!=null&&user.getUserid().length()>0) {
				JSONObject json=new JSONObject();
				json.put("data", user);
				response.getWriter().write(JsonUtil.object2json(json));
				return "succes:成功根据sessionId获取用户信息";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("fail:获取用户信息失败");
		}
	
		return "fail:获取用户信息失败";
	}
	
	
	//////////////////////////////////////////////修改登录版本之后///////////////////////
	
	/**
	 * 微信端用户登录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WXLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取用户名和密码
		String userid=request.getParameter("userid");
		String password=request.getParameter("password");
		System.out.println("userid: "+userid+"password: "+password);
		
		//2.调用serive完成登录操作 返回user
		UserService us = (UserService) BeanFactory.getBean("UserService");
		UserTwo user=us.queryByUidAndPass(userid,password);
		JSONObject data=new JSONObject();
		if(user!=null) {
			data.put("isAuthorized", true);
			data.put("userInfo", JsonUtil.object2json(user));
			WriteBackUtil.WriteBackJsonStr(1, FEIPCodeEnum.OK.getCode(),FEIPCodeEnum.OK.getMsg(),data,response);
			return "success";
		}else{
			data.put("isAuthorized", false);
			WriteBackUtil.WriteBackJsonStr(1, FEIPCodeEnum.USER_NOTEXIST.getCode(),FEIPCodeEnum.USER_NOTEXIST.getMsg(),data,response);
			return "fail";
		}
     
	}	
	
	
	
	
	/**
	 * 通过sessionId获取用户信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String getUserInfoByUid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("调用了getUserInfoByUid");
		String userid=request.getParameter("userid");
		System.out.println("接收到的userid: "+ userid);
		UserService us = (UserService) BeanFactory.getBean("UserService");
		try {
			UserTwo user = us.getUserInfoByUid(userid);
			JSONObject data=new JSONObject();
			if(user!=null&&user.getUserid().length()>0) {
				data.put("userInfo", user);
				WriteBackUtil.WriteBackJsonStr(1, FEIPCodeEnum.OK.getCode(), FEIPCodeEnum.OK.getMsg(), data, response);
				return "success";
			}else {
				WriteBackUtil.WriteBackJsonStr(1, FEIPCodeEnum.USER_NOTEXIST.getCode(), FEIPCodeEnum.USER_NOTEXIST.getMsg(), data, response);
				return "fail";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;

	}
	
	
	
	
	

	
	
	
	
	
	
	

}
