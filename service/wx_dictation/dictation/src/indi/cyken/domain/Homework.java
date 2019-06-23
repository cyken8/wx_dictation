package indi.cyken.domain;

import java.util.LinkedList;
import java.util.List;

/**
 * 作业实体
 * @author Yong
 *
 */
public class Homework {
	
	private String hwid;
	private String hwname;
	private User user;
	
	private List<HomeworkItem> items=new LinkedList<>();
	
	public Homework() {
		
	}
	
	

	public Homework(String hwid) {
		super();
		this.hwid = hwid;
	}



	public String getHwid() {
		return hwid;
	}

	public void setHwid(String hwid) {
		this.hwid = hwid;
	}



	public List<HomeworkItem> getItems() {
		return items;
	}

	public void setItems(List<HomeworkItem> items) {
		this.items = items;
	}

	public String getHwname() {
		return hwname;
	}

	public void setHwname(String hwname) {
		this.hwname = hwname;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
