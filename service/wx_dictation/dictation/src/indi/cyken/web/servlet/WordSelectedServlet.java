package indi.cyken.web.servlet;


import java.util.LinkedList;
import java.util.List;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import indi.cyken.constant.FEIPCodeEnum;
import indi.cyken.domain.Word;
import indi.cyken.service.WordSelectedService;
import indi.cyken.service.WordService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.JsonUtil;
import indi.cyken.utils.WriteBackUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class WordSelected
 */
public class WordSelectedServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * 根据用户id和课时id获取课时下被筛选过的单词
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WXGetSelectedWordsByUidCid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取userid和courseid
		System.out.println("调用了WordServlet 中的 WXGetSelectedWordsByUidCid ");
		String userid = request.getParameter("userid");
		String courseid = request.getParameter("courseid");
		System.out.println("userid = "+ userid+" courseid = "+courseid);
		
		//2.调用service 通过oid 返回值:order
		WordSelectedService ws=(WordSelectedService) BeanFactory.getBean("WordSelectedService");
		List<Word> wlist=ws.getSelectedWordsByUidCid(userid,courseid);
		if(wlist!=null) {
			String jsonstr = JsonUtil.list2json(wlist);
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("code", FEIPCodeEnum.OK.getCode());
			jsonObj.put("state", 1);
			jsonObj.put("data",jsonstr );
			String jsonstr2=JsonUtil.object2json(jsonObj);
			WriteBackUtil.WriteBackJsonStr(jsonstr2, response);
			return "success:获取课时下所有单词成功";
		}else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("state", 0);
			jsonObj.put("code", FEIPCodeEnum.VALUE_NULL.getCode());
			jsonObj.put("data","{}" );
			String jsonstr2=JsonUtil.object2json(jsonObj);
			WriteBackUtil.WriteBackJsonStr(jsonstr2, response);
			return "fail:获取课时下所有单词失败";
		}

		
	}
	
	/**
	 * 根据用户id和传递过来的单词列表json字符串更新用户对单词的筛选状态
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WXUpdateWordSelState(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取userid和wordlist
		System.out.println("调用了WordServlet 中的 WXUpdateWordSelState ");
		String userid = request.getParameter("userid");
		String wordListJsonstr = request.getParameter("wordListJsonstr");
		System.out.println("userid = "+ userid+" wordListJsonstr = "+wordListJsonstr);
		JSONArray wordListObj = JSONArray.fromObject(wordListJsonstr);
		List<JSONObject> list=new LinkedList<>();
		for(int i=0;i<wordListObj.size();i++) {
			System.out.println(wordListObj.get(i));
			JSONObject word=(JSONObject) wordListObj.get(i);
			list.add(word);
		}
		WordSelectedService ws=(WordSelectedService) BeanFactory.getBean("WordSelectedService");
		JSONObject jsonObj=new JSONObject();
		int ret=ws.updateWordSelState(userid,list);
		if(ret!=-1) {
			jsonObj.put("code", FEIPCodeEnum.OK.getCode());
			jsonObj.put("state", 1);
			jsonObj.put("data","{}" );
			String jsonstr2=JsonUtil.object2json(jsonObj);
			WriteBackUtil.WriteBackJsonStr(jsonstr2, response);
			return "success:获取课时下所有单词成功";
		}
		return null;
		
	}

}
