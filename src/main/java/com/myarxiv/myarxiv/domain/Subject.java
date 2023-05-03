package com.myarxiv.myarxiv.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName subject
 */
@TableName(value ="subject")
@Data
public class Subject implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer subjectId;

    /**
     * 
     */
    private String subjectName;

    /**
     * 
     */
    private String subjectDescription;

    /**
     * 
     */
    private Date subjectCreateTime;

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
        Subject other = (Subject) that;
        return (this.getSubjectId() == null ? other.getSubjectId() == null : this.getSubjectId().equals(other.getSubjectId()))
            && (this.getSubjectName() == null ? other.getSubjectName() == null : this.getSubjectName().equals(other.getSubjectName()))
            && (this.getSubjectDescription() == null ? other.getSubjectDescription() == null : this.getSubjectDescription().equals(other.getSubjectDescription()))
            && (this.getSubjectCreateTime() == null ? other.getSubjectCreateTime() == null : this.getSubjectCreateTime().equals(other.getSubjectCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSubjectId() == null) ? 0 : getSubjectId().hashCode());
        result = prime * result + ((getSubjectName() == null) ? 0 : getSubjectName().hashCode());
        result = prime * result + ((getSubjectDescription() == null) ? 0 : getSubjectDescription().hashCode());
        result = prime * result + ((getSubjectCreateTime() == null) ? 0 : getSubjectCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", subjectId=").append(subjectId);
        sb.append(", subjectName=").append(subjectName);
        sb.append(", subjectDescription=").append(subjectDescription);
        sb.append(", subjectCreateTime=").append(subjectCreateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}