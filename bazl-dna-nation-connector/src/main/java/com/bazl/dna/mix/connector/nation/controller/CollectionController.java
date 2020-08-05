package com.bazl.dna.mix.connector.nation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.mix.connector.nation.constants.ErrorCodes;
import com.bazl.dna.mix.connector.nation.constants.ErrorInfo;
import com.bazl.dna.mix.connector.nation.dao.ReagentKitMapper;
import com.bazl.dna.mix.connector.nation.model.po.ReagentKit;
import com.bazl.dna.mix.connector.nation.model.po.SampleDnaGene;
import com.bazl.dna.mix.connector.nation.model.po.SampleInfo;
import com.bazl.dna.mix.connector.nation.model.po.SysDict;
import com.bazl.dna.mix.connector.nation.model.vo.SampleDnaGeneVo;
import com.bazl.dna.mix.connector.nation.service.SampleDnaGeneService;
import com.bazl.dna.mix.connector.nation.service.SampleInfoService;
import com.bazl.dna.mix.connector.nation.service.SysDictService;

@RestController
@RequestMapping("/collection")
public class CollectionController extends BaseController {

    @Autowired
    private SampleDnaGeneService sampleDnaGeneService;
    
    @Autowired
    private SampleInfoService sampleInfoService;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private ReagentKitMapper reagentKitMapper;

    /**
     * 样本编号模糊查询
     * @param sampleNo
     * @return
     */
    @RequestMapping(value = "/selectSampleNo")
	public ResponseData selectSampleNo(@RequestParam("sampleNo") String sampleNo) {
        List<SampleDnaGeneVo> list = sampleDnaGeneService.selectBySampleNoList(sampleNo);
	    for (SampleDnaGeneVo dnaGeneVo : list){
	        SampleDnaGene sampleDnaGene = sampleDnaGeneService.selectByPrimaryKey(dnaGeneVo.getEntity().getId());
	        if (sampleDnaGene != null){
	            dnaGeneVo.getEntity().setGeneInfo(sampleDnaGene.getGeneInfo());
	        }
	        SysDict bean = new SysDict();
	        bean.setDictCategory("SAMPLE_TYPE");
	        bean.setDictKey(dnaGeneVo.getSampleType());
	        SysDict sysDict = sysDictService.selectByPrimaryKeyBean(bean);
	        if (sysDict != null){
	            dnaGeneVo.setSampleTypeName(sysDict.getDictValue1());
	        }
	        ReagentKit reagentKit = reagentKitMapper.selectByPrimaryKey(dnaGeneVo.getEntity().getReagentKit());
	        if (reagentKit != null){
	            dnaGeneVo.setKitName(reagentKit.getName());
	            dnaGeneVo.setKitId(reagentKit.getId());
	        }
	    }
	    return new ResponseData(list);
    }
    
    /**
     * 样本编号查询
     * @param
     * @return
     */
    @RequestMapping(value = "/selectSampleId")
	public ResponseData selectSampleId(@RequestParam("sampleId") String sampleId) {
    		SampleInfo sampleInfo = sampleInfoService.selectByPrimaryKey(sampleId);
    		if (sampleInfo != null) {
    			return new ResponseData(sampleInfo);
    		}
    		return new ResponseData(ErrorCodes.ERR_DATA_EMPTY, ErrorInfo.ERR_DATA_EMPTY);
    }
}
