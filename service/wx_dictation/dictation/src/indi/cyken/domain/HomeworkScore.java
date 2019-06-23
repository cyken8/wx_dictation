package indi.cyken.domain;

/**
 * 作业成绩
 * @author Yong
 *
 */
public class HomeworkScore {

	private Integer hpid;
	private User user;
	private Homework homework;
	
	private Double score;
	private Boolean state;
	
	public HomeworkScore() {
		
	}
	
	
	
	public HomeworkScore(User userid, Homework homework, Double score, Boolean state) {
		super();
		this.setUser(userid);
		this.setHomework(homework);
		this.score = score;
		this.state = state;
	}



	public Integer getHpid() {
		return hpid;
	}
	public void setHpid(Integer hpid) {
		this.hpid = hpid;
	}
	
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public Boolean getState() {
		return state;
	}
	public void setState(Boolean state) {
		this.state = state;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public Homework getHomework() {
		return homework;
	}



	public void setHomework(Homework homework) {
		this.homework = homework;
	}

	
	
}
