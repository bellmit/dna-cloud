package com.bazl.dna.database.service.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* <p>
    * 案件照片信息表
    * </p>
*
* @author lizhihua
* @since 2020-02-11
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CaseImage extends Model<CaseImage> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField("CASE_ID")
    private Integer caseId;

    @TableField("IMAGE_DESC")
    private String imageDesc;

    @TableField("CASE_IMAGE")
    private String caseImage;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
