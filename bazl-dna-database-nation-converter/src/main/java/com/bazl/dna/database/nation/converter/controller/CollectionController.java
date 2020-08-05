package com.bazl.dna.database.nation.converter.controller;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.nation.converter.model.po.SampleInfo;
import com.bazl.dna.database.nation.converter.model.vo.SampleDnaGeneVo;
import com.bazl.dna.database.nation.converter.service.SampleDnaGeneService;
import com.bazl.dna.database.nation.converter.service.SampleInfoService;
import com.bazl.dna.database.nation.converter.utils.ErrorCodes;
import com.bazl.dna.database.nation.converter.utils.ErrorInfo;
import com.bazl.dna.database.nation.converter.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/collection")
public class CollectionController extends BaseController {

    @Autowired
    private SampleDnaGeneService sampleDnaGeneService;
    
    @Autowired
    private SampleInfoService sampleInfoService;

    /**
     * 样本编号模糊查询
     * @param sampleNo
     * @return
     */
    @RequestMapping(value = "/selectSampleNo")
	public ResponseData selectSampleNo(@RequestParam("sampleNo") String sampleNo) {
        List<SampleDnaGeneVo> list = sampleDnaGeneService.selectBySampleNoList(sampleNo);
        if(ListUtils.isNotNullAndEmptyList(list)) {
        		return new ResponseData(list);
        }
        return new ResponseData(ErrorCodes.ERR_DATA_EMPTY, ErrorInfo.ERR_DATA_EMPTY);
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
