package com.bazl.dna.database.service.model;

import com.bazl.dna.database.service.model.po.CaseInfo;
import com.bazl.dna.database.service.model.po.ConsignmentInfo;
import com.bazl.dna.database.service.model.po.DnaGeneInfo;
import com.bazl.dna.database.service.model.po.DnaSampleInfo;
import com.bazl.dna.util.DateUtil;

import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2019/6/10.
 */
public class XmlParamsToCriminalNation {

    public String getXmlBasicInfo() {
        StringBuffer str = new StringBuffer();
        str.append("<fileInfo>");
        str.append("<version>").append("1.0").append("</version>");
        str.append("<system>").append("DNA Lims").append("</system>");
        str.append("</fileInfo>");
        return str.toString();
    }

    public String getXmlConsignment(ConsignmentInfo consignment) {
        StringBuffer str = new StringBuffer();
        str.append("<consignment>");
        if(consignment != null) {
            str.append("<consignmentId>").append(consignment.getId() == null?"":consignment.getId()).append("</consignmentId>");
//            str.append("<consignmentType>").append(consignment.getCategory() == null?"无":consignment.getCategory()).append("</consignmentType>");
            str.append("<consignOrgRegionalism>").append(consignment.getConsignOrgCode() == null?"无":consignment.getConsignOrgCode()).append("</consignOrgRegionalism>");
            str.append("<consignOrgName>").append(consignment.getConsignOrgName() == null?"无":consignment.getConsignOrgName()).append("</consignOrgName>");
            str.append("<consignPerson1Name>").append(consignment.getConsignPerson1Name() == null?"无":consignment.getConsignPerson1Name()).append("</consignPerson1Name>");
            str.append("<consignPerson1Phone>").append(consignment.getConsignPerson1Phone() == null?"无":consignment.getConsignPerson1Phone()).append("</consignPerson1Phone>");
            str.append("<consignerCertificateType>").append(consignment.getConsignPerson1CertificateType() == null?"5":consignment.getConsignPerson1CertificateType()).append("</consignerCertificateType>");
            str.append("<consignerCertificateNo>").append(consignment.getConsignPerson1CertificateNo() == null?"无":consignment.getConsignPerson1CertificateNo()).append("</consignerCertificateNo>");
            str.append("<consignPerson2Name>").append(consignment.getConsignPerson2Name() == null?"无":consignment.getConsignPerson2Name()).append("</consignPerson2Name>");
            str.append("<consignPerson2Phone>").append(consignment.getConsignPerson2Phone() == null?"无":consignment.getConsignPerson2Phone()).append("</consignPerson2Phone>");
            str.append("<consignerCertificateType2>").append(consignment.getConsignPerson2CertificateType() == null?"5":consignment.getConsignPerson2CertificateType()).append("</consignerCertificateType2>");
            str.append("<consignerCertificateNo2>").append(consignment.getConsignPerson2CertificateNo() == null?"无":consignment.getConsignPerson2CertificateNo()).append("</consignerCertificateNo2>");
            str.append("<identifyRequest>").append(consignment.getIdentifyRequirement() == null?"无":consignment.getIdentifyRequirement()).append("</identifyRequest>");
            str.append("<consignDatetime>").append(consignment.getConsignmentRegDatetime() == null ? "" : DateUtil.dateToString(Date.from(consignment.getConsignmentRegDatetime().atZone(ZoneId.systemDefault()).toInstant()), "yyyyMMddHHmmss")).append("</consignDatetime>");
            str.append("<AcceptPersonId>").append(consignment.getAcceptPersonId() == null?"":consignment.getAcceptPersonId()).append("</AcceptPersonId>");
            str.append("<isAppend>").append(consignment.getAppendFlag() == null?"":consignment.getAppendFlag()).append("</isAppend>");
            str.append("<acceptDatetime>").append(consignment.getAcceptDatetime() == null ? "" : DateUtil.dateToString(Date.from(consignment.getAcceptDatetime().atZone(ZoneId.systemDefault()).toInstant()),"yyyyMMddHHmmss")).append("</acceptDatetime>");
            str.append("<storePersonName>").append(consignment.getStorePersonName() == null?"":consignment.getStorePersonName()).append("</storePersonName>");
            str.append("<storeDatetime>").append(consignment.getStoreDatetime() == null ? "" : DateUtil.dateToString(Date.from(consignment.getStoreDatetime().atZone(ZoneId.systemDefault()).toInstant()), "yyyyMMddHHmmss")).append("</storeDatetime>");
            str.append("<updateUser>").append(consignment.getUpdatePersonName() == null?"":consignment.getUpdatePersonName()).append("</updateUser>");
            str.append("<updateDatetime>").append(consignment.getUpdateDatetime() == null?"":DateUtil.dateToString(Date.from(consignment.getUpdateDatetime().atZone(ZoneId.systemDefault()).toInstant()), "yyyyMMddHHmmss")).append("</updateDatetime>");
            str.append("<consignmentNo>").append(consignment.getConsignmentFileNo() == null?"":consignment.getConsignmentFileNo()).append("</consignmentNo>");
            str.append("<consignOrgZipCode>").append(consignment.getConsignOrgZipCode() == null?"":consignment.getConsignOrgZipCode()).append("</consignOrgZipCode>");
            str.append("<consignOrgAddress>").append(consignment.getConsignOrgAddress() == null?"":consignment.getConsignOrgAddress()).append("</consignOrgAddress>");
            str.append("<consignOrgPhone>").append(consignment.getConsignOrgPhone() == null?"":consignment.getConsignOrgPhone()).append("</consignOrgPhone>");
//            str.append("<consignerDuty>").append(consignment.getConsignerDuty() == null?"":consignment.getConsignerDuty()).append("</consignerDuty>");
//            str.append("<consignerDuty2>").append(consignment.getConsignerDuty2() == null?"":consignment.getConsignerDuty2()).append("</consignerDuty2>");
            str.append("<remark>").append(consignment.getMoreRemarks() == null?"":consignment.getMoreRemarks()).append("</remark>");
            str.append("<acceptOrgName>").append(consignment.getConsignOrgName() == null?"":consignment.getConsignOrgName()).append("</acceptOrgName>");
            str.append("<identifyResult>").append(consignment.getIdentifyRequirement() == null?"无":consignment.getIdentifyRequirement()).append("</identifyResult>");
        }

        str.append("</consignment>");
        return str.toString();
    }

