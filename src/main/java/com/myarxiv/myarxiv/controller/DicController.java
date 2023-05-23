package com.myarxiv.myarxiv.controller;

import com.myarxiv.myarxiv.common.ResponseResult;
import com.myarxiv.myarxiv.common.StatusCode;
import com.myarxiv.myarxiv.domain.CareerStatus;
import com.myarxiv.myarxiv.domain.Country;
import com.myarxiv.myarxiv.domain.License;
import com.myarxiv.myarxiv.service.CareerStatusService;
import com.myarxiv.myarxiv.service.CountryService;
import com.myarxiv.myarxiv.service.LicenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/dic")
public class DicController {

    @Resource
    private CountryService countryService;

    @Resource
    private CareerStatusService careerStatusService;

    @Resource
    private LicenseService licenseService;

    @PostMapping("/addCountry")
    public Object addCountry(@RequestBody Country country){
        log.info("organizationMailbox",country);
        if (country == null){
            return ResponseResult.fail("参数为null", StatusCode.ERROR.getCode());
        }
        countryService.save(country);
        return ResponseResult.success("创建成功！");
    }

    @PostMapping("/removeCountry")
    public Object removeCountry(@RequestBody Country country){
        log.info("organizationMailbox",country);
        if (country == null){
            return ResponseResult.fail("参数为null", StatusCode.ERROR.getCode());
        }
        countryService.removeById(country);
        return ResponseResult.success("删除成功！");
    }

    @GetMapping("/getCountryList")
    public Object getCountryList(){
        return ResponseResult.success(countryService.list());
    }

    @PostMapping("/addCareerStatus")
    public Object addCareerStatus(@RequestBody CareerStatus careerStatus){
        log.info("organizationMailbox",careerStatus);
        if (careerStatus == null){
            return ResponseResult.fail("参数为null", StatusCode.ERROR.getCode());
        }
        careerStatusService.save(careerStatus);
        return ResponseResult.success("创建成功！");
    }

    @PostMapping("/removeCareerStatus")
    public Object removeCareerStatus(@RequestBody CareerStatus careerStatus){
        log.info("organizationMailbox",careerStatus);
        if (careerStatus == null){
            return ResponseResult.fail("参数为null", StatusCode.ERROR.getCode());
        }
        careerStatusService.removeById(careerStatus);
        return ResponseResult.success("删除成功！");
    }

    @GetMapping("/getCareerStatus")
    public Object getCareerStatus(){
        return ResponseResult.success(careerStatusService.list());
    }

    @PostMapping("/addLicense")
    public Object addLicense(@RequestBody License license){
        log.info("organizationMailbox",license);
        if (license == null){
            return ResponseResult.fail("参数为null", StatusCode.ERROR.getCode());
        }
        licenseService.save(license);
        return ResponseResult.success("创建成功！");
    }

    @PostMapping("/removeLicense")
    public Object removeLicense(@RequestBody License license){
        log.info("organizationMailbox",license);
        if (license == null){
            return ResponseResult.fail("参数为null", StatusCode.ERROR.getCode());
        }
        licenseService.removeById(license);
        return ResponseResult.success("删除成功！");
    }

    @GetMapping("/getLicense")
    public Object getLicense(){
        return ResponseResult.success(licenseService.list());
    }

}
