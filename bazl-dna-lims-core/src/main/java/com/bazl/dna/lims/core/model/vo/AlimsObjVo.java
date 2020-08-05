package com.bazl.dna.lims.core.model.vo;


import com.bazl.dna.lims.core.model.po.ErsReportInfo;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/7/21.
 */
public class AlimsObjVo implements Serializable {

    //案件信息
    private ErsReportInfo ersReportInfo;
    //文件
    private byte[] fileBinary;
    //签名
    private String  defalutSigner;
    private String opType;
    private String token;
    private String caseId;

    public ErsReportInfo getErsReportInfo() {
        return ersReportInfo;
    }

    public void setErsReportInfo(ErsReportInfo ersReportInfo) {
        this.ersReportInfo = ersReportInfo;
    }

    public byte[] getFileBinary() {
        return fileBinary;
    }

    public void setFileBinary(byte[] fileBinary) {
        this.fileBinary = fileBinary;
    }

    public String getDefalutSigner() {
        return defalutSigner;
    }

    public void setDefalutSigner(String defalutSigner) {
        this.defalutSigner = defalutSigner;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
}
