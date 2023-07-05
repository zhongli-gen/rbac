package com.situ.rbac.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class Result {
    private Integer code;
    private String msg;
    private Object data;
    //成功
    public  static Result success(){
        return  success(null);
    }
    public  static Result success(Object data){
        Result result =new Result();
        result.setCode(0);
        result.setData(data);
        return result;

    }
    //失败
    public static Result fail(String msg){
        Result result =new Result();
        result.setCode(1);
        result.setMsg(msg);
        return result;

    }
}
