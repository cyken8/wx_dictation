package indi.cyken.domain;

/**
 * 用户角色实体
 * @author Yong
 *
 */
public class Role {
	private String roleid;
	private String rolename;
	
    //保留此默认的构造方法
     public Role(){
         
     }
     public Role(String roleid,String rolename) {
    	 this.roleid=roleid;
    	 this.rolename=rolename;
     }
     public Role(String roleid) {
    	 this.roleid=roleid;
     }
	
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	
	
	
	
	
	

}
