package com.bazl.dna.caseinfo.accept.controller.center;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bazl.dna.lims.model.po.LaboratoryInfo;
import com.bazl.dna.lims.model.vo.AmPersonalInfoVo;
import com.bazl.dna.lims.service.AmPersonalInfoService;
import com.bazl.dna.lims.service.LaboratoryInfoService;

@Component
public class ScheduledData {
    @Autowired
    AmPersonalInfoService amPersonalInfoService;
    @Autowired
    LaboratoryInfoService laboratoryInfoService;

    //每隔5秒执行一次 0/5 * *  * * ?
    //每天中午12点    0 0 12 * * ?
    @Scheduled(cron = "0 0 12  * * ? ")
    public void timingObtainData(){
        String orgArr[]={"110108000000","110230000004","110114000000","110115000000","110106000000","110107000000","110105000000","110102000000","110101000000"};
        for(int i=0;i<orgArr.length;i++){
            List<AmPersonalInfoVo> amPersonalInfoVoList = amPersonalInfoService.queryAmPersonalInfoVoList(orgArr[i]);
            //更新实验室信息表 --实验人员数量字段值
            LaboratoryInfo laboratoryInfo=new LaboratoryInfo();
            laboratoryInfo.setLabPersonnel(new BigDecimal(amPersonalInfoVoList.size()));
            laboratoryInfo.setOrgId(orgArr[i]);
            laboratoryInfoService.updateOne(laboratoryInfo);
        }
    }
}
