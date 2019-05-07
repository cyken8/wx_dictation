package indi.cyken.domain;

/**
 * 省份实体
 * @author Yong
 *
 */
public class Province {
	private String provinceid;
	private String provincename;
	
	public Province() {
		
	}
	
	
	public Province(String provinceid) {
		super();
		this.provinceid = provinceid;
	}


	public String getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
	public String getProvincename() {
		return provincename;
	}
	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}
	
	

}
