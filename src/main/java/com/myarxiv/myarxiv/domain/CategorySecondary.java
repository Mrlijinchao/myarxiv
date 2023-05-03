package com.myarxiv.myarxiv.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName category_secondary
 */
@TableName(value ="category_secondary")
@Data
public class CategorySecondary implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer categorySecondaryId;

    /**
     * 
     */
    private String categorySecondaryName;

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
        CategorySecondary other = (CategorySecondary) that;
        return (this.getCategorySecondaryId() == null ? other.getCategorySecondaryId() == null : this.getCategorySecondaryId().equals(other.getCategorySecondaryId()))
            && (this.getCategorySecondaryName() == null ? other.getCategorySecondaryName() == null : this.getCategorySecondaryName().equals(other.getCategorySecondaryName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCategorySecondaryId() == null) ? 0 : getCategorySecondaryId().hashCode());
        result = prime * result + ((getCategorySecondaryName() == null) ? 0 : getCategorySecondaryName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", categorySecondaryId=").append(categorySecondaryId);
        sb.append(", categorySecondaryName=").append(categorySecondaryName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}