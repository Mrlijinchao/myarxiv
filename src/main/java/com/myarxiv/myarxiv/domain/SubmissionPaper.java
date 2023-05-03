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
 * @TableName submission_paper
 */
@TableName(value ="submission_paper")
@Data
public class SubmissionPaper implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer submissionPaperId;

    /**
     * 
     */
    private Integer submissionPaperDetailId;

    /**
     * 
     */
    private Integer licenseId;

    /**
     * 
     */
    private Integer submissionSubjectId;

    /**
     * 
     */
    private String submissionPaperTitle;

    /**
     * 
     */
    private String submissionPaperAbstract;

    /**
     * 
     */
    private String submissionPaperIdentifier;

    /**
     * 
     */
    private Date submissionPaperCreateTime;

    /**
     * 
     */
    private String submissionPaperComments;

    /**
     * 
     */
    private Date submissionPaperUpdateTime;

    /**
     * 
     */
    private String submissionPaperAuthors;

    /**
     * 
     */
    private Integer submissionPaperStatus;

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
        SubmissionPaper other = (SubmissionPaper) that;
        return (this.getSubmissionPaperId() == null ? other.getSubmissionPaperId() == null : this.getSubmissionPaperId().equals(other.getSubmissionPaperId()))
            && (this.getSubmissionPaperDetailId() == null ? other.getSubmissionPaperDetailId() == null : this.getSubmissionPaperDetailId().equals(other.getSubmissionPaperDetailId()))
            && (this.getLicenseId() == null ? other.getLicenseId() == null : this.getLicenseId().equals(other.getLicenseId()))
            && (this.getSubmissionSubjectId() == null ? other.getSubmissionSubjectId() == null : this.getSubmissionSubjectId().equals(other.getSubmissionSubjectId()))
            && (this.getSubmissionPaperTitle() == null ? other.getSubmissionPaperTitle() == null : this.getSubmissionPaperTitle().equals(other.getSubmissionPaperTitle()))
            && (this.getSubmissionPaperAbstract() == null ? other.getSubmissionPaperAbstract() == null : this.getSubmissionPaperAbstract().equals(other.getSubmissionPaperAbstract()))
            && (this.getSubmissionPaperIdentifier() == null ? other.getSubmissionPaperIdentifier() == null : this.getSubmissionPaperIdentifier().equals(other.getSubmissionPaperIdentifier()))
            && (this.getSubmissionPaperCreateTime() == null ? other.getSubmissionPaperCreateTime() == null : this.getSubmissionPaperCreateTime().equals(other.getSubmissionPaperCreateTime()))
            && (this.getSubmissionPaperComments() == null ? other.getSubmissionPaperComments() == null : this.getSubmissionPaperComments().equals(other.getSubmissionPaperComments()))
            && (this.getSubmissionPaperUpdateTime() == null ? other.getSubmissionPaperUpdateTime() == null : this.getSubmissionPaperUpdateTime().equals(other.getSubmissionPaperUpdateTime()))
            && (this.getSubmissionPaperAuthors() == null ? other.getSubmissionPaperAuthors() == null : this.getSubmissionPaperAuthors().equals(other.getSubmissionPaperAuthors()))
            && (this.getSubmissionPaperStatus() == null ? other.getSubmissionPaperStatus() == null : this.getSubmissionPaperStatus().equals(other.getSubmissionPaperStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSubmissionPaperId() == null) ? 0 : getSubmissionPaperId().hashCode());
        result = prime * result + ((getSubmissionPaperDetailId() == null) ? 0 : getSubmissionPaperDetailId().hashCode());
        result = prime * result + ((getLicenseId() == null) ? 0 : getLicenseId().hashCode());
        result = prime * result + ((getSubmissionSubjectId() == null) ? 0 : getSubmissionSubjectId().hashCode());
        result = prime * result + ((getSubmissionPaperTitle() == null) ? 0 : getSubmissionPaperTitle().hashCode());
        result = prime * result + ((getSubmissionPaperAbstract() == null) ? 0 : getSubmissionPaperAbstract().hashCode());
        result = prime * result + ((getSubmissionPaperIdentifier() == null) ? 0 : getSubmissionPaperIdentifier().hashCode());
        result = prime * result + ((getSubmissionPaperCreateTime() == null) ? 0 : getSubmissionPaperCreateTime().hashCode());
        result = prime * result + ((getSubmissionPaperComments() == null) ? 0 : getSubmissionPaperComments().hashCode());
        result = prime * result + ((getSubmissionPaperUpdateTime() == null) ? 0 : getSubmissionPaperUpdateTime().hashCode());
        result = prime * result + ((getSubmissionPaperAuthors() == null) ? 0 : getSubmissionPaperAuthors().hashCode());
        result = prime * result + ((getSubmissionPaperStatus() == null) ? 0 : getSubmissionPaperStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", submissionPaperId=").append(submissionPaperId);
        sb.append(", submissionPaperDetailId=").append(submissionPaperDetailId);
        sb.append(", licenseId=").append(licenseId);
        sb.append(", submissionSubjectId=").append(submissionSubjectId);
        sb.append(", submissionPaperTitle=").append(submissionPaperTitle);
        sb.append(", submissionPaperAbstract=").append(submissionPaperAbstract);
        sb.append(", submissionPaperIdentifier=").append(submissionPaperIdentifier);
        sb.append(", submissionPaperCreateTime=").append(submissionPaperCreateTime);
        sb.append(", submissionPaperComments=").append(submissionPaperComments);
        sb.append(", submissionPaperUpdateTime=").append(submissionPaperUpdateTime);
        sb.append(", submissionPaperAuthors=").append(submissionPaperAuthors);
        sb.append(", submissionPaperStatus=").append(submissionPaperStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}