package com.myarxiv.myarxiv.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName organization_mailbox
 */
@TableName(value ="organization_mailbox")
@Data
public class OrganizationMailbox implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer organizationMailboxId;

    /**
     * 
     */
    private String organizationMailboxName;

    /**
     * 
     */
    private String organizationMailboxEmail;

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
        OrganizationMailbox other = (OrganizationMailbox) that;
        return (this.getOrganizationMailboxId() == null ? other.getOrganizationMailboxId() == null : this.getOrganizationMailboxId().equals(other.getOrganizationMailboxId()))
            && (this.getOrganizationMailboxName() == null ? other.getOrganizationMailboxName() == null : this.getOrganizationMailboxName().equals(other.getOrganizationMailboxName()))
            && (this.getOrganizationMailboxEmail() == null ? other.getOrganizationMailboxEmail() == null : this.getOrganizationMailboxEmail().equals(other.getOrganizationMailboxEmail()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrganizationMailboxId() == null) ? 0 : getOrganizationMailboxId().hashCode());
        result = prime * result + ((getOrganizationMailboxName() == null) ? 0 : getOrganizationMailboxName().hashCode());
        result = prime * result + ((getOrganizationMailboxEmail() == null) ? 0 : getOrganizationMailboxEmail().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", organizationMailboxId=").append(organizationMailboxId);
        sb.append(", organizationMailboxName=").append(organizationMailboxName);
        sb.append(", organizationMailboxEmail=").append(organizationMailboxEmail);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}