package indi.cyken.dao;

import java.util.List;

import indi.cyken.domain.Score;

public interface ScoreDao {

	int add(Score score) throws Exception;

	List<Score> getScoreAllCourse(String userid) throws Exception;

	List<Score> getScoreOneCourse(String userid, String courseid) throws Exception;

}
