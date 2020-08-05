package com.bazl.dna.database.service.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PanelInfoQuery extends Model<PanelInfoQuery> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String panelName;
    private String locusOrd;
    private String coreLocusFlag;

}
