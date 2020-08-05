package com.bazl.dna.database.service.model.qo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.bazl.dna.database.service.model.po.DnaLocusInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 试剂盒信息表  Query
 * Created by Liuchang on 2020/5/27.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DnaPanelInfoQuery extends AbstractQuery implements Serializable {


    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 1-STR, 2-YSTR
     */
    @TableField("PANEL_TYPE")
    private Integer panelType;

    /**
     * 试剂盒代码
     */
    @TableField("PANEL_CODE")
    private String panelCode;

    /**
     * 试剂盒名称
     */
    @TableField("PANEL_NAME")
    private String panelName;

    /**
     * 试剂盒全名
     */
    @TableField("PANEL_FULL_NAME")
    private String panelFullName;

    /**
     * 别名
     */
    @TableField("ALIAS_NAME")
    private String aliasName;

    /**
     * 生产商
     */
    @TableField("PRODUCER_NAME")
    private String producerName;

    /**
     * 版本
     */
    @TableField("PANEL_VERSION_NO")
    private String panelVersionNo;

    /**
     * 存储试剂盒对应panel文件路径
     */
    @TableField("PANEL_FILE_PATH")
    private String panelFilePath;

    /**
     * 存储试剂盒对应bins文件路径
     */
    @TableField("BINS_FILE_PATH")
    private String binsFilePath;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("CREATE_DATETIME")
    private transient LocalDateTime createDatetime;

    /**
     * 创建人姓名
     */
    @TableField("CREATE_PERSON")
    private String createPerson;

    /**
     * 创建人单位名称
     */
    @TableField("CREATE_PERSON_ORG_NAME")
    private String createPersonOrgName;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("UPDATE_DATETIME")
    private transient LocalDateTime updateDatetime;

    /**
     * 更新人姓名
     */
    @TableField("UPDATE_PERSON")
    private String updatePerson;

    /**
     * 更新人单位名称
     */
    @TableField("UPDATE_PERSON_ORG_NAME")
    private String updatePersonOrgName;


    @TableField(exist = false)
    private List<DnaLocusInfo> dnaLocusInfoList;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
