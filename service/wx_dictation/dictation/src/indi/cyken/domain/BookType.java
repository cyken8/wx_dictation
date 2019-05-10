package indi.cyken.domain;

/**
 * 课本类别实体
 * @author Yong
 *
 */
public class BookType {

	private String typeid;
	private String typename;
	
	public BookType() {
		
	}
	
	
	public BookType(String typeid) {
		super();
		this.typeid = typeid;
	}


	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}	
	
}
