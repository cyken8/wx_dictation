package indi.cyken.dto.Book;

/**
 * 网页端课本信息显示DTO
 */
public class BookInfoDto {

	private String bookid;
	private String bookname;
	private String bookversion;
	
	private String booklang;
	private String booktype;
	private String bookcategory;
	
	private String cover;
	private String status;
		
	
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
	public String getBookversion() {
		return bookversion;
	}
	public void setBookversion(String bookversion) {
		this.bookversion = bookversion;
	}
	public String getBooklang() {
		return booklang;
	}
	public void setBooklang(String booklang) {
		this.booklang = booklang;
	}
	public String getBooktype() {
		return booktype;
	}
	public void setBooktype(String booktype) {
		this.booktype = booktype;
	}
	public String getBookcategory() {
		return bookcategory;
	}
	public void setBookcategory(String bookcategory) {
		this.bookcategory = bookcategory;
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
	
	
}
