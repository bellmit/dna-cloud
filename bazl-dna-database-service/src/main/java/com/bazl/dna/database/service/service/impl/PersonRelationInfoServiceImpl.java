package com.bazl.dna.database.service.service.impl;

import com.bazl.dna.database.service.model.po.PersonRelativeInfo;
import com.bazl.dna.database.service.mapper.PersonRelativeInfoMapper;
import com.bazl.dna.database.service.service.PersonRelationInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 人员关系表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Service
public class PersonRelationInfoServiceImpl extends ServiceImpl<PersonRelativeInfoMapper, PersonRelativeInfo> implements PersonRelationInfoService {
	
	private static final String CACHE_NAME = "PersonRelationInfo";

    @Autowired
    private PersonRelativeInfoMapper personRelativeInfoMapper;

    @Override
    @Cacheable(value = CACHE_NAME + ":queryPersonRelationList", keyGenerator="keyGenerator")
    public List<PersonRelativeInfo> queryPersonRelationList(PersonRelativeInfo personRelativeInfo) {
        return personRelativeInfoMapper.queryPersonRelationList(personRelativeInfo);
    }
}
