package com.bazl.dna.database.transfer.task;

import com.bazl.dna.database.transfer.constants.TransferConstants;
import com.bazl.dna.database.transfer.model.po.HttpSampleInfoModel;
import com.bazl.dna.database.transfer.model.po.SampleInfoModel;
import com.bazl.dna.database.transfer.model.po.TransferCaseQueue;
import com.bazl.dna.database.transfer.service.CaseInfoService;
import com.bazl.dna.database.transfer.service.SampleInfoService;
import com.bazl.dna.database.transfer.service.TransferCaseQueueService;
import com.bazl.dna.database.transfer.utils.HttpRequestUtil;
import com.bazl.dna.database.transfer.utils.JsonUtils;
import com.bazl.dna.database.transfer.utils.ListUtils;
import com.bazl.dna.database.transfer.utils.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 获取并更新国家库案件样本编号
 * Created by lizhihua on 2019/6/9.
 */
public class RetrieveAndUpdateNationCaseSampleNoTask extends QuartzJobBean {

    private final Logger logger = LoggerFactory.getLogger(RetrieveAndUpdateNationCaseSampleNoTask.class);


    @Autowired
    TransferCaseQueueService transferCaseQueueService;

    @Autowired
    SampleInfoService sampleInfoService;

    @Autowired
    CaseInfoService caseInfoService;

    @Autowired
    TransferNationParamsConfig transferNationParamsConfig;

    @Autowired
    CaseInfoConfigs caseInfoConfigs;

    /**
     *
     */


    /**
     *
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        if(!transferNationParamsConfig.isRetrieveCaseSampleNoActived()){
            logger.info("******************更新案件样本入国家库状态任务未开启！********************" + System.currentTimeMillis());
            return;
        }

        if(!TaskStaticFlag.RETRIEVE_CASE_SAMPLE_RUNNING) {
            logger.info("******************开始执行更新案件样本入国家库状态任务！********************" + System.currentTimeMillis());

            TaskStaticFlag.RETRIEVE_CASE_SAMPLE_RUNNING = true;

            List<TransferCaseQueue> transferCaseQueueList = null;

            try {

                transferCaseQueueList = transferCaseQueueService.selectGenerateSucceedList(transferNationParamsConfig.getLabServerNo());

                if (ListUtils.isNotEmptyList(transferCaseQueueList)) {
                    logger.info("******************待更新案件样本入国家库状态任务的任务数量为" + transferCaseQueueList.size() + ". ********************" + System.currentTimeMillis());
                    executeTransferCaseQueue(transferCaseQueueList);
                }else{
                    logger.info("******************待更新案件样本入国家库状态任务的任务数量为0. ********************" + System.currentTimeMillis());
                }

                logger.info("******************完成更新案件样本入国家库状态任务！********************" + System.currentTimeMillis());
            } catch (Exception ex) {
                logger.error("更新案件样本入国家库状态过程中出现错误！", ex);
            }
        }

        TaskStaticFlag.RETRIEVE_CASE_SAMPLE_RUNNING = false;
    }

    /**
     * 执行案件数据上报
     * @throws Exception
     */
    private void executeTransferCaseQueue(List<TransferCaseQueue> transferCaseQueueList) throws Exception {
        for (TransferCaseQueue transferCaseQueue : transferCaseQueueList) {
                updateNationSampleNoFromDNADataBase(transferCaseQueue);
        }
    }

    private boolean updateNationSampleNoFromDNADataBase(TransferCaseQueue transferCaseQueue) throws Exception {
        List<SampleInfoModel> queueSampleInfoModelList = null;

        queueSampleInfoModelList = sampleInfoService.selectSampleInfoListByCaseQueueId(transferCaseQueue.getId());


        if(ListUtils.isNotEmptyList(queueSampleInfoModelList)) {
            int succeedCnt = 0;
            for(SampleInfoModel sim : queueSampleInfoModelList) {
                if(retriveNationCaseAndSampleNo(transferCaseQueue.getCaseId(), sim)){
                    succeedCnt++;
                }
            }

            //更新成功数，等于上报样本数，表示所有样本均已上报成功
            if(succeedCnt == queueSampleInfoModelList.size()){
                try {
                    transferCaseQueue.setTransferStatus(TransferConstants.TRANSFER_STATUS_UPLOAD_SUCCEED);
                    transferCaseQueue.setTransferDatetime(new Date());
                    transferCaseQueueService.updateStatus(transferCaseQueue);
                }catch(Exception ex){
                    logger.info("标记队列状态为已上报成功时出现异常。", ex);
                }

                return true;
            }
        }

        return false;
    }

    private boolean retriveNationCaseAndSampleNo(String caseId, SampleInfoModel sampleInfoModel){
        try {
            logger.info("-------------更新入国家库检材编号开始：" + sampleInfoModel.getSampleLabNo());
            String xmlSampleParams = this.sampleXmlParam(sampleInfoModel);
            String nationSampleNo = this.queryNationSampleNo(xmlSampleParams, sampleInfoModel);
            logger.info(sampleInfoModel.getSampleLabNo() + " -------------入国家库检材编号为：" + nationSampleNo);


            logger.info("-------------更新入国家库案件编号开始：" + caseId);
            String xmlCaseParams = this.xmlCaseParam(caseId);
            String nationCaseNo = this.queryNationCaseNo(xmlCaseParams, sampleInfoModel);
            logger.info(sampleInfoModel.getSampleLabNo() + " -------------入国家库案件编号为：" + nationCaseNo);

            if(StringUtils.isNotBlank(nationSampleNo)) {
                //更新样本上报状态
                sampleInfoService.updateSampleNoAndStateByBarcode(nationSampleNo, TransferConstants.NATION_SAMPLE_STATE_TRUE, sampleInfoModel.getSampleLabNo());
                logger.info("-------------更新入国家库检材编号结束：" + nationSampleNo);
            }

            if(StringUtils.isNotBlank(nationCaseNo)) {
                caseInfoService.updateLabNoByCaseId(nationCaseNo, caseId);
                logger.info("-------------更新入国家库案件编号结束：" + nationCaseNo);
            }

            return true;
        }catch(Exception ex){
            logger.info("获取并更新案件及样本国家库编号失败。", ex);
            return false;
        }
    }

