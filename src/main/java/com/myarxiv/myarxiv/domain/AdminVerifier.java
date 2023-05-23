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
 * @TableName admin_verifier
 */
@TableName(value ="admin_verifier")
@Data
@Component
public class AdminVerifier implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer adminVerifierId;

    /**
     *
     */
    private String adminVerifierAccount;

    /**
     *
     */
    private String adminVerifierName;

    /**
     *
     */
    private String adminVerifierEmail;

    /**
     *
     */
    private Integer isAdmin;

    /**
     *
     */
    private String adminVerifierPassword;

    /**
     *
     */
    private String adminVerifierIntroduce;

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
        AdminVerifier other = (AdminVerifier) that;
        return (this.getAdminVerifierId() == null ? other.getAdminVerifierId() == null : this.getAdminVerifierId().equals(other.getAdminVerifierId()))
            && (this.getAdminVerifierAccount() == null ? other.getAdminVerifierAccount() == null : this.getAdminVerifierAccount().equals(other.getAdminVerifierAccount()))
            && (this.getAdminVerifierName() == null ? other.getAdminVerifierName() == null : this.getAdminVerifierName().equals(other.getAdminVerifierName()))
            && (this.getAdminVerifierEmail() == null ? other.getAdminVerifierEmail() == null : this.getAdminVerifierEmail().equals(other.getAdminVerifierEmail()))
            && (this.getIsAdmin() == null ? other.getIsAdmin() == null : this.getIsAdmin().equals(other.getIsAdmin()))
            && (this.getAdminVerifierPassword() == null ? other.getAdminVerifierPassword() == null : this.getAdminVerifierPassword().equals(other.getAdminVerifierPassword()))
            && (this.getAdminVerifierIntroduce() == null ? other.getAdminVerifierIntroduce() == null : this.getAdminVerifierIntroduce().equals(other.getAdminVerifierIntroduce()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAdminVerifierId() == null) ? 0 : getAdminVerifierId().hashCode());
        result = prime * result + ((getAdminVerifierAccount() == null) ? 0 : getAdminVerifierAccount().hashCode());
        result = prime * result + ((getAdminVerifierName() == null) ? 0 : getAdminVerifierName().hashCode());
        result = prime * result + ((getAdminVerifierEmail() == null) ? 0 : getAdminVerifierEmail().hashCode());
        result = prime * result + ((getIsAdmin() == null) ? 0 : getIsAdmin().hashCode());
        result = prime * result + ((getAdminVerifierPassword() == null) ? 0 : getAdminVerifierPassword().hashCode());
        result = prime * result + ((getAdminVerifierIntroduce() == null) ? 0 : getAdminVerifierIntroduce().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", adminVerifierId=").append(adminVerifierId);
        sb.append(", adminVerifierAccount=").append(adminVerifierAccount);
        sb.append(", adminVerifierName=").append(adminVerifierName);
        sb.append(", adminVerifierEmail=").append(adminVerifierEmail);
        sb.append(", isAdmin=").append(isAdmin);
        sb.append(", adminVerifierPassword=").append(adminVerifierPassword);
        sb.append(", adminVerifierIntroduce=").append(adminVerifierIntroduce);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
