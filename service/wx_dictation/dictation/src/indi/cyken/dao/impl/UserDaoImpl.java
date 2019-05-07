package indi.cyken.dao.impl;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import indi.cyken.dao.UserDao;

import indi.cyken.domain.City;
import indi.cyken.domain.Grade;
import indi.cyken.domain.Organization;
import indi.cyken.domain.Province;
import indi.cyken.domain.SClass;
import indi.cyken.domain.User;
import indi.cyken.domain.UserTwo;
import indi.cyken.domain.Role;
import indi.cyken.utils.DataSourceUtils;

public class UserDaoImpl implements UserDao{

	

	/**
	 * 教师网页登录
	 * 
	 */
	@Override
	public User getByUsernameAndPwd(String username, String password)  throws Exception{
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from t_user where username = ? and password = ? limit 1";
		return qr.query(sql, new BeanHandler<>(User.class), username,password);
	}

	
	/**
	 * 通过用户id查询用户信息
	 */
	@Override
	public User getByUid(String userid) throws Exception {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from t_user where userid = ? limit 1";
		return qr.query(sql, new BeanHandler<>(User.class), userid);
	}

	/**
	 * 通过openid来查询用户
	 */
	@Override
	public User getByOpenId(String openid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from t_user where open_id = ? limit 1";
		return qr.query(sql, new BeanHandler<>(User.class), openid);
	}

	/**
	 * 注册用户
	 */
	@Override
	public int add(User user) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into t_user values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		int ret = qr.update(sql,user.getUserid(),
				user.getUsername(),
				user.getPassword(),
				user.getAvatar(),
				user.getSex(),
				user.getBirthday(),
				user.getEmail(),
				user.getRole().getRoleid(),
				user.getOrg().getOrgid(),
				user.getGrade().getGradeid(),
				user.getSclass().getClassid(),
				user.getProvince().getProvinceid(),
				user.getCity().getCityid(),
				user.getOpen_id(),
				user.getState());
		
		return ret;
	}

	/**
	 * 根据session_key获取用户
	 */
	@Override
	public User getUserBySessionId(String sessionId) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from t_user u,t_user_session s where s.client_key = ? and s.open_id=u.open_id;";
		Map<String, Object> query = qr.query(sql, new MapHandler(), sessionId);
		User user=new User();
		
		ConvertUtils.register(new DateConverter(null), java.util.Date.class);
		BeanUtils.populate(user, query);
		System.out.println("根据client_key查询出的查询出的用户:"+user.getUserid());
		return user;
	}

	/**
	 * 根据用户id和密码查询用户是否存在
	 */
	@Override
	public UserTwo queryByUidAndPass(String userid, String password) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select *  from t_user  where userid = ? and password=?  limit 1";
		Map<String, Object> query = qr.query(sql, new MapHandler(), userid,password);
		UserTwo user=new UserTwo();
		ConvertUtils.register(new DateConverter(null), java.util.Date.class);		//必须有这一句，获取的值有java中不识别的数据类型。如：java.sql.date，并不是java中的时间类型java.utils.date。
		BeanUtils.populate(user, query);
		return user;
		
	}

	/**
	 * 根据用户id查询用户具体信息
	 */
	@Override
	public UserTwo getUserInfoByUid(String userid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="SELECT * FROM t_user u,t_role ut,t_organization torg,t_grade tg,t_class tcl,t_province tp,t_city tci \r\n" + 
				"WHERE userid=? AND u.state='1' AND u.roleid=ut.roleid \r\n" + 
				"AND u.orgid=torg.orgid AND u.gradeid=tg.gradeid \r\n" + 
				"AND u.classid=tcl.classid AND u.provinceid=tp.provinceid AND u.cityid=tci.cityid ;";
		Map<String, Object> query = qr.query(sql, new MapHandler(), userid);
		UserTwo user=new UserTwo();
		
		ConvertUtils.register(new DateConverter(null), java.util.Date.class);		//必须有这一句，获取的值有java中不识别的数据类型。如：java.sql.date，并不是java中的时间类型java.utils.date。
		BeanUtils.populate(user, query);
		
		Role role=new Role();
		BeanUtils.populate(role, query);
		user.setRole(role);
		
		Organization org=new Organization();		
		BeanUtils.populate(org, query);
		user.setOrg(org);
		
		Grade grade=new Grade();
		BeanUtils.populate(grade, query);
		user.setGrade(grade);
		
		SClass sclass=new SClass();
		BeanUtils.populate(sclass, query);
		user.setSclass(sclass);
		
		Province province=new Province();
		BeanUtils.populate(province, query);
		user.setProvince(province);
		
		City city=new City();
		BeanUtils.populate(city, query);
		user.setCity(city);
		
		return user;

	}


	/**
	 * 获取所有用户信息
	 */
	@Override
	public List<User> getAllUser() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="SELECT * FROM t_user u ,t_role r,t_organization org,t_grade g,t_class cla,t_province pr,t_city c \r\n" + 
				"WHERE u.roleid=r.roleid AND u.orgid=org.orgid AND u.gradeid=g.gradeid \r\n" + 
				"AND u.classid=cla.classid AND u.provinceid=pr.provinceid AND u.cityid=c.cityid;";
		
		List<Map<String, Object>> query = qr.query(sql, new MapListHandler());
		List<User> list=new LinkedList<>();
		for (Map<String, Object> map : query) {
			Role role=new Role();
			BeanUtils.populate(role, map);
			
			Organization org=new Organization();
			BeanUtils.populate(org, map);
			
			Grade grade=new Grade();
			BeanUtils.populate(grade, map);

			SClass sclass=new SClass();
			BeanUtils.populate(sclass, map);

			Province province=new Province();
			BeanUtils.populate(province, map);

			City city=new City();
			BeanUtils.populate(city, map);

			User user =new User(role,org,grade,sclass,province,city);
			BeanUtils.populate(user, map);
			
			list.add(user);
		}
		return list;
		
	}


	/**
	 * 根据用户id删除一个用户
	 */
	@Override
	public int delOneUserByUid(String userid) throws Exception {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="delete from t_user where userid=?";
		return qr.update(sql, userid);
	}
	
	
	
	
	

	
	
}
