package com.yx.appcore.model;

public class MyResponse<T> {

	/**
	 * 错误码
	 */
	private String code;
	/**
	 * 提示信息
	 */
	private String msg;
	/**
	 * 数据内容
	 */
	private T data;

    public MyResponse(){

	}
	public MyResponse(String code, String msg){
		this.code = code;
		this.msg = msg;
	}
	public MyResponse(String code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