    public String getXmlCaseInfoModel(CaseInfo caseInfoModel) {
        StringBuffer str = new StringBuffer();
        str.append("<caseInfo>");
        if(caseInfoModel != null) {
            str.append("<caseId>").append(caseInfoModel.getId() == null?"":caseInfoModel.getId()).append("</caseId>");
            str.append("<caseName>").append(" <![CDATA[ ").append(caseInfoModel.getCaseName() == null?"":caseInfoModel.getCaseName()).append("]]>").append("</caseName>");
            str.append("<caseType>").append(caseInfoModel.getCaseType() == null?"":caseInfoModel.getCaseType()).append("</caseType>");
            str.append("<caseProperty>").append(caseInfoModel.getCaseProperty() == null?"00":caseInfoModel.getCaseProperty()).append("</caseProperty>");
            str.append("<sceneRegionalism>").append(caseInfoModel.getSceneRegionalism() == null?"无":caseInfoModel.getSceneRegionalism()).append("</sceneRegionalism>");
            str.append("<scenePlace>").append(caseInfoModel.getScenePlace() == null?"无":caseInfoModel.getScenePlace()).append("</scenePlace>");
            str.append("<caseBrief>").append(" <![CDATA[ ").append(caseInfoModel.getCaseSummary() == null?"无":caseInfoModel.getCaseSummary()).append("]]>").append("</caseBrief>");
            str.append("<createUser>").append(caseInfoModel.getStorePersonName() == null?"":caseInfoModel.getStorePersonName()).append("</createUser>");
            str.append("<createDatetime>").append(caseInfoModel.getStoreDatetime() == null?"":DateUtil.dateToString(Date.from(caseInfoModel.getStoreDatetime().atZone(ZoneId.systemDefault()).toInstant()),"yyyyMMddHHmmss")).append("</createDatetime>");
            str.append("<updateUser>").append(caseInfoModel.getUpdatePersonName() == null?"":caseInfoModel.getUpdatePersonName()).append("</updateUser>");
            str.append("<updateDatetime>").append(caseInfoModel.getUpdateDatetime() == null?"":DateUtil.dateToString(Date.from(caseInfoModel.getUpdateDatetime().atZone(ZoneId.systemDefault()).toInstant()),"yyyyMMddHHmmss")).append("</updateDatetime>");
            str.append("<caseLevel>").append(caseInfoModel.getCaseLevel() == null?"":caseInfoModel.getCaseLevel()).append("</caseLevel>");
        }
        str.append("</caseInfo>");
        return str.toString();
    }
    public String getSampleInfoModelList(List<DnaSampleInfo> sampleInfoList) {
        StringBuffer str = new StringBuffer();
        str.append("<sampleInfoList>");
        for(DnaSampleInfo sampleInfo : sampleInfoList) {
            str.append("<sampleInfo>");
            str.append("<sampleId>").append(sampleInfo.getId() == null ? "" : sampleInfo.getId()).append("</sampleId>");
            str.append("<sampleLabNo>").append(sampleInfo.getSampleLabNo() == null ? "无" : sampleInfo.getSampleLabNo()).append("</sampleLabNo>");
            str.append("<sampleName>").append(" <![CDATA[ ").append(sampleInfo.getSampleName() == null ? "" : sampleInfo.getSampleName()).append("]]>").append("</sampleName>");
            str.append("<sampleType>").append(sampleInfo.getSampleType() == null ? "" : sampleInfo.getSampleType()).append("</sampleType>");
//            str.append("<sampleStatus>").append(sampleInfo.getsam() == null ? "7" : sampleInfo.getSampleStatus()).append("</sampleStatus>");
            str.append("<acceptOpinion>").append("").append("</acceptOpinion>");
            str.append("<selfObjectId>").append("" + sampleInfo.getSampleLabNo()).append("</selfObjectId>");
            str.append("<selfObjectType>").append("").append("</selfObjectType>");
            str.append("<relationObjectId>").append("").append("</relationObjectId>");
            str.append("<relation>").append("").append("</relation>");
            str.append("<createUser>").append(sampleInfo.getStorePersonName() == null ? "" : sampleInfo.getStorePersonName()).append("</createUser>");
            str.append("<createDatetime>").append(sampleInfo.getStoreDatetime() == null ? "" : DateUtil.dateToString(Date.from(sampleInfo.getStoreDatetime().atZone(ZoneId.systemDefault()).toInstant()), "yyyyMMddHHmmss")).append("</createDatetime>");
            str.append("<updateUser>").append(sampleInfo.getUpdatePersonName() == null ? "" : sampleInfo.getUpdatePersonName()).append("</updateUser>");
            str.append("<updateDatetime>").append(sampleInfo.getUpdateDatetime() == null ? "" : DateUtil.dateToString(Date.from(sampleInfo.getStoreDatetime().atZone(ZoneId.systemDefault()).toInstant()), "yyyyMMddHHmmss")).append("</updateDatetime>");
            str.append("<sampleDesc>").append(sampleInfo.getSampleDesc() == null ? "" : sampleInfo.getSampleDesc()).append("</sampleDesc>");
            str.append("</sampleInfo>");
        }
        str.append("</sampleInfoList>");
        return str.toString();
    }

