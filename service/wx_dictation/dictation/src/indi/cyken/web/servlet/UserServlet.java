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

	
	public String registTeacher(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.封装数据
		System.out.println("调用了registTeacher Servlet");
		User user = new User();
		//注册自定义转化器
		ConvertUtils.register(new MyConventer(), Date.class);
		BeanUtils.populate(user, request.getParameterMap());
		System.out.println("接收到的参数:" +user.getUsername()+ "  "+user.getPassword());
		
		//1.1 设置用户id
		user.setUserid("U"+UUIDUtils.getId());
		System.out.println("生成的用户ID:"+user.getUserid());
	
		//1.3加密密码
		user.setPassword(MD5Utils.md5(user.getPassword()));
		
		//1.4设置用户角色
		user.setUsertypeid("UT004");
		//1.5设置用户状态
		user.setState("1");
		
		//2.调用service完成注册
		UserService s=new UserServiceImpl();
		s.registTeacher(user);
		
		JSONObject jsonObject = new JSONObject();
        jsonObject.put("state",1);
        jsonObject.put("code",0);
        jsonObject.put("msg","注册教师成功");
        response.getWriter().print(jsonObject);
		
		//3.页面请求转发
		//request.setAttribute("msg", "用户注册已成功,请去邮箱激活~~");
		
		return "success";
	}	


	
	/**
	 * 编辑用户信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String editUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.封装数据
		System.out.println("调用了editUserInfo Servlet");
		User user = new User();
		//注册自定义转化器
		ConvertUtils.register(new MyConventer(), Date.class);
		BeanUtils.populate(user, request.getParameterMap());
		System.out.println("接收到的参数:" +user.getUsername()+"  "+user.getSex());
	
		//1.4设置用户角色
		user.setUsertypeid("UT003");		//用户类型UT003=学生
		//1.5设置用户状态
		user.setState("1");					//用户状态1=启用
		
		//2.调用service完成注册
		UserService s=new UserServiceImpl();
		s.registTeacher(user);
		
		JSONObject jsonObject = new JSONObject();
        jsonObject.put("state",1);
        jsonObject.put("code",0);
        jsonObject.put("msg","编辑个人信息成功");
        response.getWriter().print(jsonObject);
		
		return "success";
	}	
	
	
	
	/**
	 * 教师网页版登录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String teacherLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取用户名和密码
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		HttpSession session = request.getSession();
		String sessionid=session.getId();
		JSONObject dataJson = new JSONObject();
		dataJson.put("access_token", sessionid);
		JSONObject jsonObject = new JSONObject();
        jsonObject.put("state",0);
        jsonObject.put("code",0);
        jsonObject.put("data", dataJson);
        
		password=MD5Utils.md5(password);
		
		//2.调用serive完成登录操作 返回user
		UserService s=new UserServiceImpl();
		User user=s.login(username,password);
		
		//3.判断用户
		if(user==null){
			//用户名密码不匹配
			request.setAttribute("msg", "用户名密码不匹配");
	        response.getWriter().print(jsonObject);
			return "fail:用户名密码不匹配";
		}
	
		//4.将user放入session中 重定向
		request.getSession().setAttribute("user", user);

        jsonObject.put("state",1);
        jsonObject.put("code",0);
        jsonObject.put("msg","登入成功");
        response.getWriter().print(jsonObject);
        System.out.println("登录返回前端的数据"+jsonObject.toString());
		return "success";
	}	
	
	/**
	 * 教师网页版登出
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String teacherLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//干掉session
		request.getSession().invalidate();
		
		//重定向
		//response.sendRedirect(request.getContextPath());
		
		//处理自动登录
		
		return null;
	}
	
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
