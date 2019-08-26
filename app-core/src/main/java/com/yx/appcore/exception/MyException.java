package com.yx.appcore.exception;

import com.yx.appcore.exception.enums.EnumExceptionResult;
import lombok.Data;

@Data
public class MyException extends RuntimeException {

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
	public MyException(String code, String msg) {
		super(msg);
		this.code = code;
	}
	/**
	 * 构造方法二(传入枚举)
	 * @param resltEnum
	 */
	public MyException(EnumExceptionResult resltEnum) {
		super(resltEnum.getMsg());
		this.code = resltEnum.getCode();
	}

}
