package com.bazl.dna.database.service.model.po;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.extension.activerecord.Model;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.time.LocalDateTime;
    import com.baomidou.mybatisplus.annotation.TableField;
    import java.io.Serializable;
    import java.util.List;

    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 字典条目信息表
    * </p>
*
* @author lizhihua
* @since 2020-02-11
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DictItem extends Model<DictItem> implements Comparable<DictItem> {

    private static final long serialVersionUID = 1L;

            @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

        @TableField("DICT_TYPE_CODE")
    private String dictTypeCode;

        @TableField("DICT_TYPE_NAME")
    private String dictTypeName;

        @TableField("DICT_CODE")
    private String dictCode;

        @TableField("DICT_NAME")
    private String dictName;

        @TableField("PARENT_ID")
    private String parentId;

        @TableField("DICT_ITEM_DESC")
    private String dictItemDesc;

        @TableField("CREATE_DATETIME")
    private transient LocalDateTime createDatetime;

    @TableField(exist = false)
    private List<DictItem> childDictItemList;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public int compareTo(DictItem o) {
        return this.getDictCode().compareTo(o.getDictCode());
    }
}
