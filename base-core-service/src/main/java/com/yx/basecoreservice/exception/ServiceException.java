package com.yx.basecoreservice.exception;

import com.yx.basecoreservice.exception.enums.EnumExceptionResult;
import lombok.Data;

@Data
public class ServiceException extends RuntimeException {

	/**
	 * 代码
	 */
	private String code;
	/**
	 * 消息
	 */
	private String msg;


	/**
	 * 构造方法一
	 * 
	 * @param code
	 * @param msg
	 */
	public ServiceException(String code, String msg) {
		super(msg);
		this.code = code;
	}
	/**
	 * 构造方法二(传入枚举)
	 * @param resltEnum
	 */
	public ServiceException(EnumExceptionResult resltEnum) {
		super(resltEnum.getMsg());
		this.code = resltEnum.getCode();
	}

}
