package indi.cyken.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;


import java.util.LinkedList;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import indi.cyken.constant.FEIPCodeEnum;
import indi.cyken.domain.User;
import indi.cyken.domain.UserTwo;
import indi.cyken.dto.Result;
import indi.cyken.dto.User.UserDto;
import indi.cyken.service.UserService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.JsonUtil;
import indi.cyken.utils.WriteBackUtil;
import net.sf.json.JSONObject;

/**
 * 用户注册教师版
 */

public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;


	
	
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
		User user=us.queryByUidAndPass(userid,password);
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
	 * 通过userid获取用户信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String getUserInfoByUid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
	
	
	/**
	 * 通过classid获取班级下所有的学生
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String WebGetAllStudentByClid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String classid=request.getParameter("classid");
		System.out.println("接收到的classid: "+ classid);
		UserService us = (UserService) BeanFactory.getBean("UserService");
		List<User> ul = us.getAllStudentByClid(classid);
		List<UserDto> list = new LinkedList<>();
		Result result = new Result(0, "获取所有用户成功");
		JSONObject data = new JSONObject();
		if (ul != null) {
			for (int i = 0; i < ul.size(); i++) {
				UserDto userDto = new UserDto();
				User userDao = ul.get(i);
				// BeanCopyUtil.copyPropertiesObj2Item(userDto, userDao);
				BeanUtils.copyProperties(userDto, userDao);
				userDto.setRole(userDao.getRole().getRolename());
				userDto.setProvince(userDao.getProvince().getProvincename());
				userDto.setCity(userDao.getCity().getCityname());
				userDto.setOrg(userDao.getOrg().getOrgname());
				userDto.setGrade(userDao.getGrade().getGradename());
				userDto.setSclass(userDao.getSclass().getClassname());
				list.add(userDto);
			}

			data.put("list", list);
			result.setResult("data", data);
			// 4.写回网页端
			WriteBackUtil.WriteBackJsonStr(result, response);
		} else {
			result.setErrorNo(1);
			WriteBackUtil.WriteBackJsonStr(result, response);
		}

		return null;


	}
	
	
	

	
	
	
	
	
	
	

}
