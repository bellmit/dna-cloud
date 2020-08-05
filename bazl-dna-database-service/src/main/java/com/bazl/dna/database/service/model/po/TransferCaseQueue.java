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
 * 案件数据上报队列
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TransferCaseQueue extends Model<TransferCaseQueue> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 实验室服务器编号
     */
    @TableField("LAB_SERVER_NO")
    private String labServerNo;

    /**
     * 案件id
     */
    @TableField("CASE_ID")
    private Integer caseId;

    /**
     * 上报状态，字典：TRANSFER_STATUS
     */
    @TableField("TRANSFER_STATUS")
    private String transferStatus;

    /**
     * 上报文件根目录
     */
    @TableField("TRANSFER_FILE_ROOT_PATH")
    private String transferFileRootPath;

    /**
     * 上报文件名称
     */
    @TableField("TRANSFER_FILE_NAME")
    private String transferFileName;

    /**
     * 上报失败信息
     */
    @TableField("TRANSFER_ERROR_MSG")
    private String transferErrorMsg;

    @TableField("CREATE_DATETIME")
    private transient LocalDateTime createDatetime;

    @TableField("UPDATE_DATETIME")
    private transient LocalDateTime updateDatetime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
