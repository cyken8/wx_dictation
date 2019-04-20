package indi.cyken.domain;

/**
 * 用户错字实体
 * @author Yong
 *
 */
public class WordWrong {
	
	private String wwid;
	private String userid;
	private String bookid;
	
	private String wordid;

	public String getWwid() {
		return wwid;
	}

	public void setWwid(String wwid) {
		this.wwid = wwid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getBookid() {
		return bookid;
	}

	public void setBookid(String bookid) {
		this.bookid = bookid;
	}

	public String getWordid() {
		return wordid;
	}

	public void setWordid(String wordid) {
		this.wordid = wordid;
	}
	
	

}
