package indi.cyken.domain;

import java.util.LinkedList;
import java.util.List;

/**
 * 单元实体 
 * @author Yong
 *
 */
public class Unit {
	
	private String unitid;
	private String unitname;
	private Book   book;
	
	//包含哪些课时
	private List<Course> courselist=new LinkedList<>();
	
	public String getUnitid() {
		return unitid;
	}
	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}
	public String getUnitname() {
		return unitname;
	}
	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public List<Course> getCourselist() {
		return courselist;
	}
	public void setCourselist(List<Course> courselist) {
		this.courselist = courselist;
	}

	
	

}
