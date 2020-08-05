package com.bazl.dna.database.service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.InstoreDataConditionMapper;
import com.bazl.dna.database.service.model.po.InstoreDataCondition;
import com.bazl.dna.database.service.service.InstoreDataConditionService;

/**
 * Created by Liuchang on 2020/5/13.
 */
@Service
public class InstoreDataConditionServiceImpl  extends ServiceImpl<InstoreDataConditionMapper,InstoreDataCondition>  implements InstoreDataConditionService {
	
    /**
     * 查询所有的入库数据最少基因位点数
     * @return
     */
    @Override
    public List<InstoreDataCondition> LeastGeneCountForInstoreDataType() {
        try{
            List<InstoreDataCondition> list = this.getBaseMapper().LeastGeneCountForInstoreDataType();
            return list;
        }catch(Exception ex){
            log.error("InstoreDataConditionService.LeastGeneCountForInstoreDataType error.", ex);
            return null;
        }
    }
}
