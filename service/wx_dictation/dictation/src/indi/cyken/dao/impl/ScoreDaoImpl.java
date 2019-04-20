package indi.cyken.dao.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import indi.cyken.constant.DBTableField;
import indi.cyken.dao.ScoreDao;
import indi.cyken.domain.Course;
import indi.cyken.domain.Score;
import indi.cyken.domain.Unit;
import indi.cyken.domain.User;
import indi.cyken.domain.Word;
import indi.cyken.domain.WordComeType;
import indi.cyken.domain.WordLengType;
import indi.cyken.utils.DataSourceUtils;

public class ScoreDaoImpl implements ScoreDao {

	@Override
	public int add(Score score) throws Exception {
		QueryRunner qr = new QueryRunner();
		String sql="insert into t_score values(?,?,?,?,?,?) ";
		int ret=qr.update(DataSourceUtils.getConnection(),sql,score.getScid(),
				score.getUser().getUserid(),
				score.getCourse().getCourseid(),
				score.getScore(),
				score.getCreatetime(),
				score.getScstate());
		return ret;
	}

	/**
	 * 获取用户所有听写过的课时的最新成绩
	 */
	@Override
	public List<Score> getScoreAllCourse(String userid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		//String sql="select * from t_score s,t_user u,t_course c ,t_unit un where s.userid=? and s.scstate=1 and s.userid=u.userid and s.courseid=c.courseid and c.unitid=un.unitid ORDER BY s.score DESC";
		String sql="SELECT *  FROM  (SELECT * FROM t_score  ORDER BY createtime DESC) s,t_user u,t_course c ,t_unit un  \r\n" + 
				"WHERE s.userid=? AND s.userid=u.userid AND s.courseid=c.courseid AND c.unitid=un.unitid\r\n" + 
				"GROUP BY s.courseid ";
		List<Map<String, Object>> query = qr.query(sql, new MapListHandler(), userid);
		List<Score> list=new LinkedList<>();
		for (Map<String, Object> map : query) {
			
			Unit unit=new Unit();
			BeanUtils.populate(unit, map);

			
			Course course=new Course();
			BeanUtils.populate(course, map);
			course.setUnit(unit);
			User user=new User();
			BeanUtils.populate(user, map);
			
			Score score =new Score();
			BeanUtils.populate(score, map);
			score.setUser(user);
			score.setCourse(course);
			
			list.add(score);	
		}
		return list;
	}

	/**
	 * 获取某课时的所有历史成绩
	 */
	@Override
	public List<Score> getScoreOneCourse(String userid, String courseid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		//String sql="select * from t_score s,t_user u,t_course c ,t_unit un where s.userid=? and s.scstate=1 and s.userid=u.userid and s.courseid=c.courseid and c.unitid=un.unitid ORDER BY s.score DESC";
		String sql="SELECT *  FROM  t_score WHERE userid=? AND courseid=? ORDER BY createtime ASC";
		List<Map<String, Object>> query = qr.query(sql, new MapListHandler(), userid,courseid);
		List<Score> list=new LinkedList<>();
		for (Map<String, Object> map : query) {
			
			Unit unit=new Unit();
			BeanUtils.populate(unit, map);

			
			Course course=new Course();
			BeanUtils.populate(course, map);
			course.setUnit(unit);
			User user=new User();
			BeanUtils.populate(user, map);
			
			Score score =new Score();
			BeanUtils.populate(score, map);
			score.setUser(user);
			score.setCourse(course);
			
			list.add(score);	
		}
		return list;
	}

	

}