    public String getXmlSampleDnaGeneList(List<DnaGeneInfo> sampleDnaGeneList) {
        StringBuffer str = new StringBuffer();
        str.append("<sampleDnaGeneList>");
        if(sampleDnaGeneList != null && sampleDnaGeneList.size() > 0) {
            Iterator<DnaGeneInfo> var4 = sampleDnaGeneList.iterator();

            while(var4.hasNext()) {
                DnaGeneInfo sampleDnaGene = var4.next();
                str.append("<sampleDnaGene>");
                str.append("<geneId>").append(sampleDnaGene.getId() == null?"":sampleDnaGene.getId() + "HHH").append("</geneId>");
                str.append("<sampleId>").append(sampleDnaGene.getSampleId() == null?"":sampleDnaGene.getSampleId()).append("</sampleId>");
                str.append("<geneType>").append(sampleDnaGene.getGeneType() == null?"1":sampleDnaGene.getGeneType()).append("</geneType>");
                str.append("<geneInfo>").append(sampleDnaGene.getGeneInfoDetail() == null?"":sampleDnaGene.getGeneInfoDetail()).append("</geneInfo>");
                str.append("<createUser>").append(sampleDnaGene.getStorePersonName() == null?"":sampleDnaGene.getStorePersonName()).append("</createUser>");
                str.append("<createDatetime>").append(sampleDnaGene.getStoreDatetime() == null?"":DateUtil.dateToString(Date.from(sampleDnaGene.getStoreDatetime().atZone(ZoneId.systemDefault()).toInstant()),"yyyyMMddHHmmss")).append("</createDatetime>");
                str.append("<updateUser>").append(sampleDnaGene.getUpdatePersonName() == null?"":sampleDnaGene.getUpdatePersonName()).append("</updateUser>");
                str.append("<updateDatetime>").append(sampleDnaGene.getUpdateDatetime() == null?"":DateUtil.dateToString(Date.from(sampleDnaGene.getStoreDatetime().atZone(ZoneId.systemDefault()).toInstant()),"yyyyMMddHHmmss")).append("</updateDatetime>");
                str.append("</sampleDnaGene>");
            }
        }

        str.append("</sampleDnaGeneList>");
        return str.toString();
    }
}
