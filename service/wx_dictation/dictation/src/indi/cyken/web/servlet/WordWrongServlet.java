package indi.cyken.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import indi.cyken.constant.Constant;
import indi.cyken.constant.FEIPCodeEnum;
import indi.cyken.domain.Word;
import indi.cyken.service.WordSelectedService;
import indi.cyken.service.WordWrongService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.JsonUtil;
import indi.cyken.utils.UUIDUtils;
import indi.cyken.utils.WriteBackUtil;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class WordWrongServlet
 */

public class WordWrongServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * 添加错字集项目
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WXAddWrongWord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取userid和wordlist
		System.out.println("调用了WordWrongServlet 中的 WXAddWrongWord ");
		String userid = request.getParameter("userid");
		String bookid=request.getParameter("bookid"); 
		String wordid=request.getParameter("wordid"); 
		
		System.out.println("userid = "+ userid+" wordid = "+wordid+"bookid="+bookid);

		WordWrongService ws=(WordWrongService) BeanFactory.getBean("WordWrongService");
	
		String wwid=Constant.PREFIX_MAP_WORDWRONG_USER+UUIDUtils.getCode();
		int ret=ws.addWordWrong(wwid,wordid,bookid,userid);

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
	
	/**
	 * 根据用户id和课时id获取课时下被筛选过的单词
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WXGetWrongWord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取userid和courseid
		System.out.println("调用了WordWrongServlet 中的 WXGetWrongWord ");
		String userid = request.getParameter("userid");
		String bookid = request.getParameter("bookid");
		System.out.println("userid = "+ userid+" bookid = "+bookid);
		
		//2.调用service 通过oid 返回值:order
		WordWrongService ww=(WordWrongService) BeanFactory.getBean("WordWrongService");
		List<Word> wlist=ww.getWrongWordByUidBid(userid,bookid);
		JSONObject jsonObj=new JSONObject();

		if(wlist!=null) {
			String jsonstr = JsonUtil.list2json(wlist);
			jsonObj.put("state", 1);
			jsonObj.put("code", FEIPCodeEnum.OK.getCode());
			jsonObj.put("data",jsonstr );
			WriteBackUtil.WriteBackJsonStr(jsonObj, response);
			return "success:获取该课本下所有错误单词成功";
		}else {
			jsonObj.put("state", 0);
			jsonObj.put("code", FEIPCodeEnum.VALUE_NULL.getCode());
			jsonObj.put("data","{}" );
			WriteBackUtil.WriteBackJsonStr(jsonObj, response);
			return "fail:获取该课本下所有错误单词失败";
		}

		
	}


}
