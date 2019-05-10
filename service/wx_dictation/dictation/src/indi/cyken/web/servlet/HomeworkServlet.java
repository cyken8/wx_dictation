package indi.cyken.web.servlet;

import java.util.LinkedList;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import indi.cyken.constant.FEIPCodeEnum;
import indi.cyken.domain.Homework;
import indi.cyken.domain.Word;
import indi.cyken.service.HomeworkService;
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
	 * 根据用户id和传递过来的单词列表json字符串更新用户对单词的筛选状态
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
}
