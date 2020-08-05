package com.bazl.dna.lims.core.model.vo;


import com.bazl.dna.lims.core.model.po.FugitivesInfo;
import com.bazl.dna.lims.core.utils.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wanghaiyang
 * @date 2020/6/15
 */
public class FugitivesInfoVo extends AbstractBaseVo<FugitivesInfo> implements Serializable {

    public FugitivesInfoVo() {
        super();
        this.entity = new FugitivesInfo();
    }

    public FugitivesInfoVo(FugitivesInfo entity) {
        super();
        this.entity = entity;
    }

    private String personTypeName;

    private String personGenderName;

    //在逃人员亲属姓名
    private String involvedParentName;

    //在逃人员亲属证件
    private String involvedParentCard;

    //录入时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date createDateTime;

    public String getPersonTypeName() {
        return personTypeName;
    }

    public void setPersonTypeName(String personTypeName) {
        this.personTypeName = personTypeName;
    }

    public String getPersonGenderName() {
        return personGenderName;
    }

    public void setPersonGenderName(String personGenderName) {
        this.personGenderName = personGenderName;
    }

    public String getInvolvedParentName() {return involvedParentName;}

    public void setInvolvedParentName(String involvedParentName) {this.involvedParentName = involvedParentName;}

    public String getInvolvedParentCard() {return involvedParentCard;}

    public void setInvolvedParentCard(String involvedParentCard) {this.involvedParentCard = involvedParentCard;}

    public Date getCreateDateTime() {return createDateTime;}

    public void setCreateDateTime(Date createDateTime) {this.createDateTime = createDateTime;}
}
