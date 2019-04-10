package indi.cyken.domain;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 收藏记录实体
 * @author Yong
 *
 */
public class Collection {


	private User user;
	//包含那些收藏项
	private List<CollectionItem> items=new LinkedList<>();
	
	private String item1;		//保留项1
	private String item2;		//保留项2
	private String item3;		//保留项3
	private String item4;		//保留项4
	
	public String getItem1() {
		return item1;
	}
	public void setItem1(String item1) {
		this.item1 = item1;
	}
	public String getItem2() {
		return item2;
	}
	public void setItem2(String item2) {
		this.item2 = item2;
	}
	public String getItem3() {
		return item3;
	}
	public void setItem3(String item3) {
		this.item3 = item3;
	}
	public String getItem4() {
		return item4;
	}
	public void setItem4(String item4) {
		this.item4 = item4;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<CollectionItem> getItems() {
		return items;
	}
	public void setItems(List<CollectionItem> items) {
		this.items = items;
	}

	
}
