package indi.cyken.domain;

/**
 * 课时实体
 * @author Yong
 *
 */
public class Course {
	
	private String courseid;
	private String coursename;
	private Unit unit;
	
	public Course() {
		
	}
	
	public Course(String courseid, String coursename, Unit unit) {
		super();
		this.courseid = courseid;
		this.coursename = coursename;
		this.unit = unit;
	}
	


	public Course(String courseid) {
		super();
		this.courseid = courseid;
	}

	public String getCourseid() {
		return courseid;
	}
	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}
	public String getCoursename() {
		return coursename;
	}
	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	

}
