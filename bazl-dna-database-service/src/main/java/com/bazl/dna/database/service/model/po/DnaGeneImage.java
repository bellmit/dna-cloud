package com.bazl.dna.database.service.model.po;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.extension.activerecord.Model;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.sql.Blob;
    import com.baomidou.mybatisplus.annotation.TableField;
    import java.io.Serializable;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * DNA基因图谱信息
    * </p>
*
* @author lizhihua
* @since 2020-02-11
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DnaGeneImage extends Model<DnaGeneImage> implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

        @TableField("GENE_ID")
    private Integer geneId;

        @TableField("GENE_IMAGE")
    private Blob geneImage;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
