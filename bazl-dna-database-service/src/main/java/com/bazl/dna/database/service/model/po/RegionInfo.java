package com.bazl.dna.database.service.model.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RegionInfo implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 行政区划代码
     */
    private String regionCode;

    private String value;

    private String label;

    /**
     * 行政区划名称
     */
    private String regionName;

    /**
     * 行政区划简称
     */
    private String regionShortName;

    /**
     * 级别
     */
    private Integer regionLevel;

    /**
     * 上级行政区划代码
     */
    private String parentRegionCode;

    private static final long serialVersionUID = 1L;

}