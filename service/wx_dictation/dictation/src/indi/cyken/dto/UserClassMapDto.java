package indi.cyken.dto;

import java.util.List;

import indi.cyken.domain.SClass;

/**
 * 教师所属班级Dto
 * @author Administrator
 *
 */
public class UserClassMapDto {
	private String userid;
	private List<SClass> classList;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public List<SClass> getClassList() {
		return classList;
	}
	public void setClassList(List<SClass> classList) {
		this.classList = classList;
	}
	
	

}
