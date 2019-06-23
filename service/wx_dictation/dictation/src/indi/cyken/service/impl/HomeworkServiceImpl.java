package indi.cyken.service.impl;

import java.util.List;

import indi.cyken.dao.HomeworkDao;
import indi.cyken.domain.Homework;
import indi.cyken.domain.Word;
import indi.cyken.service.HomeworkService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.DataSourceUtils;

public class HomeworkServiceImpl implements HomeworkService {

	/**
	 * 添加作业项
	 */
	@Override
	public int addHomework(String hwid,String hwname,String classid, List<String> list) throws Exception {
		try {
			//1.开启事务
			DataSourceUtils.startTransaction();
			//2.添加作业本
			HomeworkDao hd=(HomeworkDao) BeanFactory.getBean("HomeworkDao");
			Boolean hwstate=true;
			int ret=hd.addHomework(hwid,hwname,classid,hwstate);
			//2.添加作业项
			for(int i=0;i<list.size();i++) {
				String wordid=list.get(i);
				ret=hd.addHomeworkItem(hwid,wordid);
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
	 * 获取用户所有作业
	 */
	@Override
	public List<Homework> getAllHomeworkByUid(String classid) throws Exception {
		HomeworkDao cd=(HomeworkDao) BeanFactory.getBean("HomeworkDao");
		return cd.getAllHomeworkByUid(classid);
	}

	/**
	 * 获取作业下所有单词
	 */
	@Override
	public List<Word> getAllWordByHWid(String hwid) throws Exception {
		HomeworkDao cd=(HomeworkDao) BeanFactory.getBean("HomeworkDao");
		return cd.getAllWordByHWid(hwid);
	}

	/**
	 * 删除一项作业
	 */
	@Override
	public int delOneHomeworkByHwid(String hwid) throws Exception {
		HomeworkDao cd=(HomeworkDao) BeanFactory.getBean("HomeworkDao");
		return cd.delOneHomeworkByHwid(hwid);
	}

}
