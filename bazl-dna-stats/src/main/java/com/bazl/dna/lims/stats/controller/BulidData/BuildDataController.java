package com.bazl.dna.lims.stats.controller.BulidData;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.datasource.ContextHolder;
import com.bazl.dna.datasource.DataSourceUtil;
import com.bazl.dna.lims.model.bo.BuildDataModel;
import com.bazl.dna.lims.stats.controller.BaseController;
import com.bazl.dna.lims.utils.MathUtils;

/**
 * @author liuChang
 * @since 2020-07-30
 * @Desc 建库数据查询接口
 */
@RestController
@RequestMapping("/stats/bulidData")
public class BuildDataController extends BaseController {


    /**
     * 类说明：查询全部建库数据--liuchang
     * Date:2020-07-30
     *
     * @return
     */
    @GetMapping(value = "/allBuildData")
    //todo 配置建库宝数据源查询建库宝
    public ResponseData buildData() {
        BuildDataModel buildDataModel = new BuildDataModel(); //建库数据总计Model
        try {
            /*
             * 注释：2020-07-31
             * 配置mysql 建库宝数据源查询数据并插入本地model
             */
            javax.sql.DataSource jkbDataSource = (DataSource) ContextHolder.dataSourceMap.get("jkb");
            Connection connection = jkbDataSource.getConnection();
            /*
             * allCollectPerson:计算采集人员总数
             *查询sql被采集人员总数 统计全市建库宝被采集人员总数
             */
            String allCollectPerson = "select count(distinct t.id) as all_collect_person_cnt\n" +
                    "from jkb_person_info t \n" +
                    "where t.person_no is not null \n" +
                    "and t.is_deleted = '0'";
            List<Map<String, String>> allCollectPersonList = DataSourceUtil.execute(connection, allCollectPerson);

            if (allCollectPersonList != null && !allCollectPersonList.isEmpty()) {
                buildDataModel.setAllCollectPersonCnt(allCollectPersonList.get(0).get("all_collect_person_cnt"));//计算采集人员总数
            }
            /*
             * accpetCollectPerson:计算采集人员已送检的总数
             * 送检总数 统计全市建库宝被采集人员已送检的总数（已送检状态包含确认受理和拒绝受理）
             * 受理状态  0不受理;1未受理;2已受理',
             */
            String accpetCollectPerson = "select count(distinct t.id) as accept_collect_person_cnt\n" +
                    "from jkb_person_info t \n" +
                    "where t.person_no is not null \n" +
                    "and t.is_deleted = '0'\n" +
                    "and t.`status` in ('0','2')";
            List<Map<String, String>> accpetCollectPersonList = DataSourceUtil.execute(connection, accpetCollectPerson);

            if (accpetCollectPersonList != null && !accpetCollectPersonList.isEmpty()) {
                buildDataModel.setAccpetCollectPersonCnt(accpetCollectPersonList.get(0).get("accept_collect_person_cnt"));//计算采集人员已送检的总数
            }
            /*
             * 计算送检率
             * 送检率 (送检总数/采集总数)*100%
             * 1.送检总数与采集总数不为空的时候计算百分比
             * 2.若送检总数大于采集总数时默认为100%
             * 3.除数不能为0
             */
            if (buildDataModel != null) {
                if (!buildDataModel.getAllCollectPersonCnt().equals("0") &&StringUtils.isNotBlank(buildDataModel.getAccpetCollectPersonCnt()) && StringUtils.isNotBlank(buildDataModel.getAllCollectPersonCnt())) {
                    if (Integer.valueOf(buildDataModel.getAccpetCollectPersonCnt()) >= Integer.valueOf(buildDataModel.getAllCollectPersonCnt())) {
                        buildDataModel.setInspectionRate("100.0%");
                    } else {
                        String inspectionRate = (MathUtils.GetPercentStr(Integer.valueOf(buildDataModel.getAccpetCollectPersonCnt()),
                                Integer.valueOf(buildDataModel.getAllCollectPersonCnt()), 2)); //inspectionRate  送检率
                        buildDataModel.setInspectionRate(inspectionRate == null ? "0.0%" : inspectionRate);
                    }
                } else {
                    buildDataModel.setInspectionRate("0.0%");
                }
            }
            /*
             *计算重复率
             * 优先计算 已采集且身份证号码相同的人员数据 sameCardPersonCnt
             * 计算方式:(身份证号相同的人员总数/采集总数)*100%
             *  除数不能为0
             */
            String sameCardPersonCnt = "SELECT COUNT(DISTINCT t.id) AS same_card_person_cnt\n" +
                    "FROM jkb_person_info t \n" +
                    "WHERE t.is_deleted = '0'\n" +
                    "AND t.person_no IS NOT NULL \n" +
                    "AND t.person_idcard_no IN (\n" +
                    "SELECT DISTINCT p.person_idcard_no AS  same_card_person_cnt\n" +
                    "FROM jkb_person_info p \n" +
                    "WHERE p.is_deleted = '0'\n" +
                    "AND p.person_idcard_no NOT IN ('无')\n" +
                    "AND p.person_idcard_no IS NOT NULL \n" +
                    "GROUP BY p.person_idcard_no \n" +
                    "HAVING COUNT(p.person_idcard_no) >1)";
            List<Map<String, String>> sameCardPersonList = DataSourceUtil.execute(connection, sameCardPersonCnt);

            if (sameCardPersonList != null && !sameCardPersonList.isEmpty()) {
                buildDataModel.setSameCardPersonCnt(sameCardPersonList.get(0).get("same_card_person_cnt"));//计算已采集且身份证号码相同的人员数
            }
            if (buildDataModel != null) {
                if (!buildDataModel.getAllCollectPersonCnt().equals("0") && StringUtils.isNotBlank(buildDataModel.getSameCardPersonCnt()) && StringUtils.isNotBlank(buildDataModel.getAllCollectPersonCnt())) {
                    if (Integer.valueOf(buildDataModel.getSameCardPersonCnt()) >= Integer.valueOf(buildDataModel.getAllCollectPersonCnt())) {
                        buildDataModel.setSameRate("100.0%");
                    } else {
                        String sameRate = (MathUtils.GetPercentStr(Integer.valueOf(buildDataModel.getSameCardPersonCnt()),
                                Integer.valueOf(buildDataModel.getAllCollectPersonCnt()), 2));//sameRate 重复率
                        buildDataModel.setSameRate(sameRate == null ? "0.0%" : sameRate);
                    }
                } else {
                    buildDataModel.setSameRate("0.0%");
                }
            }
            connection.close(); //连接关闭 （若不关闭会造成 资源阻塞）
            return new ResponseData(buildDataModel);
        } catch (Exception ex) {
            logger.error("invoke BuildDataController.allBuildData error!", ex.getMessage());
        }
        return new ResponseData();
    }
}
