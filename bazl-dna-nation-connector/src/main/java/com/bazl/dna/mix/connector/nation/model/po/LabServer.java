package com.bazl.dna.mix.connector.nation.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/*
* LAB_SERVER
*实验室服务器表
* */
public class LabServer {
    private String id;

    private String labServerNo;

    private String labServerName;

    private String labServerKey;

    private String serverType;

    private String serverIpAddr;

    private String serverPort;

    private Short rootFlag;

    private String parentServerNo;

    private Short activeFlag;

    private Integer serverOrd;

    private String remark;

    private String createUser;

    @JSONField(name = "createDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createDatetime;

    private String updateUser;

    @JSONField(name = "updateDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updateDatetime;

    private Integer labServerRole;

    private String controlOperate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLabServerNo() {
        return labServerNo;
    }

    public void setLabServerNo(String labServerNo) {
        this.labServerNo = labServerNo == null ? null : labServerNo.trim();
    }

    public String getLabServerName() {
        return labServerName;
    }

    public void setLabServerName(String labServerName) {
        this.labServerName = labServerName == null ? null : labServerName.trim();
    }

    public String getLabServerKey() {
        return labServerKey;
    }

    public void setLabServerKey(String labServerKey) {
        this.labServerKey = labServerKey == null ? null : labServerKey.trim();
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType == null ? null : serverType.trim();
    }

    public String getServerIpAddr() {
        return serverIpAddr;
    }

    public void setServerIpAddr(String serverIpAddr) {
        this.serverIpAddr = serverIpAddr == null ? null : serverIpAddr.trim();
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort == null ? null : serverPort.trim();
    }

    public Short getRootFlag() {
        return rootFlag;
    }

    public void setRootFlag(Short rootFlag) {
        this.rootFlag = rootFlag;
    }

    public String getParentServerNo() {
        return parentServerNo;
    }

    public void setParentServerNo(String parentServerNo) {
        this.parentServerNo = parentServerNo == null ? null : parentServerNo.trim();
    }

    public Short getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Short activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Integer getServerOrd() {
        return serverOrd;
    }

    public void setServerOrd(Integer serverOrd) {
        this.serverOrd = serverOrd;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public Integer getLabServerRole() {
        return labServerRole;
    }

    public void setLabServerRole(Integer labServerRole) {
        this.labServerRole = labServerRole;
    }

    public String getControlOperate() {
        return controlOperate;
    }

    public void setControlOperate(String controlOperate) {
        this.controlOperate = controlOperate == null ? null : controlOperate.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LabServer)) return false;

        LabServer labServer = (LabServer) o;

        if (id != null ? !id.equals(labServer.id) : labServer.id != null) return false;
        if (labServerNo != null ? !labServerNo.equals(labServer.labServerNo) : labServer.labServerNo != null)
            return false;
        if (labServerName != null ? !labServerName.equals(labServer.labServerName) : labServer.labServerName != null)
            return false;
        if (labServerKey != null ? !labServerKey.equals(labServer.labServerKey) : labServer.labServerKey != null)
            return false;
        if (serverType != null ? !serverType.equals(labServer.serverType) : labServer.serverType != null) return false;
        if (serverIpAddr != null ? !serverIpAddr.equals(labServer.serverIpAddr) : labServer.serverIpAddr != null)
            return false;
        if (serverPort != null ? !serverPort.equals(labServer.serverPort) : labServer.serverPort != null) return false;
        if (rootFlag != null ? !rootFlag.equals(labServer.rootFlag) : labServer.rootFlag != null) return false;
        if (parentServerNo != null ? !parentServerNo.equals(labServer.parentServerNo) : labServer.parentServerNo != null)
            return false;
        if (activeFlag != null ? !activeFlag.equals(labServer.activeFlag) : labServer.activeFlag != null) return false;
        if (serverOrd != null ? !serverOrd.equals(labServer.serverOrd) : labServer.serverOrd != null) return false;
        if (remark != null ? !remark.equals(labServer.remark) : labServer.remark != null) return false;
        if (createUser != null ? !createUser.equals(labServer.createUser) : labServer.createUser != null) return false;
        if (createDatetime != null ? !createDatetime.equals(labServer.createDatetime) : labServer.createDatetime != null)
            return false;
        if (updateUser != null ? !updateUser.equals(labServer.updateUser) : labServer.updateUser != null) return false;
        if (updateDatetime != null ? !updateDatetime.equals(labServer.updateDatetime) : labServer.updateDatetime != null)
            return false;
        if (labServerRole != null ? !labServerRole.equals(labServer.labServerRole) : labServer.labServerRole != null)
            return false;
        return controlOperate != null ? controlOperate.equals(labServer.controlOperate) : labServer.controlOperate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (labServerNo != null ? labServerNo.hashCode() : 0);
        result = 31 * result + (labServerName != null ? labServerName.hashCode() : 0);
        result = 31 * result + (labServerKey != null ? labServerKey.hashCode() : 0);
        result = 31 * result + (serverType != null ? serverType.hashCode() : 0);
        result = 31 * result + (serverIpAddr != null ? serverIpAddr.hashCode() : 0);
        result = 31 * result + (serverPort != null ? serverPort.hashCode() : 0);
        result = 31 * result + (rootFlag != null ? rootFlag.hashCode() : 0);
        result = 31 * result + (parentServerNo != null ? parentServerNo.hashCode() : 0);
        result = 31 * result + (activeFlag != null ? activeFlag.hashCode() : 0);
        result = 31 * result + (serverOrd != null ? serverOrd.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createDatetime != null ? createDatetime.hashCode() : 0);
        result = 31 * result + (updateUser != null ? updateUser.hashCode() : 0);
        result = 31 * result + (updateDatetime != null ? updateDatetime.hashCode() : 0);
        result = 31 * result + (labServerRole != null ? labServerRole.hashCode() : 0);
        result = 31 * result + (controlOperate != null ? controlOperate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LabServer{" +
                "id='" + id + '\'' +
                ", labServerNo='" + labServerNo + '\'' +
                ", labServerName='" + labServerName + '\'' +
                ", labServerKey='" + labServerKey + '\'' +
                ", serverType='" + serverType + '\'' +
                ", serverIpAddr='" + serverIpAddr + '\'' +
                ", serverPort='" + serverPort + '\'' +
                ", rootFlag=" + rootFlag +
                ", parentServerNo='" + parentServerNo + '\'' +
                ", activeFlag=" + activeFlag +
                ", serverOrd=" + serverOrd +
                ", remark='" + remark + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createDatetime=" + createDatetime +
                ", updateUser='" + updateUser + '\'' +
                ", updateDatetime=" + updateDatetime +
                ", labServerRole=" + labServerRole +
                ", controlOperate='" + controlOperate + '\'' +
                '}';
    }
}