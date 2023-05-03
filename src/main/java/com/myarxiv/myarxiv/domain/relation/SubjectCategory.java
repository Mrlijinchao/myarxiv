package com.myarxiv.myarxiv.domain.relation;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName subject_category
 */
@TableName(value ="subject_category")
@Data
public class SubjectCategory implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer subjectCategoryId;

    /**
     * 
     */
    private Integer subjectId;

    /**
     * 
     */
    private Integer categoryPrimaryId;

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
        SubjectCategory other = (SubjectCategory) that;
        return (this.getSubjectCategoryId() == null ? other.getSubjectCategoryId() == null : this.getSubjectCategoryId().equals(other.getSubjectCategoryId()))
            && (this.getSubjectId() == null ? other.getSubjectId() == null : this.getSubjectId().equals(other.getSubjectId()))
            && (this.getCategoryPrimaryId() == null ? other.getCategoryPrimaryId() == null : this.getCategoryPrimaryId().equals(other.getCategoryPrimaryId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSubjectCategoryId() == null) ? 0 : getSubjectCategoryId().hashCode());
        result = prime * result + ((getSubjectId() == null) ? 0 : getSubjectId().hashCode());
        result = prime * result + ((getCategoryPrimaryId() == null) ? 0 : getCategoryPrimaryId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", subjectCategoryId=").append(subjectCategoryId);
        sb.append(", subjectId=").append(subjectId);
        sb.append(", categoryPrimaryId=").append(categoryPrimaryId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}