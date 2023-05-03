package com.myarxiv.myarxiv.domain.relation;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 *
 * @TableName paper_category
 */
@TableName(value ="paper_category")
@Data
@Component
public class PaperCategory implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer paperCategoryId;

    /**
     *
     */
    private Integer paperId;

    /**
     *
     */
    private Integer submissionPaperId;

    /**
     *
     */
    private Integer categoryPrimaryId;

    /**
     *
     */
    private Integer categorySecondaryId;

    /**
     *
     */
    private Integer paperCategoryStatus;

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
        PaperCategory other = (PaperCategory) that;
        return (this.getPaperCategoryId() == null ? other.getPaperCategoryId() == null : this.getPaperCategoryId().equals(other.getPaperCategoryId()))
            && (this.getPaperId() == null ? other.getPaperId() == null : this.getPaperId().equals(other.getPaperId()))
            && (this.getSubmissionPaperId() == null ? other.getSubmissionPaperId() == null : this.getSubmissionPaperId().equals(other.getSubmissionPaperId()))
            && (this.getCategoryPrimaryId() == null ? other.getCategoryPrimaryId() == null : this.getCategoryPrimaryId().equals(other.getCategoryPrimaryId()))
            && (this.getCategorySecondaryId() == null ? other.getCategorySecondaryId() == null : this.getCategorySecondaryId().equals(other.getCategorySecondaryId()))
            && (this.getPaperCategoryStatus() == null ? other.getPaperCategoryStatus() == null : this.getPaperCategoryStatus().equals(other.getPaperCategoryStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPaperCategoryId() == null) ? 0 : getPaperCategoryId().hashCode());
        result = prime * result + ((getPaperId() == null) ? 0 : getPaperId().hashCode());
        result = prime * result + ((getSubmissionPaperId() == null) ? 0 : getSubmissionPaperId().hashCode());
        result = prime * result + ((getCategoryPrimaryId() == null) ? 0 : getCategoryPrimaryId().hashCode());
        result = prime * result + ((getCategorySecondaryId() == null) ? 0 : getCategorySecondaryId().hashCode());
        result = prime * result + ((getPaperCategoryStatus() == null) ? 0 : getPaperCategoryStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", paperCategoryId=").append(paperCategoryId);
        sb.append(", paperId=").append(paperId);
        sb.append(", submissionPaperId=").append(submissionPaperId);
        sb.append(", categoryPrimaryId=").append(categoryPrimaryId);
        sb.append(", categorySecondaryId=").append(categorySecondaryId);
        sb.append(", paperCategoryStatus=").append(paperCategoryStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
