package indi.cyken.domain;

import java.sql.Timestamp;

/**
 * 登陆态实体
 * @author Yong
 *
 */
public class UserSession {
	private Integer sid;
	private String seesion_key;
	private String open_id;
	
	private Timestamp expire_date;
	private String client_key;
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public String getSeesion_key() {
		return seesion_key;
	}
	public void setSeesion_key(String seesion_key) {
		this.seesion_key = seesion_key;
	}
	public String getOpen_id() {
		return open_id;
	}
	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}
	public Timestamp getExpire_date() {
		return expire_date;
	}
	public void setExpire_date(Timestamp expire_date) {
		this.expire_date = expire_date;
	}
	public String getClient_key() {
		return client_key;
	}
	public void setClient_key(String client_key) {
		this.client_key = client_key;
	}

	

}
