package com.bazl.dna.database.core.controller.export;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.service.model.po.CaseInfo;
import com.bazl.dna.database.service.model.po.CasePersonInfo;
import com.bazl.dna.database.service.model.po.DnaSampleInfo;
import com.bazl.dna.database.service.model.po.MatchResultYstr;
import com.bazl.dna.database.service.model.vo.MatchResultVo;
import com.bazl.dna.database.service.service.CaseInfoService;
import com.bazl.dna.database.service.service.CasePersonInfoService;
import com.bazl.dna.database.service.service.DnaSampleInfoService;
import com.bazl.dna.database.service.service.MatchResultYstrService;
import com.bazl.dna.util.DataUtils;
import com.bazl.dna.util.excel.ExportExcelUtils;
import com.google.common.collect.Lists;

@RestController
@RequestMapping("/export/ystr")
public class ExportMatchResultYstrController extends BaseController {
	
	@Value("${filePath}")
	private String filePath;
	
	@Autowired
    private MatchResultYstrService matchResultService;
	
	@Autowired
	private DnaSampleInfoService sampleInfoService;
	
	@Autowired
	private CaseInfoService caseInfoService;
	
	@Autowired
	private CasePersonInfoService casePersonInfoService;

	@GetMapping(value = "/result")
	public ResponseEntity<byte[]> matchResultSame(String ids) {
		try {
			if (StringUtils.trimToNull(ids) == null) {
				return null;
			}
			List<MatchResultVo> objectList = Lists.newArrayList();
			String[] idsArray = StringUtils.split(ids, ",");
			
			for (String id : idsArray) {
				MatchResultYstr entity = matchResultService.getById(Integer.parseInt(id));
				
				DnaSampleInfo sample = sampleInfoService.getById(entity.getSampleaId());
				DnaSampleInfo matchSample = sampleInfoService.getById(entity.getSamplebId());
				if (sample != null) {
					CaseInfo caseInfo = caseInfoService.getById(sample.getCaseId());
					CasePersonInfo personInfo = casePersonInfoService.getById(sample.getRefPersonId());
					
					MatchResultVo vo = new MatchResultVo();
					if (personInfo != null) {
						vo.setIdCard(personInfo.getPersonIdcardNo());
						vo.setPersonName(personInfo.getPersonName());
					} else {
						vo.setIdCard("");
						vo.setPersonName("");
					}
					vo.setConsignOrgName("");
					vo.setThirdNumber("");
					if (caseInfo != null) {
						vo.setCaseNo(caseInfo.getCaseAcceptNo());
						vo.setCaseDesc(caseInfo.getCaseSummary());
					} else {
						vo.setCaseNo("");
						vo.setCaseDesc("");
					}
					vo.setOrgName("");
					vo.setMatchSampleName("(" + matchSample.getSampleName() + ")" + matchSample.getSampleDesc());
					objectList.add(vo);
				}
			}
			String[] columnName = new String[] {"身份证号","人员名称","人员送检单位","三版本编号","案件编号","简要案情","分县局","比中检材情况","工作情况","破案"};
			String[] titleKey = new String[] {"idCard","personName","consignOrgName","thirdNumber","caseNo","caseDesc","orgName"};
			
			String fileName = "YSTR_" + new Date().getTime() +".xls";
			ExportExcelUtils.exportExcel(fileName, columnName, titleKey, objectList, filePath);
			return DataUtils.download(new File(filePath + File.separator + fileName), fileName);
		} catch (Exception e) {
			logger.error("matchResultSame error:", e);
		}
		return null;
	}
}
