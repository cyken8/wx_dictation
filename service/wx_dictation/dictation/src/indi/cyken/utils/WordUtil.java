package indi.cyken.utils;

import java.util.LinkedList;
import java.util.List;

import indi.cyken.constant.Constant;
import indi.cyken.constant.DBTableField;
import indi.cyken.domain.BookLanguage;
import indi.cyken.domain.Course;
import indi.cyken.domain.Word;
import indi.cyken.domain.WordComeType;
import indi.cyken.domain.WordLengType;
import indi.cyken.service.CourseService;

/**
 * 生成单词对象的工具类（只适合本工程使用）
 * @author Yong
 *
 */
public class WordUtil {
	
	
	public static List<Word> getWordByText(String userid,String courseid, List<String> wordTextList) throws Exception {
		
		List<Word> list=new LinkedList<>();
		for(int i=0;i<wordTextList.size();i++) {
			String wordtext=wordTextList.get(i);
			System.out.println("userId:"+userid+" courseId:"+courseid+" wordText:"+wordtext);
			//1.获取单词语言类型
			CourseService cs=(CourseService) BeanFactory.getBean("CourseService");
			BookLanguage bl=cs.getBookLangByCourseid(courseid);
			if(bl==null) {
				return null;
			}
			//2.获取录音文件保存路径
			//String path=ResourceBundle.getBundle("resourcepath").getString("WordPath");
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
			Course course=new Course();
			course.setCourseid(courseid);
			WordLengType lengtype=new WordLengType();
			String lengtypeid=DBTableField.WORD_LENGTYPE_LONGWORD;		//这里只设置单词，不设置单字，改功能抛弃
			lengtype.setLengtypeid(lengtypeid);
			WordComeType cometype=new WordComeType();
			String cometypeid=DBTableField.WORD_COMETYPE_EX;
			cometype.setCometypeid(cometypeid);
			String wordid=Constant.PREFIX_WORD+UUIDUtils.getCode();
			String wordPathVirtual=OSResourcePathUtil.getWordPathVirtual();
			String voiceurl=wordPathVirtual+fileName;
			String weid=Constant.PREFIX_MAP_WORDEX_USER+UUIDUtils.getCode();
			
			Word word=new Word();
			word.setWordid(wordid);
			word.setWordtext(wordtext);
			word.setVoiceurl(voiceurl);
			word.setLengtype(lengtype);
			word.setCometype(cometype);
			word.setCourse(course);
			
			list.add(word);
			
		}
		return list;

	}

}
