package indi.cyken.service;

import java.util.List;

import indi.cyken.domain.HomeworkScore;
import indi.cyken.domain.Score;

public interface ScoreService {


	int add(Score score) throws Exception;

	List<Score> getScoreAllCourse(String userid) throws Exception;

	List<Score> getScoreOneCourse(String userid, String courseid) throws Exception;

	int addHomeworkScore(HomeworkScore hwScore) throws Exception;


}
