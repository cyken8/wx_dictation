package indi.cyken.service.impl;

import java.util.List;

import indi.cyken.dao.ScoreDao;
import indi.cyken.domain.Score;
import indi.cyken.service.ScoreService;
import indi.cyken.utils.BeanFactory;

public class ScoreServiceImpl implements ScoreService{

	/**
	 * 添加成绩
	 */
	@Override
	public int add(Score score) throws Exception {
		ScoreDao sd=(ScoreDao) BeanFactory.getBean("ScoreDao");
		return sd.add(score);
		
	}

	/**
	 * 获取用户所有听写过的课时的最新成绩
	 */
	@Override
	public List<Score> getScoreAllCourse(String userid) throws Exception {
		ScoreDao sd=(ScoreDao) BeanFactory.getBean("ScoreDao");
		return sd.getScoreAllCourse(userid);
	}

	/**
	 * 获取某课时的所有历史成绩
	 */
	@Override
	public List<Score> getScoreOneCourse(String userid, String courseid) throws Exception {
		ScoreDao sd=(ScoreDao) BeanFactory.getBean("ScoreDao");
		return sd.getScoreOneCourse(userid,courseid);

	}

}
