package indi.cyken.service.impl;


import java.util.List;

import indi.cyken.dao.WordSelectedDao;
import indi.cyken.dao.WordWrongDao;
import indi.cyken.domain.Word;
import indi.cyken.service.WordWrongService;
import indi.cyken.utils.BeanFactory;


public class WordWrongServiceImpl implements WordWrongService {

	/**
	 * 添加错字集项
	 */
	@Override
	public int addWordWrong(String wwid,String wordid, String bookid, String userid) throws Exception {
		
		WordWrongDao wd=(WordWrongDao) BeanFactory.getBean("WordWrongDao");
		return wd.addWordWrong(wwid,wordid,bookid,userid);
		
	}

	/**
	 * 获取错字集
	 */
	@Override
	public List<Word> getWrongWordByUidBid(String userid, String bookid) throws Exception {
		WordWrongDao wd=(WordWrongDao) BeanFactory.getBean("WordWrongDao");
		return wd.getWrongWordByUidBid(userid, bookid);
	}

}
