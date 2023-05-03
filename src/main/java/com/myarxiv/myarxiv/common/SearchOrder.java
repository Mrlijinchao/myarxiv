package com.myarxiv.myarxiv.common;

public enum SearchOrder {

    NEW(0),OLD(1);

    private Integer code;

    SearchOrder(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}
