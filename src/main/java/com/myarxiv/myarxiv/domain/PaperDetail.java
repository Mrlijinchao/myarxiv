package com.myarxiv.myarxiv.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName paper_detail
 */
@TableName(value ="paper_detail")
@Data
public class PaperDetail implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer paperDetailId;

    /**
     * 
     */
    private String paperAcmClass;

    /**
     * 
     */
    private String paperMscClass;

    /**
     * 
     */
    private String paperReportNumber;

    /**
     * 
     */
    private String paperJournalReference;

    /**
     * 
     */
    private String paperDoi;

    /**
     * 
     */
    private Integer paperDetailStatus;

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
        PaperDetail other = (PaperDetail) that;
        return (this.getPaperDetailId() == null ? other.getPaperDetailId() == null : this.getPaperDetailId().equals(other.getPaperDetailId()))
            && (this.getPaperAcmClass() == null ? other.getPaperAcmClass() == null : this.getPaperAcmClass().equals(other.getPaperAcmClass()))
            && (this.getPaperMscClass() == null ? other.getPaperMscClass() == null : this.getPaperMscClass().equals(other.getPaperMscClass()))
            && (this.getPaperReportNumber() == null ? other.getPaperReportNumber() == null : this.getPaperReportNumber().equals(other.getPaperReportNumber()))
            && (this.getPaperJournalReference() == null ? other.getPaperJournalReference() == null : this.getPaperJournalReference().equals(other.getPaperJournalReference()))
            && (this.getPaperDoi() == null ? other.getPaperDoi() == null : this.getPaperDoi().equals(other.getPaperDoi()))
            && (this.getPaperDetailStatus() == null ? other.getPaperDetailStatus() == null : this.getPaperDetailStatus().equals(other.getPaperDetailStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPaperDetailId() == null) ? 0 : getPaperDetailId().hashCode());
        result = prime * result + ((getPaperAcmClass() == null) ? 0 : getPaperAcmClass().hashCode());
        result = prime * result + ((getPaperMscClass() == null) ? 0 : getPaperMscClass().hashCode());
        result = prime * result + ((getPaperReportNumber() == null) ? 0 : getPaperReportNumber().hashCode());
        result = prime * result + ((getPaperJournalReference() == null) ? 0 : getPaperJournalReference().hashCode());
        result = prime * result + ((getPaperDoi() == null) ? 0 : getPaperDoi().hashCode());
        result = prime * result + ((getPaperDetailStatus() == null) ? 0 : getPaperDetailStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", paperDetailId=").append(paperDetailId);
        sb.append(", paperAcmClass=").append(paperAcmClass);
        sb.append(", paperMscClass=").append(paperMscClass);
        sb.append(", paperReportNumber=").append(paperReportNumber);
        sb.append(", paperJournalReference=").append(paperJournalReference);
        sb.append(", paperDoi=").append(paperDoi);
        sb.append(", paperDetailStatus=").append(paperDetailStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}