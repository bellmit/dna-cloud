package com.bazl.dna.database.service.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 实验室服务器信息
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LabServerInfo extends Model<LabServerInfo> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 实验室服务器编号
     */
    @TableField("LAB_SERVER_NO")
    private String labServerNo;

    /**
     * 实验室名称
     */
    @TableField("LAB_SERVER_NAME")
    private String labServerName;

    /**
     * 实验室应用服务器IP地址
     */
    @TableField("LAB_SERVER_IPADDR")
    private String labServerIpaddr;

    /**
     * 实验室应用服务器端口
     */
    @TableField("LAB_SERVER_PORT")
    private String labServerPort;

    /**
     * 实验室机构编号
     */
    @TableField("ORG_ID")
    private String orgId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
