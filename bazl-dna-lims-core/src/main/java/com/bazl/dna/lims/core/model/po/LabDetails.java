package com.bazl.dna.lims.core.model.po;

/**
 * Created by Administrator on 2019/4/29.
 */

import java.io.Serializable;

/**
 * 案件详情表
 */
public class LabDetails implements Serializable {
    /**
     * id
     */
    private String id;
    /**
     * 实验室相关信息表id
     */
    private String labId;
    /**
     * 类型：；例：文件受控号
     */
    private String type;
    private String typeValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }
}
