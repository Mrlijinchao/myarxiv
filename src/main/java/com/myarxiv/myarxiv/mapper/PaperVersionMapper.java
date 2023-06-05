package com.myarxiv.myarxiv.mapper;

import com.myarxiv.myarxiv.domain.PaperVersion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author 时之始
* @description 针对表【paper_version】的数据库操作Mapper
* @createDate 2023-06-02 15:05:09
* @Entity com.myarxiv.myarxiv.domain.PaperVersion
*/
public interface PaperVersionMapper extends BaseMapper<PaperVersion> {

    @Select("select max(version_number) from paper_version where paper_id = #{paperId}")
    Integer getMaxVersionNum(@Param("paperId") Integer paperId);
}




