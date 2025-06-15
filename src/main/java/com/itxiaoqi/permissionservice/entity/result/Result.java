package com.itxiaoqi.permissionservice.entity.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;


@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer code;//1成功，0失败
    private String msg;
    private T data;
    public static <T> Result<T> success() {
        return new Result<>(1, "请求成功", null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(1, "请求成功", data);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(0, msg, null);
    }
}