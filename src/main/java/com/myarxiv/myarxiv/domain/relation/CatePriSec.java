package com.myarxiv.myarxiv.domain.relation;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName cate_pri_sec
 */
@TableName(value ="cate_pri_sec")
@Data
public class CatePriSec implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer catePriSecId;

    /**
     * 
     */
    private Integer categoryPrimaryId;

    /**
     * 
     */
    private Integer categorySecondaryId;

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
        CatePriSec other = (CatePriSec) that;
        return (this.getCatePriSecId() == null ? other.getCatePriSecId() == null : this.getCatePriSecId().equals(other.getCatePriSecId()))
            && (this.getCategoryPrimaryId() == null ? other.getCategoryPrimaryId() == null : this.getCategoryPrimaryId().equals(other.getCategoryPrimaryId()))
            && (this.getCategorySecondaryId() == null ? other.getCategorySecondaryId() == null : this.getCategorySecondaryId().equals(other.getCategorySecondaryId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCatePriSecId() == null) ? 0 : getCatePriSecId().hashCode());
        result = prime * result + ((getCategoryPrimaryId() == null) ? 0 : getCategoryPrimaryId().hashCode());
        result = prime * result + ((getCategorySecondaryId() == null) ? 0 : getCategorySecondaryId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", catePriSecId=").append(catePriSecId);
        sb.append(", categoryPrimaryId=").append(categoryPrimaryId);
        sb.append(", categorySecondaryId=").append(categorySecondaryId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}