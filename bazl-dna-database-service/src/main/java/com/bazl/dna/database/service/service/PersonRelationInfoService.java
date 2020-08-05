package com.bazl.dna.database.service.service;

import com.bazl.dna.database.service.model.po.PersonRelativeInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 人员关系表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
public interface PersonRelationInfoService extends IService<PersonRelativeInfo> {

    List<PersonRelativeInfo> queryPersonRelationList(PersonRelativeInfo personRelativeInfo);
}
