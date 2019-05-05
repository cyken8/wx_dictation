package indi.cyken.service.impl;

import java.util.List;

import indi.cyken.constant.Constant;
import indi.cyken.dao.CollectionDao;
import indi.cyken.dao.WordDao;
import indi.cyken.domain.CollectionItem;
import indi.cyken.domain.Word;
import indi.cyken.service.WordService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.DataSourceUtils;
import indi.cyken.utils.UUIDUtils;
import net.sf.json.JSONObject;

public class WordServiceImpl implements WordService {

	/**
	 * 根据用户id和课时id获取课时下所有单词
	 */
	@Override
	public List<Word> getWordsByUidCid(String userid, String courseid) throws Exception {
		WordDao wd=(WordDao) BeanFactory.getBean("WordDao");
		return wd.getWordsByUidCid(userid, courseid);

	}

	/**
	 * 添加用户自定义单词
	 */
	@Override
	public int add(String wordid, String weid, String wordtext, String voiceurl, String lengtypeid, String cometypeid,
			String courseid, String userid) throws Exception {
		try {
			//1.开启事务
			DataSourceUtils.startTransaction();
			
			WordDao wd=(WordDao) BeanFactory.getBean("WordDao");
			//2.添加单词项
			wd.addWord(wordid, wordtext, voiceurl,  lengtypeid, cometypeid,courseid);
			//3.添加单词
			int ret=wd.addWordExUserMap(weid,wordid,userid);
	
			//4.事务处理
			DataSourceUtils.commitAndClose();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
	}

	/**
	 * 根据用户id和课时id获取课时下所有的自定义单词
	 */
	@Override
	public List<Word> getWordEx(String userid, String courseid) throws Exception {
		WordDao wd=(WordDao) BeanFactory.getBean("WordDao");
		return wd.getWordEx(userid, courseid);
	}

	/**
	 * 
	 */
	@Override
	public int addWordEx(String userid, List<Word> wordList) throws Exception {
		try {
			//1.开启事务
			DataSourceUtils.startTransaction();
			
			WordDao wd=(WordDao) BeanFactory.getBean("WordDao");
			//2.添加单词项
			int ret=0;
			for(int i=0;i<wordList.size();i++) {
				Word word=wordList.get(i);
				wd.addWord(word.getWordid(), 
						word.getWordtext(), 
						word.getVoiceurl(),  
						word.getLengtype().getLengtypeid(), 
						word.getCometype().getCometypeid(),
						word.getCourse().getCourseid());
				//3.添加单词
				String weid=Constant.PREFIX_MAP_WORDEX_USER+UUIDUtils.getCode();
				wd.addWordExUserMap(weid,word.getWordid(),userid);
			}

			//4.事务处理
			DataSourceUtils.commitAndClose();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
	}

	/**
	 * 删除用户自定义单词
	 * wordIdList:[wordid,wordid,wordid]
	 */
	@Override
	public int delWordEx(List<String> wordIdList,String userid) throws Exception {
		try {
			//1.开启事务
			DataSourceUtils.startTransaction();
			
			WordDao wd=(WordDao) BeanFactory.getBean("WordDao");
			//2.添加单词项
			int  ret=0;
			for(int i=0;i<wordIdList.size();i++) {
				
				String weid=Constant.PREFIX_MAP_WORDEX_USER+UUIDUtils.getCode();
				wd.delWordEx(wordIdList.get(i),userid);
				ret++;
			}

			//4.事务处理
			DataSourceUtils.commitAndClose();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
	}

	/**
	 * 根据课时获取所有标准单词
	 */
	@Override
	public List<Word> getWordsByCid(String courseid) throws Exception {
		WordDao wd=(WordDao) BeanFactory.getBean("WordDao");
		return wd.getWordsByCid(courseid);
	}





}
