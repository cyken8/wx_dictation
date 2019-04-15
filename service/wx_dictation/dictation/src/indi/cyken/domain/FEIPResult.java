package indi.cyken.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 前端，后端交互规范
 * @author Yong
 *
 */
public class FEIPResult implements Serializable {
	
	private Integer state; 		//状态，1=成功，0=识别
	private Integer code;				//状态码
	private String msg;			    //消息
	private Object data;			//数据
	
	
	public Integer getStatus() {
		return state;
	}
	public void setStatus(Integer state) {
		this.state = state;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	/**
	 * 无参构造器
	 */
	public FEIPResult(){
		super();
	}

	

	/**
	 * 只返回状态，状态码，消息
	 * @param state
	 * @param code
	 * @param msg
	 */
	public FEIPResult(Integer state, Integer code, String msg){
		super();
		this.state=state;
		this.code=code;
		this.msg=msg;
	}
	

	/**
	 * 只返回状态，状态码，数据对象
	 * @param state
	 * @param code
	 * @param data
	 */
	public FEIPResult(Integer state, Integer code, Object data){
		super();
		this.state=state;
		this.code=code;
		this.data=data;
	}
	

	/**
	 * 返回全部信息即状态，状态码，消息，数据对象
	 * @param state
	 * @param code
	 * @param msg
	 * @param data
	 */
	public FEIPResult(Integer state, Integer code, String msg, Object data){
		super();
		this.state=state;
		this.code=code;
		this.msg=msg;
		this.data=data;
	}


	/**
	 * 将所有成员集合在一个字符串中
	 */
	public String toString() {
		Map<String, Object> map = new HashMap<>();
		map.put("state", state);
		map.put("msg", msg);
		map.put("data", data);
		return JSONObject.fromObject(map).toString();
	}
	

}
