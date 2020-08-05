package com.bazl.dna.database.service.model.po;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.extension.activerecord.Model;
    import com.baomidou.mybatisplus.annotation.TableId;
    import com.baomidou.mybatisplus.annotation.TableField;
    import java.io.Serializable;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 人员关系表
    * </p>
*
* @author lizhihua
* @since 2020-02-11
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PersonRelativeInfo extends Model<PersonRelativeInfo> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
    * 被寻人（受害人）UUID
    */
    @TableField("TARGET_PERSON_UUID")
    private String targetPersonUuid;

    /**
    * 被寻人亲属UUID
    */
    @TableField("RELATION_PERSON_UUID")
    private String relationPersonUuid;

    /**
    * 与被害人的亲属关系，字典：PERSON_RELATION_TYPE
    */
    @TableField("RELATION_TYPE_CODE")
    private String relationTypeCode;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
