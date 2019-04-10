package indi.cyken.domain;

import java.io.Serializable;

/**
 * 用户收藏记录项
 * @author Yong
 *
 */
public class CollectionItem implements Serializable{
	
	private String itemid;
	
	//包含哪本课本
	private Book Book;
	
	//属于那个收藏
	private Collection collection;
	
	private String state;			//

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public Book getBook() {
		return Book;
	}

	public void setBook(Book book) {
		Book = book;
	}

	public Collection getCollection() {
		return collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
	
	
	
}
