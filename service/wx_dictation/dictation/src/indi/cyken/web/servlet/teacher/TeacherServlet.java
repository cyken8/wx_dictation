package indi.cyken.web.servlet.teacher;



import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import indi.cyken.constant.DBTableField;
import indi.cyken.domain.City;
import indi.cyken.domain.Grade;
import indi.cyken.domain.Organization;
import indi.cyken.domain.Province;
import indi.cyken.domain.Role;
import indi.cyken.domain.SClass;
import indi.cyken.domain.User;
import indi.cyken.domain.UserTwo;
import indi.cyken.dto.Result;
import indi.cyken.service.UserService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.UUIDUtils;
import indi.cyken.utils.WriteBackUtil;
import indi.cyken.web.servlet.BaseServlet;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class TeacherServlet
 */

public class TeacherServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
  
	
	/**
	 * 网页教师用户登录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebTeacherLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取用户名和密码
		String userid=request.getParameter("userid");
		String password=request.getParameter("password");
		System.out.println("userid: "+userid+"password: "+password);

		//2.调用serive完成登录操作 返回user
		UserService us = (UserService) BeanFactory.getBean("UserService");
		User user=us.queryByUidAndPass(userid,password);
		JSONObject data=new JSONObject();
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		System.out.println(user.getRole().getRoleid());
		if(user!=null && user.getRole().getRoleid().equals(DBTableField.USER_ROLE_TEACHER)) {
			//保存用户信息到session
			HttpSession session = request.getSession();
	        session.setAttribute("userid", userid);
			//用户存在且为教师身份
			 writer.write("True");
		}else{
			writer.write("False");
		}
		return null;
     
	}	
	
	
	/**
	 * 新增学生
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebAddStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1.获取参数
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String roleid = request.getParameter("role");
		String sex = request.getParameter("sex");
		String provinceid = request.getParameter("province");
		String cityid = request.getParameter("city");
		String orgid = request.getParameter("org");
		String gradeid = request.getParameter("grade");
		String sclassid = request.getParameter("sclass");

		// 2.添加用户
		User user = new User(UUIDUtils.getCode(), username, password, "avatar", sex, new Date(), "email",
				new Role(roleid), new Organization(orgid), new Grade(gradeid), new SClass(sclassid),
				new Province(provinceid), new City(cityid), "open_id", "1");
		UserService us = (UserService) BeanFactory.getBean("UserService");
		int ret = us.add(user);
		Result result = new Result(0, "添加用户成功");
		JSONObject data = new JSONObject();
		if (ret > 0) { // 添加用户成功
			// 写回前端添加用户成功提示
			result.setResult(1); // 按例子来
			WriteBackUtil.WriteBackJsonStr(result, response);
			System.out.println("添加用户成功");

			// 3.读取所有用户
			List<User> ul = us.getAllUser();
			List<Object> list = new LinkedList<>();
			for (int i = 0; i < ul.size(); i++) {
				JSONObject userinfo = new JSONObject();
				User tmpuser = ul.get(i);
				userinfo.put("userid", tmpuser.getUserid());
				userinfo.put("username", tmpuser.getUsername());
				userinfo.put("password", tmpuser.getPassword());
				userinfo.put("role", tmpuser.getRole().getRolename());
				list.add(userinfo);
			}

			data.put("list", list);
			result.setResult("data", data);
			// 4.写回网页端
			WriteBackUtil.WriteBackJsonStr(result, response);
		} else {
			// 添加用户失败
			result.setErrorNo(1); // 失败errorNo:1代表成功
			result.setResult(0); // 按例子来
			WriteBackUtil.WriteBackJsonStr(result, response);
		}

		return null;

	}
}
