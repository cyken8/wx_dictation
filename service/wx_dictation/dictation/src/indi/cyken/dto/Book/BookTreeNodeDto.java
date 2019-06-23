package indi.cyken.dto.Book;

/**
 * 网页端课本树节点DTO
 * @author Administrator
 *
 */
public class BookTreeNodeDto {

	private String id;
	private Boolean isParent;
	private String name;
	private Boolean open;
	private String	pId;
	
	public BookTreeNodeDto() {
		
	}
	
	
	
	public BookTreeNodeDto(String id, Boolean isParent, String name, Boolean open, String pId) {
		super();
		this.id = id;
		this.setIsParent(isParent);
		this.name = name;
		this.open = open;
		this.pId = pId;
	}



	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getOpen() {
		return open;
	}
	public void setOpen(Boolean open) {
		this.open = open;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}



	public Boolean getIsParent() {
		return isParent;
	}



	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	
	
}
