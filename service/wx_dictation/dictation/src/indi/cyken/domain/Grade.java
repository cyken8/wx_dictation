package indi.cyken.domain;

/**
 * 年级实体
 * @author Yong
 *
 */
public class Grade {
	private String gradeid;
	private String gradename;
	
	public Grade() {
		
	}
	
	
	public Grade(String gradeid) {
		super();
		this.gradeid = gradeid;
	}


	public String getGradeid() {
		return gradeid;
	}
	public void setGradeid(String gradeid) {
		this.gradeid = gradeid;
	}
	public String getGradename() {
		return gradename;
	}
	public void setGradename(String gradename) {
		this.gradename = gradename;
	}
	
	

}
