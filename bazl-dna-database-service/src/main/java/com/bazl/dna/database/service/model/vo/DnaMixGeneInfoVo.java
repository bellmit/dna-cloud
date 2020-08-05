package com.bazl.dna.database.service.model.vo;

import java.io.Serializable;

import com.bazl.dna.database.service.model.po.DnaMixGeneInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DnaMixGeneInfoVo extends DnaMixGeneInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String panelName;//试剂盒名称
    private String ystrFlag;;//区分STR和YSTR 1:STR 2:YSTR 3:混合STR
}
