package com.bazl.dna.database.service.model.vo;

import com.bazl.dna.database.service.model.po.DnaSampleInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by lizhihua on 2020-04-10.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DnaSampleInfoVo extends DnaSampleInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String instoreDataTypeName;
    private String sampleTypeName;//检材类型名称
    private String personName;//人员名称

	private String geneInfo;//基因信息
	private String geneImage;//基因图片路径
    private int panelId;//试剂盒id
	private String panelName;//试剂盒名称
	private int locusCount;//检出位点个数

	private int dataSource; //数据类型
}
