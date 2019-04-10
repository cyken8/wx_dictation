package indi.cyken.web.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import indi.cyken.constant.Constant;
import indi.cyken.converter.MyConventer;
import indi.cyken.domain.User;
import indi.cyken.service.UserService;
import indi.cyken.service.impl.UserServiceImpl;
import indi.cyken.utils.AesCbcUtil;
import indi.cyken.utils.HttpRequest;
import indi.cyken.utils.MD5Utils;
import indi.cyken.utils.UUIDUtils;
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
	 * 获取微信用户信息保存进数据库
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String decodeUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.封装数据
		System.out.println("调用了decodeUserInfo Servlet");
		//2、获取参数
        String encryptedData=request.getParameter("encryptedData");
        String iv=request.getParameter("iv");
        String code=request.getParameter("code");

        //登录凭证不能为空
		JSONObject jsonObject = new JSONObject();
        if (code == null || code.length() == 0) {
	        jsonObject.put("state",0);
	        jsonObject.put("code",0);
	        jsonObject.put("msg","code 不能为空");
	        response.getWriter().print(jsonObject);  
	        return "fail";
        }     
        
        //小程序唯一标识   (在微信小程序管理后台获取)
        String wxspAppid = "wxf2b2f3649a584f77";
        //小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = "289fcf3163054ce7954e045dbd3a9ac3";
        //授权（必填）
        String grant_type = "authorization_code";

        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(sr);
        //获取会话密钥（session_key）
        String session_key = json.get("session_key").toString();
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");

        //////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
        try {
            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            if (null != result && result.length() > 0) {
            	jsonObject.put("status", 1);
            	jsonObject.put("msg", "解密成功");
                JSONObject userInfoJSON = JSONObject.fromObject(result);
                System.out.println(userInfoJSON);
                jsonObject.put("userInfo", result);
                response.getWriter().print(jsonObject); 
                return "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonObject.put("status", 0);
        jsonObject.put("msg", "解密失败");
        response.getWriter().print(jsonObject);  
		return "fail";
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
	
	
	

}
