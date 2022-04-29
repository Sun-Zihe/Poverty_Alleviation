package com.fc.vo;

//返回消息的VO对象


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnMessageVO {
    private Integer code;
    private String message;
    private Boolean success;
    private Object data;
}
