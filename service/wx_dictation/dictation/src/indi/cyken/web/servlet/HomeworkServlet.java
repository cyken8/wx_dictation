package indi.cyken.web.servlet;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import indi.cyken.constant.Constant;
import indi.cyken.constant.FEIPCodeEnum;
import indi.cyken.domain.Course;
import indi.cyken.domain.Homework;
import indi.cyken.domain.HomeworkScore;
import indi.cyken.domain.Score;
import indi.cyken.domain.User;
import indi.cyken.domain.Word;
import indi.cyken.dto.Result;
import indi.cyken.service.HomeworkService;
import indi.cyken.service.ScoreService;
import indi.cyken.service.UserService;
import indi.cyken.service.WordService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.UUIDUtils;
import indi.cyken.utils.WriteBackUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class HomeworkServlet
 */

public class HomeworkServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;


	
	/**
	 * 添加作业
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WXAddWordHomework(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String classid = request.getParameter("classid");
		String wordListJsonstr = request.getParameter("wordListJsonstr");
		String hwname = request.getParameter("hwname"); // 作业名
		System.out.println("作业名："+hwname);
		System.out.println("classid = " + classid + " wordListJsonstr = " + wordListJsonstr);
		JSONArray wordListObj = JSONArray.fromObject(wordListJsonstr);
		List<String> list = new LinkedList<>();
		for (int i = 0; i < wordListObj.size(); i++) {
			System.out.println(wordListObj.get(i));
			list.add((String) wordListObj.get(i));
		}
		HomeworkService ws = (HomeworkService) BeanFactory.getBean("HomeworkService");
		String hwid = UUIDUtils.getCode();
		int ret = ws.addHomework(hwid, hwname, classid, list);
		JSONObject data = new JSONObject();
		if (ret != -1) {
			WriteBackUtil.WriteBackJsonStr(1, FEIPCodeEnum.OK.getCode(), FEIPCodeEnum.OK.getMsg(), data, response);
			return "success";
		} else {
			WriteBackUtil.WriteBackJsonStr(0, FEIPCodeEnum.FAIL.getCode(), FEIPCodeEnum.FAIL.getMsg(), data, response);
			return "fail";
		}

	}

	/**
	 * 获取作业
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WXGetAllHomework(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1.获取userid和wordlist
		String classid = request.getParameter("classid");
		System.out.println("classid: "+classid);
		HomeworkService hs = (HomeworkService) BeanFactory.getBean("HomeworkService");
		List<Homework> hlist = null;
		JSONObject data = new JSONObject();
		try {
			hlist = hs.getAllHomeworkByUid(classid);
			if (hlist != null) {
				data.put("homeworkList", hlist);
				WriteBackUtil.WriteBackJsonStr(1, FEIPCodeEnum.OK.getCode(), FEIPCodeEnum.OK.getMsg(), data, response);
				return "success";
			} else {
				WriteBackUtil.WriteBackJsonStr(0, FEIPCodeEnum.FAIL.getCode(), FEIPCodeEnum.FAIL.getMsg(), data,response);
				return "fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	/**
	 * 根据作业编号获取作业下的所有单词
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WXGetAllWordByHWid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String hwid = request.getParameter("hwid");		//作业编号
		System.out.println("hwid: "+hwid);
		HomeworkService hs = (HomeworkService) BeanFactory.getBean("HomeworkService");
		List<Word> wlist = null;
		JSONObject data = new JSONObject();
		try {
			wlist = hs.getAllWordByHWid(hwid);
			if (wlist != null) {
				data.put("wordList", wlist);
				WriteBackUtil.WriteBackJsonStr(1, FEIPCodeEnum.OK.getCode(), FEIPCodeEnum.OK.getMsg(), data, response);
				return "success";
			} else {
				WriteBackUtil.WriteBackJsonStr(0, FEIPCodeEnum.FAIL.getCode(), FEIPCodeEnum.FAIL.getMsg(), data,response);
				return "fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	
	/**
	 * 微信端添加成绩
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WXAddHomeworkScore(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取参数
		String scoreNum = request.getParameter("score");
		String userid = request.getParameter("userid");
		String hwid =request.getParameter("hwid");
		
		System.out.println("score:"+scoreNum+"userid"+userid+"hwid"+hwid);
	
		//2.组成成score
		User user = new User(userid);
		Homework homework = new Homework(hwid);
		HomeworkScore hwScore=new HomeworkScore(user,homework,Double.valueOf(scoreNum),true);
		
	
		//4、.调用Service
		ScoreService ss=(ScoreService) BeanFactory.getBean("ScoreService");
		int ret=ss.addHomeworkScore(hwScore);
		if(ret<=0) {
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
	 * 删除作业
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WXDelOneHomework(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		//1.获取参数
		String hwid=request.getParameter("hwid");
	
		//2.删除用户
		HomeworkService us = (HomeworkService) BeanFactory.getBean("HomeworkService");
		int ret=us.delOneHomeworkByHwid(hwid);

		JSONObject jsonObj=new JSONObject();
		if(ret>0) {
			jsonObj.put("code", FEIPCodeEnum.OK.getCode());
			jsonObj.put("state",1 );
			jsonObj.put("data","{}" );
			WriteBackUtil.WriteBackJsonStr(jsonObj, response);
		}else {
			jsonObj.put("code", FEIPCodeEnum.FAIL.getCode());
			jsonObj.put("state",0 );
			jsonObj.put("data","{}" );
			WriteBackUtil.WriteBackJsonStr(jsonObj, response);
		}
		
	return null;
	
	}
	
}
