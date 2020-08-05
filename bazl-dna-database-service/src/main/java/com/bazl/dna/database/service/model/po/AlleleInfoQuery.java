package com.bazl.dna.database.service.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AlleleInfoQuery extends Model<AlleleInfoQuery> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String locusName;
    private String otherName;
    private String coreLocusFlag;
    private String valueScope;
    private String locusOrd;

}
