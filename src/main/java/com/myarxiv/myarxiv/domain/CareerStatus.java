package com.myarxiv.myarxiv.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName career_status
 */
@TableName(value ="career_status")
@Data
public class CareerStatus implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer careerStatusId;

    /**
     * 
     */
    private String careerStatusName;

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
        CareerStatus other = (CareerStatus) that;
        return (this.getCareerStatusId() == null ? other.getCareerStatusId() == null : this.getCareerStatusId().equals(other.getCareerStatusId()))
            && (this.getCareerStatusName() == null ? other.getCareerStatusName() == null : this.getCareerStatusName().equals(other.getCareerStatusName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCareerStatusId() == null) ? 0 : getCareerStatusId().hashCode());
        result = prime * result + ((getCareerStatusName() == null) ? 0 : getCareerStatusName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", careerStatusId=").append(careerStatusId);
        sb.append(", careerStatusName=").append(careerStatusName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}