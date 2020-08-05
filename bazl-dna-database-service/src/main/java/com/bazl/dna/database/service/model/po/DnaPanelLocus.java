package com.bazl.dna.database.service.model.po;

    import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
* <p>
    * 试剂盒基因座关系表
    * </p>
*
* @author lizhihua
* @since 2020-02-11
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DnaPanelLocus extends Model<DnaPanelLocus> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField("PANEL_ID")
    private Integer panelId;

    @TableField("LOCUS_ID")
    private Integer locusId;

            /**
            * 基因座在当前试剂盒中的顺序号（位置）
            */
    @TableField("LOCUS_ORD")
    private Integer locusOrd;

    @TableField("LOCUS_NAME")
    private String  locusName;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
