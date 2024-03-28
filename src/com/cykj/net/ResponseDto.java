package com.cykj.net;

/**
 * Description:
 * 返回报文，主要用于处理请求后返回客户端统一报文格式，主要包含状态码（code）、解释信息（msg）和数据（data）
 * @author Guguguy
 * @version 1.0
 * @since 2023/11/22 20:20
 */
public class ResponseDto {
    private final int code;
    private final String msg;
    private final Object data;

    /**
     * Description:
     * @param code 状态码
     * @param msg 需要传达的信息报文
     * @param data 需要传达的数据
     * @author Guguguy
     * @since 2023/11/24 20:38
     */
    public ResponseDto(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    @Override
    public String toString() {
        return "{code = " + code + " msg = " + msg + " data = " + data + "}";
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }
}
