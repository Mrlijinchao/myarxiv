package com.myarxiv.myarxiv.domain.relation;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName paper_file
 */
@TableName(value ="paper_file")
@Data
public class PaperFile implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer paperFileId;

    /**
     * 
     */
    private Integer fileId;

    /**
     * 
     */
    private Integer paperId;

    /**
     * 
     */
    private Integer submissionPaperId;

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
        PaperFile other = (PaperFile) that;
        return (this.getPaperFileId() == null ? other.getPaperFileId() == null : this.getPaperFileId().equals(other.getPaperFileId()))
            && (this.getFileId() == null ? other.getFileId() == null : this.getFileId().equals(other.getFileId()))
            && (this.getPaperId() == null ? other.getPaperId() == null : this.getPaperId().equals(other.getPaperId()))
            && (this.getSubmissionPaperId() == null ? other.getSubmissionPaperId() == null : this.getSubmissionPaperId().equals(other.getSubmissionPaperId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPaperFileId() == null) ? 0 : getPaperFileId().hashCode());
        result = prime * result + ((getFileId() == null) ? 0 : getFileId().hashCode());
        result = prime * result + ((getPaperId() == null) ? 0 : getPaperId().hashCode());
        result = prime * result + ((getSubmissionPaperId() == null) ? 0 : getSubmissionPaperId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", paperFileId=").append(paperFileId);
        sb.append(", fileId=").append(fileId);
        sb.append(", paperId=").append(paperId);
        sb.append(", submissionPaperId=").append(submissionPaperId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}