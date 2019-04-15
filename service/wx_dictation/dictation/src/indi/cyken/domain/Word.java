package indi.cyken.domain;

public class Word {
	private String wordid;
	private String wordtext;
	private String voiceurl;
	
	private Course course;	//所属课时
	private WordLengType lengtype;		//长度类别
	private WordComeType cometype;		//来源类别
	public String getWordid() {
		return wordid;
	}
	public void setWordid(String wordid) {
		this.wordid = wordid;
	}
	public String getWordtext() {
		return wordtext;
	}
	public void setWordtext(String wordtext) {
		this.wordtext = wordtext;
	}
	public String getVoiceurl() {
		return voiceurl;
	}
	public void setVoiceurl(String voiceurl) {
		this.voiceurl = voiceurl;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public WordLengType getLengtype() {
		return lengtype;
	}
	public void setLengtype(WordLengType lengtype) {
		this.lengtype = lengtype;
	}
	public WordComeType getCometype() {
		return cometype;
	}
	public void setCometype(WordComeType cometype) {
		this.cometype = cometype;
	}
	
	

}
