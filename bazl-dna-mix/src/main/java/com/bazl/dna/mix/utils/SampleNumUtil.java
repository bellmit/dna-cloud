package com.bazl.dna.mix.utils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.mix.constants.Constants;
import com.bazl.dna.mix.model.po.DictItem;
import com.bazl.dna.mix.service.DictItemService;
import com.bazl.dna.util.DateTools;

@RestController
public class SampleNumUtil {

    @Autowired
    private DictItemService dictItemService;

    public String getNextSampleNoVal(String createPerson,String dictTypeCode) {
        String year = String.valueOf(DateTools.getToYear());

        List<DictItem> dictItemList = dictItemService.selectListByDictType(dictTypeCode,createPerson);
        int val = 0;
        try {
            if (dictItemList.size() != 0 ) {
                DictItem dictItem = new DictItem();
                val = Integer.parseInt(dictItemList.get(0).getDictCode());
                val = val + 1;
                dictItem.setDictCode(val+"");
                dictItem.setDictItemId(dictItemList.get(0).getDictItemId());
                dictItemService.updateSampleNoVal(dictItem);
            } else {
                DictItem dictItem = new DictItem();
                val = val + 1;
                dictItem.setDictCode(val+"");
                dictItem.setDictItemId(UUID.randomUUID().toString());
                dictItem.setDictInfoId(UUID.randomUUID().toString());
                dictItem.setDictTypeCode(Constants.SAMPLE_NO);
                dictItem.setDictCode(val+"");
                dictItem.setDictName("样品编号");
                dictItem.setCreateDatetime(new Date());
                dictItem.setCreatePerson(createPerson);
                dictItemService.insertSampleNoVal(dictItem);
            }
        } catch (Exception ex) {
            throw ex;
        }
        //返回任务编号
        return "SG" + "-" + year + "-" +val;
    }
}
