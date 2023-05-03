package com.myarxiv.myarxiv.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 *
 * @TableName endorsement
 */
@TableName(value ="endorsement")
@Data
@Component
public class Endorsement implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer endorsementId;

    /**
     *
     */
    private Integer userId;

    /**
     *
     */
    private Integer submissionId;

    /**
     *
     */
    private Integer paperId;

    /**
     *
     */
    private Integer endorsementStatus;

    /**
     *
     */
    private String endorsementCode;

    /**
     *
     */
    private Integer endorsorId;

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
        Endorsement other = (Endorsement) that;
        return (this.getEndorsementId() == null ? other.getEndorsementId() == null : this.getEndorsementId().equals(other.getEndorsementId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getSubmissionId() == null ? other.getSubmissionId() == null : this.getSubmissionId().equals(other.getSubmissionId()))
            && (this.getPaperId() == null ? other.getPaperId() == null : this.getPaperId().equals(other.getPaperId()))
            && (this.getEndorsementStatus() == null ? other.getEndorsementStatus() == null : this.getEndorsementStatus().equals(other.getEndorsementStatus()))
            && (this.getEndorsementCode() == null ? other.getEndorsementCode() == null : this.getEndorsementCode().equals(other.getEndorsementCode()))
            && (this.getEndorsorId() == null ? other.getEndorsorId() == null : this.getEndorsorId().equals(other.getEndorsorId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getEndorsementId() == null) ? 0 : getEndorsementId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getSubmissionId() == null) ? 0 : getSubmissionId().hashCode());
        result = prime * result + ((getPaperId() == null) ? 0 : getPaperId().hashCode());
        result = prime * result + ((getEndorsementStatus() == null) ? 0 : getEndorsementStatus().hashCode());
        result = prime * result + ((getEndorsementCode() == null) ? 0 : getEndorsementCode().hashCode());
        result = prime * result + ((getEndorsorId() == null) ? 0 : getEndorsorId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", endorsementId=").append(endorsementId);
        sb.append(", userId=").append(userId);
        sb.append(", submissionId=").append(submissionId);
        sb.append(", paperId=").append(paperId);
        sb.append(", endorsementStatus=").append(endorsementStatus);
        sb.append(", endorsementCode=").append(endorsementCode);
        sb.append(", endorsorId=").append(endorsorId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
