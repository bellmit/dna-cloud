package com.bazl.dna.database.service.model.vo;

import java.io.Serializable;

import com.bazl.dna.database.service.model.po.CasePersonInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by lizhihua on 2020-04-10.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CasePersonInfoVo extends CasePersonInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String personTypeName;//人员类型
    private String personGenderName;//性别
    private String relationTypeName;//亲缘类型
    private String personRaceName;//名族

}
