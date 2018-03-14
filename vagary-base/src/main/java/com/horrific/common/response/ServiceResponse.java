package com.horrific.common.response;



import com.horrific.common.enums.BaseResponseEnum;

import java.io.Serializable;

/**
 * 返回json统一格式
 * @author horrific
 * @version 2017-12-23
 * @param <T>
 */
public class ServiceResponse<T> implements Serializable {

    private static final long serialVersionUID = 2488663702267110932L;
    private int code;
    private String msg;
    private String detail;
    private T data;

    public static ServiceResponse<BaseResponseEnum> successResponse() {
        return new ServiceResponse<BaseResponseEnum>(BaseResponseEnum.SUCCESS);
    }

    public static ServiceResponse<BaseResponseEnum> failureResponse() {
        return new ServiceResponse<BaseResponseEnum>(BaseResponseEnum.FAILURE);
    }

    public ServiceResponse() {
        this.code = BaseResponseEnum.SUCCESS.getCode();
        this.msg = BaseResponseEnum.SUCCESS.getMsg();
    }

    public ServiceResponse(T data) {
        this.code = BaseResponseEnum.SUCCESS.getCode();
        this.msg = BaseResponseEnum.SUCCESS.getMsg();
        this.data = data;
    }

    public ServiceResponse(T data, String msg) {
        this.data = data;
        this.msg = msg;
    }

    public ServiceResponse(T data, int code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public ServiceResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ServiceResponse(int code, String msg, String detail) {
        this.code = code;
        this.msg = msg;
        this.detail = detail;
    }

    public ServiceResponse(ResponseCode ResponseCode, String detail) {
        this.code = ResponseCode.getCode();
        this.msg = ResponseCode.getMsg();
        this.detail = detail;
    }

    public ServiceResponse(ResponseCode ResponseCode) {
        this.code = ResponseCode.getCode();
        this.msg = ResponseCode.getMsg();
    }

    public static <T> ServiceResponse<T> illegalArg(T data) {
        return new ServiceResponse<T>(data, BaseResponseEnum.FAILURE.getCode(), BaseResponseEnum.PARAM_ERROR.getMsg());
    }

    public static <T> ServiceResponse<T> ok(T data) {
        return new ServiceResponse<T>(data, BaseResponseEnum.SUCCESS.getCode(), BaseResponseEnum.SUCCESS.getMsg());
    }

    public static <T> ServiceResponse<T> failure() {
        return failure(BaseResponseEnum.FAILURE.getMsg());
    }

    public static <T> ServiceResponse<T> failure(String msg) {
        return failure(null, BaseResponseEnum.FAILURE.getCode(), msg);
    }

    public static <T> ServiceResponse<T> failureBusiness(String msg) {
        return failure(null, BaseResponseEnum.FAILURE_BUSSINES.getCode(), msg);
    }

    public static <T> ServiceResponse<T> failure(int code, String msg) {
        return failure(null, code, msg);
    }

    public static <T> ServiceResponse<T> failure(T data, int code, String msg) {
        return new ServiceResponse<>(null, code, msg);
    }


    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return this.detail;
    }

    public <T extends ServiceResponse> T addDetail(String detail) {
        this.setDetail(detail);
        return (T) this;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
