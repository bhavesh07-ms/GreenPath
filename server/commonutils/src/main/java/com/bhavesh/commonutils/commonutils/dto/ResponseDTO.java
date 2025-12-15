package com.bhavesh.commonutils.commonutils.dto;

public class ResponseDTO<T> {

    private boolean success;
    private String message;
    private T data;

    public ResponseDTO() {}

    public ResponseDTO(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseDTO<T> ok(T data) {
        return new ResponseDTO<>(true, "OK", data);
    }

    public static <T> ResponseDTO<T> fail(String msg) {
        return new ResponseDTO<>(false, msg, null);
    }

    // Getters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }
}
