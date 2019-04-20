package indi.cyken.domain;

/**
 * 班级实体
 * @author Yong
 *
 */
public class SClass {
	
	private String classid;
	private String classname;
	private Grade grade;
	public String getClassid() {
		return classid;
	}
	public void setClassid(String classid) {
		this.classid = classid;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public Grade getGrade() {
		return grade;
	}
	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	
	

}
