package com.myarxiv.myarxiv.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myarxiv.myarxiv.domain.AdminVerifier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myarxiv.myarxiv.pojo.SubmissionMapById;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 时之始
* @description 针对表【admin_verifier】的数据库操作Mapper
* @createDate 2023-05-26 10:41:42
* @Entity com.myarxiv.myarxiv.domain.AdminVerifier
*/
@Mapper
public interface AdminVerifierMapper extends BaseMapper<AdminVerifier> {
    List<SubmissionMapById> getSubmissionAndPaper(@Param("submissionStatus") Integer status,@Param("subjectId") Integer subjectId,
                                                  @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    Integer getSubmissionCount(@Param("submissionStatus") Integer status,@Param("subjectId") Integer subjectId);
}




