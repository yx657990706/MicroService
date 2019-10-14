package com.yx.basecoreservice.utils;

import com.yx.appcore.model.MyResponse;

/**
 * 统一消息返回工具类
 * 
 * @author jesse
 *
 */
public class ResponseUtil {
	
	/**
	 * 成功(有返回数据)
	 * 
	 * @param object
	 * @return
	 */
	public static MyResponse success(final Object object) {
		if(object==null){
			return new MyResponse("0000","success");
		}
		return new MyResponse("0000","success",object);
	}
	/**
	 * 成功(无返回数据)
	 * 
	 * @return
	 */
	public static MyResponse success() {
		return success(null);
	}

	/**
	 * 失败
	 * 
	 * @param code
	 * @param msg
	 * @return
	 */
	public static MyResponse error(String code, String msg) {
		return  new MyResponse(code,msg);
	}
}
