package com.bazl.dna.database.service.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 基因同步队列
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GeneSyncQueue extends Model<GeneSyncQueue> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;


    /**
     * 实验室服务器编号
     */
    @TableField("LAB_SERVER_NO")
    private String labServeNo;

    /**
     * 同步类型，字典：GENE_SYNC_TYPE
     */
    @TableField("SYNC_TYPE")
    private String syncType;

    /**
     * 基因ID
     */
    @TableField("GENE_ID")
    private Integer geneId;

    /**
     * 同步状态，字典：GENE_SYNC_STATUS
     */
    @TableField("SYNC_STATUS")
    private String syncStatus;

    /**
     * 创建时间
     */
    @TableField("CREATE_DATETIME")
    private transient LocalDateTime createDatetime;

    /**
     * 更新时间
     */
    @TableField("UPDATE_DATETIME")
    private transient LocalDateTime updateDatetime;
    private String instoreDataType;
    private String geneInfo;
    private String geneType;
    private int refPersonId;

    public int getRefPersonId() {
        return refPersonId;
    }

    public void setRefPersonId(int refPersonId) {
        this.refPersonId = refPersonId;
    }

    public String getGeneType() {
        return geneType;
    }

    public void setGeneType(String geneType) {
        this.geneType = geneType;
    }

    public String getInstoreDataType() {
        return instoreDataType;
    }

    public void setInstoreDataType(String instoreDataType) {
        this.instoreDataType = instoreDataType;
    }

    public String getGeneInfo() {
        return geneInfo;
    }

    public void setGeneInfo(String geneInfo) {
        this.geneInfo = geneInfo;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabServeNo() {
        return labServeNo;
    }

    public void setLabServeNo(String labServeNo) {
        this.labServeNo = labServeNo;
    }

    public String getSyncType() {
        return syncType;
    }

    public void setSyncType(String syncType) {
        this.syncType = syncType;
    }

    public Integer getGeneId() {
        return geneId;
    }

    public void setGeneId(Integer geneId) {
        this.geneId = geneId;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public LocalDateTime getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(LocalDateTime createDatetime) {
        this.createDatetime = createDatetime;
    }

    public LocalDateTime getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(LocalDateTime updateDatetime) {
        this.updateDatetime = updateDatetime;
    }
}
