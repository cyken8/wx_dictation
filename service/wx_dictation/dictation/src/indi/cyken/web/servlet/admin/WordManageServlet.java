package indi.cyken.web.servlet.admin;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import indi.cyken.constant.Constant;
import indi.cyken.constant.DBTableField;
import indi.cyken.domain.Course;
import indi.cyken.domain.Word;
import indi.cyken.domain.WordComeType;
import indi.cyken.domain.WordLengType;
import indi.cyken.dto.Result;
import indi.cyken.service.WordService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.HTMLConvertTextUtil;
import indi.cyken.utils.OSResourcePathUtil;
import indi.cyken.utils.SpeechUtil;
import indi.cyken.utils.UUIDUtils;
import indi.cyken.utils.WriteBackUtil;
import indi.cyken.web.servlet.BaseServlet;

/**
 * Servlet implementation class WordManageServlet
 */

public class WordManageServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
 
	/**
	 * 获取某一课时下的所有标准单词
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebGetWordStandByCid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.节点编号
		String nodeId = request.getParameter("menuId");	
		//2.判断节点类型，此处无需判断也可完成业务逻辑，留待改善代码使用。

		//2.调用service 通过oid 返回值:order
		WordService ws=(WordService) BeanFactory.getBean("WordService");
		List<Word> wlist=ws.getWordsByCid(nodeId);
		Result result=new Result(0,"获取标准单词成功");
		if(wlist!=null) {
			result.setResult(wlist);
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("获取标准单词失败");
		}
		return null;
	}
	
	/**
	 * 管理员网页上添加标准单词
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebAddWordStandard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.节点编号
		String courseid = request.getParameter("courseid");	//课时编号
		String description=request.getParameter("description");
		
//		description = HTMLConvertTextUtil.convertOneText(description);
		List<String> list = Arrays.asList(description.split("\n"));

		List<Word> wlist=new LinkedList<>();
		for(int i=0;i<list.size();i++) {
			String wordtext=list.get(i);
			//2.获取录音文件保存路径
			String path=OSResourcePathUtil.getWordPath();
			String fileName=wordtext+".mp3";
			String fullPath=path+System.getProperty("file.separator")+fileName;

			//3.文字合成录音
			boolean ret = SpeechUtil.SpeechSynthesizer(wordtext, fullPath);
			System.out.println("文字合成录音返回的成功与否结果"+ret);
			if(!ret) {
				return null;
			}

			//组装Word
			String wordPathVirtual=OSResourcePathUtil.getWordPathVirtual();
			String voiceurl=wordPathVirtual+fileName;
			Word word=new Word(Constant.PREFIX_WORD+UUIDUtils.getCode(),
					wordtext,
					voiceurl,
					new Course(courseid),
					new WordLengType(DBTableField.WORD_LENGTYPE_LONGWORD),
					new WordComeType(DBTableField.WORD_COMETYPE_STANDARD));
			
			wlist.add(word);
			
		}
		
		WordService ws=(WordService) BeanFactory.getBean("WordService");
		int ret=ws.addWordStandard(wlist);
		Result result=new Result(0,"添加标准单词成功");
		if(ret==wlist.size()) {
			WriteBackUtil.WriteBackJsonStr(result, response);
		}
		else {
			result.setErrorNo(1);
			result.setErrorInfo("添加标准单词失败");
			WriteBackUtil.WriteBackJsonStr(result, response);

		}
		return null;
	}
	
	/**
	 * 管理员网页删除单词
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebDelWordStandard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.获取参数
		String wordid = request.getParameter("wordid");	//课时编号
		
		WordService ws=(WordService) BeanFactory.getBean("WordService");
		int ret=ws.delWordStandard(wordid);
		Result result=new Result(0,"删除标准单词成功");
		if(ret>0) {
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("删除标准单词失败");
			WriteBackUtil.WriteBackJsonStr(result, response);
		}

		return null;
	}
	
}
