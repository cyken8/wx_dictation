package indi.cyken.domain;

/**
 * 课本版本实体
 * @author Yong
 *
 */
public class BookVersion {
	private String versionid;
	private String versionname;
	
	public BookVersion() {
		
	}
	
	
	public BookVersion(String versionid) {
		super();
		this.versionid = versionid;
	}


	public String getVersionid() {
		return versionid;
	}
	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}
	public String getVersionname() {
		return versionname;
	}
	public void setVersionname(String versionname) {
		this.versionname = versionname;
	}
	

}
