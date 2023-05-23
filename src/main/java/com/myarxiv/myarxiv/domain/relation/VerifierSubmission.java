package com.myarxiv.myarxiv.domain.relation;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 *
 * @TableName verifier_submission
 */
@TableName(value ="verifier_submission")
@Data
@Component
public class VerifierSubmission implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer verifierSubmissionId;

    /**
     *
     */
    private Integer verifierId;

    /**
     *
     */
    private Integer submissionId;

    /**
     *
     */
    private Integer verifierSubmissionStatus;

    /**
     *
     */
    private Date verifierSubmissionDate;

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
        VerifierSubmission other = (VerifierSubmission) that;
        return (this.getVerifierSubmissionId() == null ? other.getVerifierSubmissionId() == null : this.getVerifierSubmissionId().equals(other.getVerifierSubmissionId()))
            && (this.getVerifierId() == null ? other.getVerifierId() == null : this.getVerifierId().equals(other.getVerifierId()))
            && (this.getSubmissionId() == null ? other.getSubmissionId() == null : this.getSubmissionId().equals(other.getSubmissionId()))
            && (this.getVerifierSubmissionStatus() == null ? other.getVerifierSubmissionStatus() == null : this.getVerifierSubmissionStatus().equals(other.getVerifierSubmissionStatus()))
            && (this.getVerifierSubmissionDate() == null ? other.getVerifierSubmissionDate() == null : this.getVerifierSubmissionDate().equals(other.getVerifierSubmissionDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getVerifierSubmissionId() == null) ? 0 : getVerifierSubmissionId().hashCode());
        result = prime * result + ((getVerifierId() == null) ? 0 : getVerifierId().hashCode());
        result = prime * result + ((getSubmissionId() == null) ? 0 : getSubmissionId().hashCode());
        result = prime * result + ((getVerifierSubmissionStatus() == null) ? 0 : getVerifierSubmissionStatus().hashCode());
        result = prime * result + ((getVerifierSubmissionDate() == null) ? 0 : getVerifierSubmissionDate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", verifierSubmissionId=").append(verifierSubmissionId);
        sb.append(", verifierId=").append(verifierId);
        sb.append(", submissionId=").append(submissionId);
        sb.append(", verifierSubmissionStatus=").append(verifierSubmissionStatus);
        sb.append(", verifierSubmissionDate=").append(verifierSubmissionDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
