package com.bazl.dna.mix.connector.nation;

import com.bazl.dna.mix.controller.base.ResultBean;
import com.bazl.dna.mix.connector.nation.fallback.NationDataConnectorFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 国家库数据连接器
 *
 * @author lizhihua on 2020-04-20.
 */
@FeignClient(value = "bazl-dna-nation-connector", fallback = NationDataConnectorFallback.class)
public interface NationDataConnector {

    /**
     * 根据案件编号查询案件详情信息
     * @param caseNo  案件编号
     * @return 案件详情信息
     */
    @RequestMapping(value = "/nationalTreasury/getCaseInfoByCaseNo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResultBean getCaseInfoByCaseNo(@RequestBody String caseNo);

    /**
     * 查询试剂盒列表
     * @return 试剂盒列表
     *
     */
    @RequestMapping(value = "/nationalTreasury/selectKitName",method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public ResultBean selectKitName();

    /**
     *
     * 试剂盒基因座关联表   通过试剂盒id查到关联的基因座列表，基因id查询基因座信息
     * @param reagentKitId 试剂盒id
     * @return 基因座列表
     */
    @RequestMapping(value = "/nationalTreasury/selectByKitIdToLocusID" ,method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public ResultBean selectByKitIdToLocusID(@RequestBody String reagentKitId);

    /**
     * 通过试剂盒名称查到关联的基因座名称列表
     * @param reagentKitName
     * @return
     */
    @RequestMapping(value = "/nationalTreasury/selectByKitNameToLocusName" ,method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public ResultBean selectByKitNameToLocusName(String reagentKitName);

    /**
     * 根据基因座名称查询基因座列表
     * @return
     */
    @RequestMapping(value = "/nationalTreasury/selectByLocusName" ,method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public ResultBean selectByLocusName();

    /**
     * 组织机构信息，省市二级联动
     * @param code
     * @return
     */
    @RequestMapping(value = "/nationalTreasury/selectProvinceAndCity" ,method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public ResultBean selectProvinceAndCity(String code);

    /**
     * 查询人员类别名称
     * @return
     */
    @RequestMapping(value = "/nationalTreasury/selectPersonCategory" ,method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public ResultBean selectPersonCategory();

    //国家库条查接口

    /**
     * 根据条件查询对应的混合基因分型列表
     * @param request
     * @param page
     * @param caseNo
     * @param caseName
     * @param sampleNo
     * @param sampleName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/nationalTreasury/queryMixedSampleGenes", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public ResultBean queryMixedSampleGenes(HttpServletRequest request, @RequestParam("page") String page, @RequestParam("caseNo")String caseNo, @RequestParam("caseName")String caseName,@RequestParam("sampleNo")String sampleNo, @RequestParam("sampleName")String sampleName);


    /**
     * 国家库删除混合样本 （假删除）
     * @param sampleGeneId
     * @return
     */
    @RequestMapping(value = "/nationalTreasury/updateSampleDnaGeneDF", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResultBean updateSampleDnaGeneDF( String sampleGeneId);

    /**
     * 查询混合基因信息(关联案件，地区，样本，字典项) 总数
     * @return
     */
    @RequestMapping(value = "/nationalTreasury/selectMixGeneByCwsd", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public ResultBean selectMixGeneByCwsd();

    /**
     * 查询单一基因信息(关联案件，地区，样本，字典项) 总数
     * @return
     */
    @RequestMapping(value = "/nationalTreasury/selectSingleGeneByCwsd", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public ResultBean selectSingleGeneByCwsd();

    /**
     * 查询混合基因信息(关联案件，地区，样本，字典项) 分页
     * @param page
     * @return
     */
    @RequestMapping(value = "/nationalTreasury/selectMixGeneByCwsdPage", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public ResultBean selectMixGeneByCwsdPage(String page);


}
