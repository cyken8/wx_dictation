package indi.cyken.domain;

/**
 * 课本语言实体
 * @author Yong
 *
 */
public class BookLanguage {

	private String langid;
	private String langname;
	
	public BookLanguage() {
		
	}
	
	
	public BookLanguage(String langid) {
		super();
		this.langid = langid;
	}


	public String getLangid() {
		return langid;
	}
	public void setLangid(String langid) {
		this.langid = langid;
	}
	public String getLangname() {
		return langname;
	}
	public void setLangname(String langname) {
		this.langname = langname;
	}
	
}
