package com.myarxiv.myarxiv.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName paper
 */
@TableName(value ="paper")
@Data
public class Paper implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer paperId;

    /**
     * 
     */
    private Integer paperDetailId;

    /**
     * 
     */
    private Integer subjectId;

    /**
     * 
     */
    private Integer licenseId;

    /**
     * 
     */
    private String paperTitle;

    /**
     * 
     */
    private String paperAbstract;

    /**
     * 
     */
    private String paperIdentifier;

    /**
     * 
     */
    private String paperAuthors;

    /**
     * 
     */
    private String paperComments;

    /**
     * 
     */
    private Date paperCreateTime;

    /**
     * 
     */
    private Date paperUpdateTime;

    /**
     * 
     */
    private Integer paperStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Paper other = (Paper) that;
        return (this.getPaperId() == null ? other.getPaperId() == null : this.getPaperId().equals(other.getPaperId()))
            && (this.getPaperDetailId() == null ? other.getPaperDetailId() == null : this.getPaperDetailId().equals(other.getPaperDetailId()))
            && (this.getSubjectId() == null ? other.getSubjectId() == null : this.getSubjectId().equals(other.getSubjectId()))
            && (this.getLicenseId() == null ? other.getLicenseId() == null : this.getLicenseId().equals(other.getLicenseId()))
            && (this.getPaperTitle() == null ? other.getPaperTitle() == null : this.getPaperTitle().equals(other.getPaperTitle()))
            && (this.getPaperAbstract() == null ? other.getPaperAbstract() == null : this.getPaperAbstract().equals(other.getPaperAbstract()))
            && (this.getPaperIdentifier() == null ? other.getPaperIdentifier() == null : this.getPaperIdentifier().equals(other.getPaperIdentifier()))
            && (this.getPaperAuthors() == null ? other.getPaperAuthors() == null : this.getPaperAuthors().equals(other.getPaperAuthors()))
            && (this.getPaperComments() == null ? other.getPaperComments() == null : this.getPaperComments().equals(other.getPaperComments()))
            && (this.getPaperCreateTime() == null ? other.getPaperCreateTime() == null : this.getPaperCreateTime().equals(other.getPaperCreateTime()))
            && (this.getPaperUpdateTime() == null ? other.getPaperUpdateTime() == null : this.getPaperUpdateTime().equals(other.getPaperUpdateTime()))
            && (this.getPaperStatus() == null ? other.getPaperStatus() == null : this.getPaperStatus().equals(other.getPaperStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPaperId() == null) ? 0 : getPaperId().hashCode());
        result = prime * result + ((getPaperDetailId() == null) ? 0 : getPaperDetailId().hashCode());
        result = prime * result + ((getSubjectId() == null) ? 0 : getSubjectId().hashCode());
        result = prime * result + ((getLicenseId() == null) ? 0 : getLicenseId().hashCode());
        result = prime * result + ((getPaperTitle() == null) ? 0 : getPaperTitle().hashCode());
        result = prime * result + ((getPaperAbstract() == null) ? 0 : getPaperAbstract().hashCode());
        result = prime * result + ((getPaperIdentifier() == null) ? 0 : getPaperIdentifier().hashCode());
        result = prime * result + ((getPaperAuthors() == null) ? 0 : getPaperAuthors().hashCode());
        result = prime * result + ((getPaperComments() == null) ? 0 : getPaperComments().hashCode());
        result = prime * result + ((getPaperCreateTime() == null) ? 0 : getPaperCreateTime().hashCode());
        result = prime * result + ((getPaperUpdateTime() == null) ? 0 : getPaperUpdateTime().hashCode());
        result = prime * result + ((getPaperStatus() == null) ? 0 : getPaperStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", paperId=").append(paperId);
        sb.append(", paperDetailId=").append(paperDetailId);
        sb.append(", subjectId=").append(subjectId);
        sb.append(", licenseId=").append(licenseId);
        sb.append(", paperTitle=").append(paperTitle);
        sb.append(", paperAbstract=").append(paperAbstract);
        sb.append(", paperIdentifier=").append(paperIdentifier);
        sb.append(", paperAuthors=").append(paperAuthors);
        sb.append(", paperComments=").append(paperComments);
        sb.append(", paperCreateTime=").append(paperCreateTime);
        sb.append(", paperUpdateTime=").append(paperUpdateTime);
        sb.append(", paperStatus=").append(paperStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}