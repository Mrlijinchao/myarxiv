package com.myarxiv.myarxiv;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.myarxiv.myarxiv.common.field.FieldMap;
import com.myarxiv.myarxiv.common.Tools;
import com.myarxiv.myarxiv.mail.SendMail;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ClassUtils;

import javax.mail.MessagingException;
import java.util.*;

@Slf4j
@SpringBootTest
class MyarxivApplicationTests {



    @Test
    void contextLoads() {
//        System.out.println(StatusCode.SUCCESS.getCode());
//        log.info("你好");


//        Date date = new Date();
//        Date expireDate = DateUtils.addDate(date, 15);
//        System.out.println(date);
//        System.out.println(expireDate);
//        String s = DateUtils.formateDate(date);
//        String s1 = DateUtils.formateDate(expireDate);
//        System.out.println(s);
//        System.out.println(s1);


//        String email = "http://localhost:8088/static/files/2023-04/2b148c59-b8b2-440c-a834-e92c6f3c884b.pdf";
//
////        int i = email.lastIndexOf('@');
//        String substring = email.substring(email.substring(0, email.lastIndexOf('/')).lastIndexOf('/')+1);
//        System.out.println(substring);
//        System.out.println(i);
//        String substring = email.substring(i + 1);
//        System.out.println(substring);
//        String[] split = substring.split("\\.");
//        System.out.println(Arrays.toString(split));
//        String str = split[split.length-2] + "." + split[split.length-1];
//        System.out.println(str);


//        Date date = new Date();
//        //获取当前系统时间年月这里获取到月如果要精确到日修改("yyyy-MM-dd")
//        String dateForm = new SimpleDateFormat("yyyy").format(date);
//        System.out.println(dateForm);
//        dateForm = dateForm + "." + new SimpleDateFormat("MM").format(date);
//        System.out.println(dateForm);
//        dateForm = dateForm + new SimpleDateFormat("dd").format(date);
//        System.out.println(dateForm);
//        dateForm = dateForm + new SimpleDateFormat("ss").format(date);
//        System.out.println(dateForm);

        String endorsementCode = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
        System.out.println(endorsementCode);

    }

    @Test
    public void jsonStrToJSONArray2() {
        String str = "{\"errors\":[{\"code\":\"UUM70004\",\"message\":\"组织单元名称不能为空\",\"data\":{\"id\":\"254\",\"suborderNo\":\"SUB_2018062797348039\",\"organUnitType\":\"部门\",\"action\":\"add\",\"parent\":\"10000\",\"ordinal\":0,\"organUnitFullName\":\"组织单元全称\"},\"success\":false},{\"code\":\"UUM70004\",\"message\":\"组织单元名称不能为空\",\"data\":{\"id\":\"255\",\"suborderNo\":\"SUB_2018062797348039\",\"organUnitType\":\"部门\",\"action\":\"add\",\"parent\":\"10000\",\"ordinal\":0,\"organUnitFullName\":\"组织单元全称\"},\"success\":false}]}";
        //获取jsonobject对象
        JSONObject jsonObject = JSON.parseObject(str);
        //把对象转换成jsonArray数组
        JSONArray error = jsonObject.getJSONArray("errors");
        //error==>[{"code":"UUM70004","message":"组织单元名称不能为空","data":{"id":"254","suborderNo":"SUB_2018062797348039","organUnitType":"部门","action":"add","parent":"10000","ordinal":0,"organUnitFullName":"组织单元全称"},"success":false},{"code":"UUM70004","message":"组织单元名称不能为空","data":{"id":"255","suborderNo":"SUB_2018062797348039","organUnitType":"部门","action":"add","parent":"10000","ordinal":0,"organUnitFullName":"组织单元全称"},"success":false}]
        //将数组转换成字符串
        String jsonString = JSONObject.toJSONString(error);//将array数组转换成字符串
        //将字符串转成list集合
        List<Error> errors = JSONObject.parseArray(jsonString, Error.class);//把字符串转换成集合

        System.out.println(errors);


    }

    @Test
    public void testSendEmail() throws MessagingException {
        SendMail.sendMail("li2116639781@sina.com",SendMail.getEndorsementContent("李锦超","计算机科学","679123"),"背书验证邮件");

        System.out.println("发送邮件成功！");

    }


    @Test
    public void testMD5(){


//        String s = DigestUtils.md5DigestAsHex("4312".getBytes());
//        System.out.println(s);


        String s = Tools.randomCode();
        System.out.println(s);
    }


    @Test
    public void testMap(){

//        LinkedHashMap<String, String> fieldMap = FieldMap.getFieldMap();
//        for (String key : fieldMap.keySet()){
//            System.out.println(key + ":" + fieldMap.get(key));
//        }

//        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
//        System.out.println(path);
//        ClassPathResource classPathResource = new ClassPathResource("static/index.html");
//        System.out.println(classPathResource.getPath());

        String str = "1";
        String[] split = str.split(",");

        System.out.println(Arrays.toString(split));

    }

    @Test
    public void test(){
        int[] arr = {1,2,3,4,5};
        System.out.println(Unknown(arr, 1, 4));
    }

    public int Unknown(int arr[], int i,int n){
        if(i == n-1){
            return arr[i];
        }else{
            int tmp = Unknown(arr,i+1,n);
            if(tmp < arr[i]){
                return arr[i];
            }else{
                return tmp;
            }
        }

    }


}

