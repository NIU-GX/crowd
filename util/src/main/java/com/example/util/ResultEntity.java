package com.example.util;

/**
 * @author NGX
 * @Date 2020/4/30 19:50
 * @Description 对ajax请求返回的结果进行规范
 *
 * 统一整个项目中ajax请求返回的结果
 */
public class ResultEntity<T> {
    private static final String SUCCESS = "SUCCESS";
    private static final String FAILED = "FAILED";
    // 封装当前请求的处理结果是成功还是失败
    private String result;
    // 请求处理失败后的信息
    private String message;
    // 要返回的数据
    private T data;

    public ResultEntity(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public ResultEntity() {
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "result='" + result + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static <E> ResultEntity<E> successWithoutData() {
        return new ResultEntity<E>(SUCCESS,null,null);
    }
    public static <E> ResultEntity<E> sucessWithData(E data) {
        return new ResultEntity<E>(SUCCESS,null,data);
    }
    public static <E> ResultEntity<E> failed(String message) {
        return new ResultEntity<E>(FAILED,message,null  );
    }
}
