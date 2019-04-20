package indi.cyken.domain;

/**
 * 组织机构实体
 * @author Yong
 *
 */
public class Organization {
	
	private String orgid;
	private String orgname;
	private City 	city;
	
	
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	
	

}