    private String queryNationSampleNo(String xmlStr, SampleInfoModel sampleInfo) throws Exception {
        String queryNationDbNoUrl = transferNationParamsConfig.getQueryNationDbNoUrl();
        String result = "";

        try {
            logger.info("-------------queryNationSampleNo接口参数,查询检材入国家库编号：" + xmlStr);

            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient(queryNationDbNoUrl);
            //设置超时单位为毫秒
            HTTPConduit conduit =(HTTPConduit) client.getConduit();
            HTTPClientPolicy policy = new HTTPClientPolicy();
            policy.setConnectionTimeout(30000); //连接超时时间
            policy.setReceiveTimeout(30000);//请求超时时间.
            conduit.setClient(policy);

            //Object[] objects = null;
            //命名空间http://service对应现勘wsdl接口中wsdl:definitions中的targetNamespace的值
            //objects = client.invoke("queryPersonNoBySampleLabNO", xmlStr);
            Object[] objects = client.invoke("queryPersonNoBySampleLabNO", new Object[]{xmlStr, "lims"});
            //.invoke(new QName("http://queryData.webservice.gdna2.highershine.com", "queryPersonNoBySampleLabNO"), xmlStr);
            if(objects != null
                    && objects.length > 0) {
                logger.info("获取案件样本入库编号返回：" + objects[0].toString());
                result = stringParseSampleResult(objects[0].toString());
            }
        } catch (Exception ex3) {
            this.logger.error("调用国家库查询样本编号接口失败。", ex3);
        }

        return result;
    }

    private String queryNationCaseNo(String xmlStr, SampleInfoModel sampleInfo) throws Exception {
        String queryNationDbNoUrl = transferNationParamsConfig.getQueryNationDbNoUrl();
        String result = "";

        try {
            logger.info("-------------queryNationCaseNo接口参数,查询案件入国家库编号：" + xmlStr);

            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient(queryNationDbNoUrl);
            //设置超时单位为毫秒
            HTTPConduit conduit =(HTTPConduit) client.getConduit();
            HTTPClientPolicy policy = new HTTPClientPolicy();
            policy.setConnectionTimeout(30000); //连接超时时间
            policy.setReceiveTimeout(30000);//请求超时时间.
            conduit.setClient(policy);

            //Object[] objects = null;
            //命名空间http://service对应现勘wsdl接口中wsdl:definitions中的targetNamespace的值
            Object[] objects = client.invoke("queryCaseInfo", new Object[]{xmlStr, "lims"});
            //invoke(new QName("http://queryData.webservice.gdna2.highershine.com", "queryCaseInfo"), xmlStr);
            if(objects != null
                    && objects.length > 0) {
                logger.info("获取案件入库编号返回：" + objects[0].toString());
                result = stringParseCaseResult(objects[0].toString());
            }
        } catch (Exception ex3) {
            this.logger.error("调用国家库查询案件编号接口失败。", ex3);
        }

        return result;
    }

    public String stringParseCaseResult(String xmlResult) throws DocumentException {
        Document document = DocumentHelper.parseText(xmlResult);
        Element root = document.getRootElement();
        String status = root.element("status").getTextTrim();
        String caseLabNo = null;

        if("1".equals(status)) {
            Element caseLists = root.element("caseInfoList");
            List caseList = caseLists.elements("caseInfo");
            if(caseList != null && caseList.size() > 0) {
                Element e = (Element)caseList.get(0);
                Element caseNo = e.element("caseNo");
                caseLabNo = caseNo.getTextTrim();
            }
        }

        return caseLabNo;
    }


    public String stringParseSampleResult(String result) throws DocumentException {
        Document document = DocumentHelper.parseText(result);
        Element root = document.getRootElement();
        String status = root.element("status").getTextTrim();

        String sampleNo = "";
       if("1".equals(status)) {
            Element personLists = root.element("personList");
            List personList = personLists.elements("personInfo");
            if(personList != null && personList.size() > 0) {
                Element e = (Element)personList.get(0);
                Element personNo = e.element("personNo");
                sampleNo = personNo.getTextTrim();

                return sampleNo;
            }
        }

        return null;
    }

    private String sampleXmlParam(SampleInfoModel sampleInfo) {
        StringBuffer str = new StringBuffer();
        str.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        str.append("<ROOT>");
        str.append("<conditions>");
        str.append("<sampleLabNo>").append(sampleInfo.getSampleLabNo()).append("</sampleLabNo>");
        str.append("<sampleName>").append(sampleInfo.getEvidenceName()).append("</sampleName>");
        str.append("</conditions>");
        str.append("</ROOT>");
        return str.toString();
    }

    private String xmlCaseParam(String caseId) {
        StringBuffer str = new StringBuffer();
        str.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        str.append("<ROOT>");
        str.append("<type>").append(1).append("</type>");
        str.append("<conditions>");
        str.append("<extid>").append(caseId).append("</extid>");
        str.append("</conditions>");
        str.append("</ROOT>");
        return str.toString();
    }

}
