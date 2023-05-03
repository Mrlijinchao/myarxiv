package com.myarxiv.myarxiv;

public enum StatusCode {

    SUCCESS(200), FAIL(400);

    private Integer code;

    private StatusCode(Integer code){
        this.code = code;
    }

    public Integer getCode(){
        return this.code;
    }


}
