package com.myarxiv.myarxiv.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName paper_version
 */
@TableName(value ="paper_version")
@Data
public class PaperVersion implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer paperVersionId;

    /**
     * 
     */
    private Integer paperId;

    /**
     * 
     */
    private Integer submissionId;

    /**
     * 
     */
    private Integer versionStatus;

    /**
     * 
     */
    private Integer versionNumber;

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
        PaperVersion other = (PaperVersion) that;
        return (this.getPaperVersionId() == null ? other.getPaperVersionId() == null : this.getPaperVersionId().equals(other.getPaperVersionId()))
            && (this.getPaperId() == null ? other.getPaperId() == null : this.getPaperId().equals(other.getPaperId()))
            && (this.getSubmissionId() == null ? other.getSubmissionId() == null : this.getSubmissionId().equals(other.getSubmissionId()))
            && (this.getVersionStatus() == null ? other.getVersionStatus() == null : this.getVersionStatus().equals(other.getVersionStatus()))
            && (this.getVersionNumber() == null ? other.getVersionNumber() == null : this.getVersionNumber().equals(other.getVersionNumber()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPaperVersionId() == null) ? 0 : getPaperVersionId().hashCode());
        result = prime * result + ((getPaperId() == null) ? 0 : getPaperId().hashCode());
        result = prime * result + ((getSubmissionId() == null) ? 0 : getSubmissionId().hashCode());
        result = prime * result + ((getVersionStatus() == null) ? 0 : getVersionStatus().hashCode());
        result = prime * result + ((getVersionNumber() == null) ? 0 : getVersionNumber().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", paperVersionId=").append(paperVersionId);
        sb.append(", paperId=").append(paperId);
        sb.append(", submissionId=").append(submissionId);
        sb.append(", versionStatus=").append(versionStatus);
        sb.append(", versionNumber=").append(versionNumber);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}