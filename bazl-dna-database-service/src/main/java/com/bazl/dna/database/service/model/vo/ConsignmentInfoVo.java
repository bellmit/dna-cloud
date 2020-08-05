package com.bazl.dna.database.service.model.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.bazl.dna.database.service.model.po.ConsignmentInfo;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ConsignmentInfoVo extends ConsignmentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 委托时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private transient LocalDateTime consignmentDatetime;

    /**
     * 受理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private transient LocalDateTime acceptDate;



}
