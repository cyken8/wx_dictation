package indi.cyken.web.servlet.admin;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import indi.cyken.domain.City;
import indi.cyken.domain.Grade;
import indi.cyken.domain.Organization;
import indi.cyken.domain.Province;
import indi.cyken.domain.Role;
import indi.cyken.domain.SClass;
import indi.cyken.domain.User;
import indi.cyken.dto.Result;
import indi.cyken.dto.User.UserDto;
import indi.cyken.service.UserService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.UUIDUtils;
import indi.cyken.utils.WriteBackUtil;
import indi.cyken.web.servlet.BaseServlet;
import net.sf.json.JSONObject;

/**
 * 网页端用户管理Servlet
 */

public class UserManageServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 获取全部用户信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebGetAllUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

		UserService us = (UserService) BeanFactory.getBean("UserService");
		List<User> ul = us.getAllUser();
		List<UserDto> list = new LinkedList<>();
		Result result = new Result(0, "获取所有用户成功");
		JSONObject data = new JSONObject();
		if (ul != null) {
			for (int i = 0; i < ul.size(); i++) {
				// JSONObject userDto=new JSONObject();
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

	/**
	 * 新增用户
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebAddUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1.获取参数
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String roleid = request.getParameter("role");
		String sex = request.getParameter("sex");
		String state = request.getParameter("state");
		String provinceid = request.getParameter("province");
		String cityid = request.getParameter("city");
		String orgid = request.getParameter("org");
		String gradeid = request.getParameter("grade");
		String sclassid = request.getParameter("sclass");

		// 2.添加用户
		User user = new User(UUIDUtils.getCode(), username, password, "avatar", sex, new Date(), "email",
				new Role(roleid), new Organization(orgid), new Grade(gradeid), new SClass(sclassid),
				new Province(provinceid), new City(cityid), "open_id", state);
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

	/**
	 * 删除一个用户
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebdelOneUserByUid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取参数
		String userid=request.getParameter("userid");
	
		//2.删除用户
		UserService us = (UserService) BeanFactory.getBean("UserService");
		int ret=us.delOneUserByUid(userid);
		Result result=new Result(0,"删除用户成功");
		if(ret>0) {
			//删除成功
			result.setResult(1);
			WriteBackUtil.WriteBackJsonStr(result, response);
			
		}else {
			result.setErrorNo(1);
			WriteBackUtil.WriteBackJsonStr(result, response);

		}
		
	

	return null;
	
	}

}
