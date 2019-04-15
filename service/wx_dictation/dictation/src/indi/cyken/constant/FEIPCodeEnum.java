package indi.cyken.constant;

public enum FEIPCodeEnum {
	

	OK(200,"成功"),
	VALUE_NULL(300,"值为空"),
	PARAM_NULL(301,"参数为空，处理异常"),
	SIGN_ERROR(400,"签名错误"),
	NO_LOGIN(401,"未登录"),
	PAY_ERROR(501,"支付异常"),
	ILLEGAL_SESSION(0001,"登录态异常");
	
	private Integer code;
	private String msg;
	private FEIPCodeEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	// 根据code返回msg信息
	public String getMsgByCode(Integer code) {
		for (FEIPCodeEnum entry : FEIPCodeEnum.values()) {
			if (entry.getCode().equals(code)) {
				return entry.getMsg();
			}
		}
		
		return "";
	}
	
	public Integer getCode() {
		return code;
	}
	
	public String getMsg() {
		return msg;
	}	
}

