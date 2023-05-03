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
 * @TableName submission
 */
@TableName(value ="submission")
@Data
public class Submission implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer submissionId;

    /**
     * 
     */
    private Integer userId;

    /**
     * 
     */
    private Integer submissionPaperId;

    /**
     * 
     */
    private Integer paperId;

    /**
     * 
     */
    private String submissionIdentifier;

    /**
     * 
     */
    private String submissionTitle;

    /**
     * 
     */
    private String submissionType;

    /**
     * 
     */
    private Date submissionExpires;

    /**
     * 
     */
    private Date submissionCreateTime;

    /**
     * 
     */
    private Integer submissionStatus;

    /**
     * 
     */
    private Integer submissionStep;

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
        Submission other = (Submission) that;
        return (this.getSubmissionId() == null ? other.getSubmissionId() == null : this.getSubmissionId().equals(other.getSubmissionId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getSubmissionPaperId() == null ? other.getSubmissionPaperId() == null : this.getSubmissionPaperId().equals(other.getSubmissionPaperId()))
            && (this.getPaperId() == null ? other.getPaperId() == null : this.getPaperId().equals(other.getPaperId()))
            && (this.getSubmissionIdentifier() == null ? other.getSubmissionIdentifier() == null : this.getSubmissionIdentifier().equals(other.getSubmissionIdentifier()))
            && (this.getSubmissionTitle() == null ? other.getSubmissionTitle() == null : this.getSubmissionTitle().equals(other.getSubmissionTitle()))
            && (this.getSubmissionType() == null ? other.getSubmissionType() == null : this.getSubmissionType().equals(other.getSubmissionType()))
            && (this.getSubmissionExpires() == null ? other.getSubmissionExpires() == null : this.getSubmissionExpires().equals(other.getSubmissionExpires()))
            && (this.getSubmissionCreateTime() == null ? other.getSubmissionCreateTime() == null : this.getSubmissionCreateTime().equals(other.getSubmissionCreateTime()))
            && (this.getSubmissionStatus() == null ? other.getSubmissionStatus() == null : this.getSubmissionStatus().equals(other.getSubmissionStatus()))
            && (this.getSubmissionStep() == null ? other.getSubmissionStep() == null : this.getSubmissionStep().equals(other.getSubmissionStep()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSubmissionId() == null) ? 0 : getSubmissionId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getSubmissionPaperId() == null) ? 0 : getSubmissionPaperId().hashCode());
        result = prime * result + ((getPaperId() == null) ? 0 : getPaperId().hashCode());
        result = prime * result + ((getSubmissionIdentifier() == null) ? 0 : getSubmissionIdentifier().hashCode());
        result = prime * result + ((getSubmissionTitle() == null) ? 0 : getSubmissionTitle().hashCode());
        result = prime * result + ((getSubmissionType() == null) ? 0 : getSubmissionType().hashCode());
        result = prime * result + ((getSubmissionExpires() == null) ? 0 : getSubmissionExpires().hashCode());
        result = prime * result + ((getSubmissionCreateTime() == null) ? 0 : getSubmissionCreateTime().hashCode());
        result = prime * result + ((getSubmissionStatus() == null) ? 0 : getSubmissionStatus().hashCode());
        result = prime * result + ((getSubmissionStep() == null) ? 0 : getSubmissionStep().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", submissionId=").append(submissionId);
        sb.append(", userId=").append(userId);
        sb.append(", submissionPaperId=").append(submissionPaperId);
        sb.append(", paperId=").append(paperId);
        sb.append(", submissionIdentifier=").append(submissionIdentifier);
        sb.append(", submissionTitle=").append(submissionTitle);
        sb.append(", submissionType=").append(submissionType);
        sb.append(", submissionExpires=").append(submissionExpires);
        sb.append(", submissionCreateTime=").append(submissionCreateTime);
        sb.append(", submissionStatus=").append(submissionStatus);
        sb.append(", submissionStep=").append(submissionStep);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}