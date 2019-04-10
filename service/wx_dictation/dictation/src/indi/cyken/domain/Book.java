package indi.cyken.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 课本实体
 * @author Yong
 *
 */
public class Book implements Serializable{
	
	private String bookid;
	private String bookname;
	private BookVersion version;
	
	private BookLanguage language;
	private BookType booktype;
	private String cover;	
	
	private String status;//状态  1:启用    0:删除
	private Category bookCategory;
	public String getBookid() {
		return bookid;
	}
	public void setBookid(String bookid) {
		this.bookid = bookid;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public BookVersion getVersion() {
		return version;
	}
	public void setVersion(BookVersion version) {
		this.version = version;
	}
	public BookLanguage getLanguage() {
		return language;
	}
	public void setLanguage(BookLanguage language) {
		this.language = language;
	}
	public BookType getBooktype() {
		return booktype;
	}
	public void setBooktype(BookType booktype) {
		this.booktype = booktype;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Category getBookCategory() {
		return bookCategory;
	}
	public void setBookCategory(Category bookCategory) {
		this.bookCategory = bookCategory;
	}

	
	
	
}
