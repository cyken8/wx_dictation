package indi.cyken.web.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.time.DateUtils;

import indi.cyken.constant.Constant;
import indi.cyken.constant.FEIPCodeEnum;
import indi.cyken.domain.FEIPResult;
import indi.cyken.domain.User;
import indi.cyken.domain.UserSession;
import indi.cyken.service.BookService;
import indi.cyken.service.SessionService;
import indi.cyken.service.UserService;
import indi.cyken.utils.AesCbcUtil;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.DateUtil;
import indi.cyken.utils.HttpRequest;
import indi.cyken.utils.JsonUtil;
import indi.cyken.utils.MD5Utils;
import indi.cyken.utils.UUIDUtils;
import indi.cyken.utils.WriteBackUtil;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class SessionServlet
 */

public class SessionServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * 获取微信用户登录态
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getSessionId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.封装数据
		System.out.println("调用了getSessionId Servlet");
		//2、获取参数
	    String encryptedData=request.getParameter("encryptedData");
	    String iv=request.getParameter("iv");
	    String code=request.getParameter("code");
	    String signature=request.getParameter("signature");
	    String rawData=request.getParameter("rawData");
	    
	
	     //小程序唯一标识   (在微信小程序管理后台获取)
        String wxspAppid = ResourceBundle.getBundle("wxapp").getString("wxspAppid");
        //小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = ResourceBundle.getBundle("wxapp").getString("wxspSecret");
        //授权（必填）
        String grant_type = ResourceBundle.getBundle("wxapp").getString("grant_type");

        UserSession us=getWxSessionObject(wxspAppid, wxspSecret, grant_type, code);
        if(us==null) {
        	return "fail:从微信服务器获取微信登录态失败";
        }
        String session_key=us.getSeesion_key();
        String openid=us.getOpen_id();
        String client_key=us.getClient_key();
        try {
            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            if (null != result && result.length() > 0) {
                JSONObject userInfoJSON = JSONObject.fromObject(result);
                System.out.println(userInfoJSON);
                
                //2、根据openid判断用户是否存在
                UserService uservice=(UserService) BeanFactory.getBean("UserService");
                SessionService ss=(SessionService) BeanFactory.getBean("SessionService");

                User user=uservice.getByOpenId(openid);
                if(user==null) {
                	//TODO 3.不存在则添加，这里的用户信息要讲名称转换为对应的编号村，使用一些临时编号数据设置
                	user=new User();
                	user.setUserid("U"+UUIDUtils.getCode());
                	user.setUsername((String)userInfoJSON.get("nickName"));
                	user.setPassword("123");
                	user.setAvatar((String)userInfoJSON.get("avatarUrl"));
                	user.setState("1");
                	user.setSex("男");
                	user.setProvinceid("PR001");
                	user.setCityid("CT001");
                	user.setOpen_id(openid);
                	int ret=uservice.add(user);
                	if(ret==0) {
                		System.out.println("注册用户失败 "+ret);
                		return "fail:注册用户识别";
                		
                	}
                    //4.存在用户则直接调用SessionService添加登录态
                    ret=ss.add(us);
                    if(ret==0) {
                    	return "fail:添加登录态失败";
                    }else {
                    	JSONObject data=new JSONObject();
                        data.put("sessionId",client_key );
                        System.out.println("添加后返回给签到的session_id: "+ client_key);
                        response.getWriter().write(JsonUtil.object2json(data));
                    	return "success:添加登录态成功";
                        
                    }
                	
                }else {
                	//用户已经存在，则直接更新登录态
                    int ret=ss.update(us);
                    if(ret!=0) {
                        JSONObject data=new JSONObject();
                        FEIPResult feipResult=new FEIPResult();
                        data.put("sessionId",client_key );
                        System.out.println("更新后返回给签到的session_id: "+ client_key);

//                        feipResult=new FEIPResult(Constant.WRITE_BACK_SUCCESS,
//                        		FEIPCodeEnum.OK.getCode(),
//                        		FEIPCodeEnum.OK.getMsg(),
//                        		data);
//                        WriteBackUtil.WriteBackJsonStr(feipResult, response);
                        response.getWriter().write(JsonUtil.object2json(data));
                        return "success:更新登录态成功";

                    }else {
                    	return "fail：更新登录态失败";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	    
		return null;
	}
	
	
	
	/**
	 * 从微信服务器获取微信小程序登录态
	 * @param wxspAppid
	 * @param wxspSecret
	 * @param grant_type
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public UserSession getWxSessionObject(String wxspAppid,String wxspSecret,String grant_type,String code) throws Exception {
	  
        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////

	        //请求参数
	        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=" + grant_type;
	        //发送请求
	        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
	        
	        //解析相应内容（转换成json对象）
	        JSONObject json = JSONObject.fromObject(sr);
	        System.out.println("UserInfo ："+sr);
	        //获取会话密钥（session_key）
	        String session_key = json.get("session_key").toString();
	        //用户的唯一标识（openid）
	        String openid = (String) json.get("openid");  
	        //session_key的有效时间长度（s)
	        int expire_in=(int)json.get("expires_in");
	        String nextTimeS = DateUtil.getPreTimeS(new Date(),expire_in );
	        Timestamp expire_date = DateUtil.strToDateSql(nextTimeS);
	        String client_key=MD5Utils.md5(session_key);
	        
	        //组装登录态
	        UserSession us=new UserSession();
	        us.setSeesion_key(session_key);
	        us.setOpen_id(openid);
	        us.setExpire_date(expire_date);
	        us.setClient_key(client_key);
	        
	        System.out.println("从微信服务器获取的登录态：" +us.toString());
		
		return us;
		
	}
	

}
