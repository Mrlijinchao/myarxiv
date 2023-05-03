package com.myarxiv.myarxiv.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName license
 */
@TableName(value ="license")
@Data
public class License implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer licenseId;

    /**
     * 
     */
    private String licenseDescription;

    /**
     * 
     */
    private String licenseLink;

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
        License other = (License) that;
        return (this.getLicenseId() == null ? other.getLicenseId() == null : this.getLicenseId().equals(other.getLicenseId()))
            && (this.getLicenseDescription() == null ? other.getLicenseDescription() == null : this.getLicenseDescription().equals(other.getLicenseDescription()))
            && (this.getLicenseLink() == null ? other.getLicenseLink() == null : this.getLicenseLink().equals(other.getLicenseLink()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLicenseId() == null) ? 0 : getLicenseId().hashCode());
        result = prime * result + ((getLicenseDescription() == null) ? 0 : getLicenseDescription().hashCode());
        result = prime * result + ((getLicenseLink() == null) ? 0 : getLicenseLink().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", licenseId=").append(licenseId);
        sb.append(", licenseDescription=").append(licenseDescription);
        sb.append(", licenseLink=").append(licenseLink);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}