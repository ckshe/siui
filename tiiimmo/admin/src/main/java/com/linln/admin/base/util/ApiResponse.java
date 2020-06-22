package com.linln.admin.base.util;

import org.springframework.validation.BindingResult;

import java.io.Serializable;

/**
 * api格式封装
 * TangJM
 *
 */
public class ApiResponse implements Serializable {

	private int code;
	private String msg;
	private Object data;
	private boolean more;
	private String timeStamp;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	private Integer count;


	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public ApiResponse() {
		super();
	}

	public ApiResponse(int code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public ApiResponse(int code, String msg, String timeStamp) {
		this.code = code;
		this.msg = msg;
		this.timeStamp = timeStamp;
	}

	public ApiResponse(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getmsg() {
		return msg;
	}
	public void setmsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public boolean isMore() {
		return more;
	}
	public void setMore(boolean more) {
		this.more = more;
	}

	public static ApiResponse ofmsg(int code, String msg) {
		return new ApiResponse(code, msg, null);
	}



	public static ApiResponse ofmsg(int code, String msg, String timeStamp) {
		return new ApiResponse(code, msg, timeStamp);
	}
	public static ApiResponse ofmsg(int code, String msg, Object data) {
		return new ApiResponse(code, msg, data);
	}

	public static ApiResponse ofmsg(StatusCode statusCode, String msg) {
		return new ApiResponse(statusCode.getCode(), msg, null);
	}
	public static ApiResponse ofmsg(String msg) {
		return new ApiResponse(msg);
	}

	/**
	 * 参数校验错误时使用，返回错误信息
	 * @param bindingResult
	 * @return
	 *//*
	public static ApiResponse ofParamError(BindingResult bindingResult) {
		return new ApiResponse(StatusCode.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultmsg(), null);
	}*/

	public static ApiResponse ofParamError(String msg){
		return new ApiResponse(StatusCode.PARAM_ERROR.getCode(), msg ,null);
	}

	public static ApiResponse ofError(String msg){
		return new ApiResponse(StatusCode.ERROR.getCode(), msg ,null);
	}

	/*public static ApiResponse ofSuccess(Object data) {
		return new ApiResponse(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getStandardmsg(), data);
	}*/

	public static ApiResponse ofSuccess(String msg) {
		return new ApiResponse(StatusCode.SUCCESS.getCode(), msg, null);
	}

	/*public static ApiResponse ofSuccess(String msg, Object data) {
		return new ApiResponse(StatusCode.SUCCESS.getCode(), msg, data);
	}

	public static ApiResponse ofStatus(StatusCode statusCode) {
		return new ApiResponse(statusCode.getCode(), statusCode.getStandardmsg(), null);
	}*/


}
