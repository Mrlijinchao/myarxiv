package com.myarxiv.myarxiv.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName category_primary
 */
@TableName(value ="category_primary")
@Data
public class CategoryPrimary implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer categoryPrimaryId;

    /**
     * 
     */
    private String categoryPrimaryName;

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
        CategoryPrimary other = (CategoryPrimary) that;
        return (this.getCategoryPrimaryId() == null ? other.getCategoryPrimaryId() == null : this.getCategoryPrimaryId().equals(other.getCategoryPrimaryId()))
            && (this.getCategoryPrimaryName() == null ? other.getCategoryPrimaryName() == null : this.getCategoryPrimaryName().equals(other.getCategoryPrimaryName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCategoryPrimaryId() == null) ? 0 : getCategoryPrimaryId().hashCode());
        result = prime * result + ((getCategoryPrimaryName() == null) ? 0 : getCategoryPrimaryName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", categoryPrimaryId=").append(categoryPrimaryId);
        sb.append(", categoryPrimaryName=").append(categoryPrimaryName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}