package indi.cyken.web.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import indi.cyken.constant.Constant;
import indi.cyken.constant.FEIPCodeEnum;
import indi.cyken.domain.Book;
import indi.cyken.domain.Collection;
import indi.cyken.domain.CollectionItem;
import indi.cyken.domain.Course;
import indi.cyken.domain.Score;
import indi.cyken.domain.User;
import indi.cyken.service.BookService;
import indi.cyken.service.CollectionService;
import indi.cyken.service.ScoreService;
import indi.cyken.service.UserService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.DateUtil;
import indi.cyken.utils.JsonUtil;
import indi.cyken.utils.UUIDUtils;
import indi.cyken.utils.WriteBackUtil;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

/**
 * Servlet implementation class ScoreServlet
 */

public class ScoreServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * 微信端添加成绩
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WXAddScore(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取参数
		System.out.println("调用了ScoreServlet  WXAddScore方法");
		String scoreNum = request.getParameter("score");
		String userid = request.getParameter("userid");
		String courseid =request.getParameter("courseid");
		
		System.out.println("score:"+scoreNum+"userid"+userid+"courseid"+courseid);
	
		//2.组成成score
		Score score=new Score();
		score.setScid(Constant.PREFIX_SCORE+UUIDUtils.getCode());
		User user=new User();
		user.setUserid(userid);
		score.setUser(user);
		Course course=new Course();
		course.setCourseid(courseid);
		score.setUser(user);
		score.setCourse(course);
		score.setScore(Double.valueOf(scoreNum));
		score.setScstate(true);
		Timestamp createtime = new Timestamp(System.currentTimeMillis());
		score.setCreatetime(createtime);
		
		
		//4、.调用Service
		ScoreService ss=(ScoreService) BeanFactory.getBean("ScoreService");
		int ret=ss.add(score);
		System.out.println("添加成绩sql返回的值："+ret);
		if(ret==-1) {
			System.out.println("添加成绩失败");
			JSONObject json=new JSONObject();
			json.put("state", 0);
			json.put("code",FEIPCodeEnum.FAIL.getCode());
			json.put("msg", FEIPCodeEnum.FAIL.getMsg());
			json.put("data", scoreNum);
			WriteBackUtil.WriteBackJsonStr(json, response);
			return "添加成绩失败";
		}else {
			JSONObject json=new JSONObject();
			json.put("state", 1);
			json.put("code", FEIPCodeEnum.OK.getCode());
			json.put("msg", FEIPCodeEnum.OK.getMsg());
			json.put("data", scoreNum);
			WriteBackUtil.WriteBackJsonStr(json, response);
			return "添加成绩成功";
			
		}
	}
	
	
	/**
	 * 获取用户所有听写过的课时的最新成绩
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WXGetScoreAllCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取参数
		System.out.println("调用了ScoreServlet  WXGetScoreAllCourse 方法");
		String userid = request.getParameter("userid");
		System.out.println("userid："+userid);

		//4、.调用Service
		ScoreService ss=(ScoreService) BeanFactory.getBean("ScoreService");
		List<Score>  list=ss.getScoreAllCourse(userid);
		if(list==null) {
			System.out.println("添加成绩失败");
			JSONObject json=new JSONObject();
			json.put("state", 0);
			json.put("code",FEIPCodeEnum.FAIL.getCode());
			json.put("msg", FEIPCodeEnum.FAIL.getMsg());
			json.put("data", "{}");
			WriteBackUtil.WriteBackJsonStr(json, response);
			return "添加成绩失败";
		}else {
			String jsonstr=JsonUtil.list2json(list);
			JSONObject json=new JSONObject();
			json.put("state", 1);
			json.put("code", FEIPCodeEnum.OK.getCode());
			json.put("msg", FEIPCodeEnum.OK.getMsg());
			json.put("data", jsonstr);
			WriteBackUtil.WriteBackJsonStr(json, response);
			return "添加成绩成功";
			
		}
	}
	
	
	
	/**
	 * 获取某课时的所有历史成绩
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WXGetScoreOneCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取参数
		System.out.println("调用了ScoreServlet  WXGetScoreOneCourse 方法");
		String userid = request.getParameter("userid");
		String courseid=request.getParameter("courseid");
		System.out.println("userid："+userid+"courseid: "+courseid);


		//4、.调用Service
		ScoreService ss=(ScoreService) BeanFactory.getBean("ScoreService");
		List<Score>  list=ss.getScoreOneCourse(userid,courseid);
		if(list==null) {
			System.out.println("添加成绩失败");
			JSONObject json=new JSONObject();
			json.put("state", 0);
			json.put("code",FEIPCodeEnum.FAIL.getCode());
			json.put("msg", FEIPCodeEnum.FAIL.getMsg());
			json.put("data", "{}");
			WriteBackUtil.WriteBackJsonStr(json, response);
			return "获取某课时的所有历史成绩失败";
		}else {
			String jsonstr=JsonUtil.list2json(list);
			JSONObject json=new JSONObject();
			json.put("state", 1);
			json.put("code", FEIPCodeEnum.OK.getCode());
			json.put("msg", FEIPCodeEnum.OK.getMsg());
			json.put("data", jsonstr);
			WriteBackUtil.WriteBackJsonStr(json, response);
			return "获取某课时的所有历史成绩成功";
			
		}
	}
	

}
