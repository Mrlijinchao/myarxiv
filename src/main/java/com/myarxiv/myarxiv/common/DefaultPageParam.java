package com.myarxiv.myarxiv.common;

public enum DefaultPageParam {

    DEFAULT_PAGE_SIZE(3),DEFAULT_PAGE_NUM(1);
    private Integer param;
    DefaultPageParam(Integer param){
        this.param = param;
    }

    public Integer getParam(){
        return this.param;
    }

}
