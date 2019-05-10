package indi.cyken.web.servlet;


import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import indi.cyken.constant.FEIPCodeEnum;
import indi.cyken.domain.Word;
import indi.cyken.service.WordService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.JsonUtil;
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
	 * 根据用户id和课时id获取课时下所有单词
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WXGetWordStandard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取userid和courseid

		String courseid = request.getParameter("courseid");
		System.out.println(" courseid = "+courseid);
		
		//2.调用service 通过oid 返回值:order
		WordService ws=(WordService) BeanFactory.getBean("WordService");
		List<Word> wlist=ws.getWordStandardByCid(courseid);
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
	 * 根据用户id和收藏的课本下的课时id获取课时下所有单词
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WXGetAllWordForCollection(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取userid和courseid
		String userid = request.getParameter("userid");
		String courseid = request.getParameter("courseid");
		System.out.println("userid = "+ userid+" courseid = "+courseid);
		
		//2.调用service 通过oid 返回值:order
		WordService ws=(WordService) BeanFactory.getBean("WordService");
		List<Word> wlist=ws.getWordsByUidCid(userid,courseid);
		JSONObject jsonObj=new JSONObject();
		if(wlist!=null&&wlist.size()>0) {
			String jsonstr = JsonUtil.list2json(wlist);
			jsonObj.put("code", FEIPCodeEnum.OK.getCode());
			jsonObj.put("state", 1);
			jsonObj.put("data",jsonstr );
			WriteBackUtil.WriteBackJsonStr(jsonObj, response);
			
		}else {
			jsonObj.put("state", 0);
			jsonObj.put("code", FEIPCodeEnum.VALUE_NULL.getCode());
			WriteBackUtil.WriteBackJsonStr(jsonObj, response);
		}
		return null;

		
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
	
	/**
	 * 根据和课时id和用户ID获取课时下所有标准单词和自定义单词(微信端教师筛选单词布置作业时),补丁
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WXGetAllWordByCid2(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String courseid = request.getParameter("courseid");
		String userid = request.getParameter("userid");
		
		//2.调用service 通过oid 返回值:order
		WordService ws=(WordService) BeanFactory.getBean("WordService");
		//3、获取标准单词
		List<Word> wlist=ws.getWordsByCid(courseid);
		//4、获取自定义单词
		List<Word> exlist=ws.getWordEx(userid, courseid);
		//5.拼接标准单词和自定义单词，两者的数据结构要一致

		JSONObject data=new JSONObject();

		if(wlist!=null) {
			if(wlist!=null) {
				wlist.addAll(exlist);
			}
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
