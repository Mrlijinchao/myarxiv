package com.myarxiv.myarxiv.common.field;

import java.util.LinkedHashMap;

public class FieldMap {
    private static LinkedHashMap<String, String> hashMap;
    private static LinkedHashMap<String, String> detailHashMap;
    public static LinkedHashMap<String,String> getFieldMap(){
        if(hashMap == null){
            synchronized (FieldMap.class){
                if (hashMap == null){
                    hashMap = new LinkedHashMap<>();
                    Fields[] values = Fields.values();
                    for(Fields field : values){
                        hashMap.put(field.getField(),field.toString());
                    }
                }
            }
        }
        return hashMap;
    }

    public static LinkedHashMap<String,String> getDetailFieldMap(){
        if(detailHashMap == null){
            synchronized (FieldMap.class){
                if (detailHashMap == null){
                    detailHashMap = new LinkedHashMap<>();
                    DetailField[] values = DetailField.values();
                    for(DetailField field : values){
                        detailHashMap.put(field.getField(),field.toString());
                    }
                }
            }
        }
        return detailHashMap;
    }


}
