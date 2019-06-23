package indi.cyken.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * 与微信端的通信协议(暂时使用FEIPReusult作为通信协议，这个留待替换）
 * @author Administrator
 *
 */
public class WXResult {

	private Integer state;
	private Integer code;
	private String  msg;
	
    private Map<String, Object> result = null;
    
    public WXResult(){
    	this.state=0;
    }
    
    

	public WXResult(Integer state, Integer code, String msg) {
		super();
		this.state = state;
		this.code = code;
		this.msg = msg;
	}



	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
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

	public Map<String, Object> getResult() {
		return result;
	}


	
    public void setResult(Object object)
    {
        this.setData("data", object);
    }
    /**
     * @Description: 多个结果集需使用此方法，前台根据结果集名称获取不同的数据
     * @author: fallsea
     * @date: 2017年10月22日 下午8:30:42
     * @param name
     * @param object
     */
    public void setData(String name, Object object)
    {
        if(null == this.result)
        {
            this.result = new HashMap<String, Object>();
        }
        this.result.put(name, object);
    }
    
    
    
}
