package indi.cyken.domain;

/**
 * 用户单词筛选实体
 * @author Yong
 *
 */
public class WordSelected {
	
	private String wusid;
	private User   user;
	private Word	word;
	public String getWusid() {
		return wusid;
	}
	public void setWusid(String wusid) {
		this.wusid = wusid;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Word getWord() {
		return word;
	}
	public void setWord(Word word) {
		this.word = word;
	}
	
	

}
