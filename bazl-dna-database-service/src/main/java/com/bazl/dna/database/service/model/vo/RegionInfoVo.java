package com.bazl.dna.database.service.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RegionInfoVo implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 行政区划代码
     */
    private String value;

    /**
     * 行政区划名称
     */
    private String label;

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

    private List<RegionInfoVo> children; //集合

    //private List<RegionInfo> lastChild; //子集合

}