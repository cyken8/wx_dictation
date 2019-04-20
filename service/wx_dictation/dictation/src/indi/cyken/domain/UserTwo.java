package indi.cyken.domain;

import java.util.Date;

/**
 * 第二版本用户实体
 * @author Yong
 *
 */
public class UserTwo {
	
	private String userid;
	private String username;
	private String password;
	
	private String avatar;	
	private String sex;
	private Date  birthday;	
	
	private String email;	
	private UserType usertype;
	private Organization org;
	
	private Grade grade;
	private SClass sclass;
	private Province province;
	
	private City city;
	private String open_id;
	private String state;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserType getUsertype() {
		return usertype;
	}
	public void setUsertype(UserType usertype) {
		this.usertype = usertype;
	}
	public Organization getOrg() {
		return org;
	}
	public void setOrg(Organization org) {
		this.org = org;
	}
	public Grade getGrade() {
		return grade;
	}
	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	public SClass getSclass() {
		return sclass;
	}
	public void setSclass(SClass sclass) {
		this.sclass = sclass;
	}
	public Province getProvince() {
		return province;
	}
	public void setProvince(Province province) {
		this.province = province;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public String getOpen_id() {
		return open_id;
	}
	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	

	
}
