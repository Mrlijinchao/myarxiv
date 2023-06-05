package com.myarxiv.myarxiv.domain;

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
 * @TableName user
 */
@Component
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer userId;

    /**
     *
     */
    private String userName;

    /**
     *
     */
    private String userEmail;

    /**
     *
     */
    private String userAccount;

    /**
     *
     */
    private String userPassword;

    /**
     *
     */
    private Integer userCountryId;

    /**
     *
     */
    private String userOrganization;

    /**
     *
     */
    private Integer careerStatusId;

    /**
     *
     */
    private Date userCreateTime;

    /**
     *
     */
    private Integer subjectId;

    /**
     *
     */
    private Integer defaultCategoryId;

    /**
     *
     */
    private String userHomePage;

    /**
     *
     */
    private Integer userStatus;

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
        User other = (User) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getUserEmail() == null ? other.getUserEmail() == null : this.getUserEmail().equals(other.getUserEmail()))
            && (this.getUserAccount() == null ? other.getUserAccount() == null : this.getUserAccount().equals(other.getUserAccount()))
            && (this.getUserPassword() == null ? other.getUserPassword() == null : this.getUserPassword().equals(other.getUserPassword()))
            && (this.getUserCountryId() == null ? other.getUserCountryId() == null : this.getUserCountryId().equals(other.getUserCountryId()))
            && (this.getUserOrganization() == null ? other.getUserOrganization() == null : this.getUserOrganization().equals(other.getUserOrganization()))
            && (this.getCareerStatusId() == null ? other.getCareerStatusId() == null : this.getCareerStatusId().equals(other.getCareerStatusId()))
            && (this.getUserCreateTime() == null ? other.getUserCreateTime() == null : this.getUserCreateTime().equals(other.getUserCreateTime()))
            && (this.getSubjectId() == null ? other.getSubjectId() == null : this.getSubjectId().equals(other.getSubjectId()))
            && (this.getDefaultCategoryId() == null ? other.getDefaultCategoryId() == null : this.getDefaultCategoryId().equals(other.getDefaultCategoryId()))
            && (this.getUserHomePage() == null ? other.getUserHomePage() == null : this.getUserHomePage().equals(other.getUserHomePage()))
            && (this.getUserStatus() == null ? other.getUserStatus() == null : this.getUserStatus().equals(other.getUserStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getUserEmail() == null) ? 0 : getUserEmail().hashCode());
        result = prime * result + ((getUserAccount() == null) ? 0 : getUserAccount().hashCode());
        result = prime * result + ((getUserPassword() == null) ? 0 : getUserPassword().hashCode());
        result = prime * result + ((getUserCountryId() == null) ? 0 : getUserCountryId().hashCode());
        result = prime * result + ((getUserOrganization() == null) ? 0 : getUserOrganization().hashCode());
        result = prime * result + ((getCareerStatusId() == null) ? 0 : getCareerStatusId().hashCode());
        result = prime * result + ((getUserCreateTime() == null) ? 0 : getUserCreateTime().hashCode());
        result = prime * result + ((getSubjectId() == null) ? 0 : getSubjectId().hashCode());
        result = prime * result + ((getDefaultCategoryId() == null) ? 0 : getDefaultCategoryId().hashCode());
        result = prime * result + ((getUserHomePage() == null) ? 0 : getUserHomePage().hashCode());
        result = prime * result + ((getUserStatus() == null) ? 0 : getUserStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append(", userEmail=").append(userEmail);
        sb.append(", userAccount=").append(userAccount);
        sb.append(", userPassword=").append(userPassword);
        sb.append(", userCountryId=").append(userCountryId);
        sb.append(", userOrganization=").append(userOrganization);
        sb.append(", careerStatusId=").append(careerStatusId);
        sb.append(", userCreateTime=").append(userCreateTime);
        sb.append(", subjectId=").append(subjectId);
        sb.append(", defaultCategoryId=").append(defaultCategoryId);
        sb.append(", userHomePage=").append(userHomePage);
        sb.append(", userStatus=").append(userStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
