package com.bazl.dna.mix.connector.nation.fallback;

import com.bazl.dna.mix.connector.nation.NationDataConnector;
import com.bazl.dna.mix.controller.base.ResultBean;
import com.bazl.dna.mix.controller.base.error.ErrorCode;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 国家库数据连接器实现类
 * Created by lizhihua on 2020-04-20.
 */
@Component
public class NationDataConnectorFallback implements NationDataConnector {

    /**
     * 根据案件编号查询案件详情信息
     *
     * @param caseNo 案件编号
     * @return 案件详情信息
     */
    @Override
    public ResultBean getCaseInfoByCaseNo(String caseNo) {
        //TODO  提示前端错误信息
        return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "根据案件编号查询案件详情信息失败。");
    }

    @Override
    public ResultBean selectKitName() {
        //TODO   提示前端错误信息
        return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "获取试剂盒信息错误!");
    }

    @Override
    public ResultBean selectByKitIdToLocusID(String reagentKitId) {
        //TODO   提示前端错误信息
        return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "通过试剂盒id查到关联的基因座列表错误!");
    }

    /**
     * 通过试剂盒名称查到关联的基因座名称列表
     *
     * @param reagentKitName
     * @return
     */
    @Override
    public ResultBean selectByKitNameToLocusName(String reagentKitName) {
        //TODO  提示前端错误信息
        return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "通过试剂盒名称查到关联的基因座名称列表失败。");
    }

    /**
     * 根据基因座名称查询基因座列表
     * @return
     */
    @Override
    public ResultBean selectByLocusName() {
        //TODO 提示前端错误信息
        return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "根据基因座名称查询基因座列表失败。");
    }

    /**
     * 组织机构信息，省市二级联动
     * @param code
     * @return
     */
    @Override
    public ResultBean selectProvinceAndCity(String code) {
        //TODO 提示前端错误信息
        return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "根据机构代码获取组织机构信息失败。");
    }

    /**
     * 查询人员类别名称
     *
     * @return
     */
    @Override
    public ResultBean selectPersonCategory() {
        //TODO 提示前端错误信息
        return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "查询人员类别名称信息失败。");
    }

    /**
     * 根据条件查询对应的混合基因分型列表
     *
     * @param request
     * @param page
     * @param caseNo
     * @param caseName
     * @param sampleNo
     * @param sampleName
     * @return
     */
    @Override
    public ResultBean queryMixedSampleGenes(HttpServletRequest request, String page, String caseNo, String caseName, String sampleNo, String sampleName) {
        //TODO 提示前端错误信息
        return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "根据条件查询对应的混合基因分型列表失败。");
    }

    /**
     * 国家库删除混合样本 （假删除）
     *
     * @param sampleGeneId
     * @return
     */
    @Override
    public ResultBean updateSampleDnaGeneDF(String sampleGeneId) {
        //TODO 提示前端错误信息
        return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "根据geneId删除混合样本失败。");
    }

    /**
     * 查询混合基因信息(关联案件，地区，样本，字典项) 总数
     *
     * @return
     */
    @Override
    public ResultBean selectMixGeneByCwsd() {
        //TODO 提示前端错误信息
        return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "查询混合基因信息(关联案件，地区，样本，字典项) 总数失败。");
    }

    /**
     * 查询单一基因信息(关联案件，地区，样本，字典项) 总数
     *
     * @return
     */
    @Override
    public ResultBean selectSingleGeneByCwsd() {
        //TODO 提示前端错误信息
        return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "查询单一基因信息(关联案件，地区，样本，字典项) 总数失败。");
    }

    /**
     * 查询混合基因信息(关联案件，地区，样本，字典项) 分页
     *
     * @param page
     * @return
     */
    @Override
    public ResultBean selectMixGeneByCwsdPage(String page) {
        //TODO 提示前端错误信息
        return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "查询混合基因信息(关联案件，地区，样本，字典项) 分页失败。");
    }

}
