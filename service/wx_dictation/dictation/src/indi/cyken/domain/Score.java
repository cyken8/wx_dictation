package indi.cyken.domain;

import java.sql.Timestamp;

/**
 * 成绩实体
 * @author Yong
 *
 */
public class Score {

	private String scid;
	private User user;
	private Course course;
	
	private Double score;
	private Timestamp createtime;
	private Boolean scstate;
	public String getScid() {
		return scid;
	}
	public void setScid(String scid) {
		this.scid = scid;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public Boolean getScstate() {
		return scstate;
	}
	public void setScstate(Boolean scstate) {
		this.scstate = scstate;
	}
	
	
}
