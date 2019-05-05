package indi.cyken.web.servlet;


import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import indi.cyken.constant.Constant;
import indi.cyken.constant.DBTableField;
import indi.cyken.constant.FEIPCodeEnum;
import indi.cyken.domain.BookLanguage;
import indi.cyken.domain.Word;
import indi.cyken.service.CourseService;
import indi.cyken.service.WordSelectedService;
import indi.cyken.service.WordService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.FileUtil;
import indi.cyken.utils.JsonUtil;
import indi.cyken.utils.SpeechUtil;
import indi.cyken.utils.UUIDUtils;
import indi.cyken.utils.WordUtil;
import indi.cyken.utils.WriteBackUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 单词Servlet
 */
public class WordServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
   

	/**
	 * 根据用户id和课时id获取课时下所有单词
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WXGetWordsByUidCid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取userid和courseid
		System.out.println("调用了WordServlet 中的 WXGetWordsByUidCid ");
		String userid = request.getParameter("userid");
		String courseid = request.getParameter("courseid");
		System.out.println("userid = "+ userid+" courseid = "+courseid);
		
		//2.调用service 通过oid 返回值:order
		WordService ws=(WordService) BeanFactory.getBean("WordService");
		List<Word> wlist=ws.getWordsByUidCid(userid,courseid);
		if(wlist!=null&&wlist.size()>0) {
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
			jsonObj.put("code", FEIPCodeEnum.VALUE_NULL.getCode());
			jsonObj.put("data","{}" );
			String jsonstr2=JsonUtil.object2json(jsonObj);
			WriteBackUtil.WriteBackJsonStr(jsonstr2, response);
			return "fail:获取课时下所有单词失败";
		}

		
	}
	
	
	
	/**
	 * 根据用户id和课时id获取课时下所有的自定义单词
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WXGetWordEx(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取userid和courseid
		System.out.println("调用了WordServlet 中的 WXGetWordEx ");
		String userid = request.getParameter("userid");
		String courseid = request.getParameter("courseid");
		System.out.println("userid = "+ userid+" courseid = "+courseid);
		
		//2.调用service 通过oid 返回值:order
		WordService ws=(WordService) BeanFactory.getBean("WordService");
		List<Word> wlist=ws.getWordEx(userid,courseid);
		JSONObject jsonObj=new JSONObject();

		if(wlist!=null) {
			String jsonstr = JsonUtil.list2json(wlist);
			jsonObj.put("code", FEIPCodeEnum.OK.getCode());
			jsonObj.put("state", 1);
			jsonObj.put("data",jsonstr );
			WriteBackUtil.WriteBackJsonStr(jsonObj, response);
			return "success:获取课时下所有自定义单词成功";
		}else {
			jsonObj.put("state", 0);
			jsonObj.put("code", FEIPCodeEnum.VALUE_NULL.getCode());
			jsonObj.put("data","{}" );
			WriteBackUtil.WriteBackJsonStr(jsonObj, response);
			return "fail:获取课时下所有自定义单词失败";
		}

		
	}
	
	/**
	 * 根据用户id和传递过来的单词列表json字符串更新用户对单词的筛选状态
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WXAddWordEx(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取userid和wordlist
		String userid = request.getParameter("userid");
		String courseid=request.getParameter("courseid"); 
		String wordListJsonstr = request.getParameter("wordListJsonstr");//[{wordtext:xxx},{wordtext:xxx}]
		System.out.println("userid = "+ userid+" wordListJsonstr = "+wordListJsonstr);
		JSONArray wordListObj = JSONArray.fromObject(wordListJsonstr);
		List<String> list=new LinkedList<>();
		for(int i=0;i<wordListObj.size();i++) {
			System.out.println(wordListObj.get(i));
			JSONObject word=(JSONObject) wordListObj.get(i);
			list.add(word.getString("wordtext"));
		}
		WordService ws=(WordService) BeanFactory.getBean("WordService");
		List<Word> wordList=WordUtil.getWordByText(userid,courseid,list);
		if(wordList==null) {
			System.out.println("根据文字生成单词实体失败");
			return "fail";
		}
		
		int ret=ws.addWordEx(userid,wordList);

		JSONObject jsonObj=new JSONObject();
		if(ret!=-1) {
			jsonObj.put("code", FEIPCodeEnum.OK.getCode());
			jsonObj.put("state",1 );
			jsonObj.put("data","{}" );
			WriteBackUtil.WriteBackJsonStr(jsonObj, response);
			return "success:添加用户自定义单词成功";
		}else {
			jsonObj.put("code", FEIPCodeEnum.FAIL.getCode());
			jsonObj.put("state",0 );
			jsonObj.put("data","{}" );
			WriteBackUtil.WriteBackJsonStr(jsonObj, response);
			return "fail:添加用户自定义单词失败";
		}

		
	}


	public String WXDeleteWordEx(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取userid和wordlist
		System.out.println("调用了WordServlet 中的 WXDeleteWordEx ");
		String userid = request.getParameter("userid");
		String wordIdListJsonstr = request.getParameter("wordIdListDel");//[{wordid:xxx},{wordid:xxx}]
		System.out.println(" wordIdListJsonstr = "+wordIdListJsonstr);
		JSONArray wordListObj = JSONArray.fromObject(wordIdListJsonstr);
		List<String> wordIdList=new LinkedList<>();
		for(int i=0;i<wordListObj.size();i++) {
			System.out.println(wordListObj.get(i));
			JSONObject word=(JSONObject) wordListObj.get(i);
			wordIdList.add(word.getString("wordid"));
		}
		WordService ws=(WordService) BeanFactory.getBean("WordService");
		int ret=ws.delWordEx(wordIdList,userid);

		JSONObject jsonObj=new JSONObject();
		if(ret==wordIdList.size()) {
			jsonObj.put("code", FEIPCodeEnum.OK.getCode());
			jsonObj.put("state",0 );
			jsonObj.put("data","{}" );
			WriteBackUtil.WriteBackJsonStr(jsonObj, response);
			return "success:添加用户自定义单词成功";
		}else {
			jsonObj.put("code", FEIPCodeEnum.FAIL.getCode());
			jsonObj.put("state",0 );
			jsonObj.put("data","{}" );
			WriteBackUtil.WriteBackJsonStr(jsonObj, response);
			return "fail:添加用户自定义单词失败";
		}
		
	}
	
	
	/**
	 * 根据和课时id获取课时下所有标准单词
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WXGetAllWordByCid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取userid和courseid
		System.out.println("调用了WordServlet 中的 WXGetAllWordByCid ");
		String courseid = request.getParameter("courseid");
		System.out.println(" courseid = "+courseid);
		
		//2.调用service 通过oid 返回值:order
		WordService ws=(WordService) BeanFactory.getBean("WordService");
		List<Word> wlist=ws.getWordsByCid(courseid);
		JSONObject data=new JSONObject();

		if(wlist!=null) {
			String jsonstr = JsonUtil.list2json(wlist);
			data.put("wordList",jsonstr );
			WriteBackUtil.WriteBackJsonStr(1,FEIPCodeEnum.OK.getCode(),FEIPCodeEnum.OK.getMsg(),data, response);
			return "success:获取课时下所有单词成功";
		}else {
			WriteBackUtil.WriteBackJsonStr(0,FEIPCodeEnum.FAIL.getCode(),FEIPCodeEnum.FAIL.getMsg(),data, response);
			return "fail:获取课时下所有单词失败";
		}

		
	}
	


	

}
