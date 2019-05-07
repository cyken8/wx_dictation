package indi.cyken.domain;

/**
 * 城市实体
 * @author Yong
 *
 */
public class City {
	private String cityid;
	private String cityname;
	private Province province;
	
	public City() {
		
	}
	
	
	public City(String cityid, String cityname, Province province) {
		super();
		this.cityid = cityid;
		this.cityname = cityname;
		this.province = province;
	}


	public City(String cityid) {
		super();
		this.cityid = cityid;
	}


	public String getCityid() {
		return cityid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	public Province getProvince() {
		return province;
	}
	public void setProvince(Province province) {
		this.province = province;
	}
	
	

}
