package com.bazl.dna.database.service.model.po;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.extension.activerecord.Model;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.time.LocalDateTime;
    import com.baomidou.mybatisplus.annotation.TableField;
    import java.io.Serializable;
    import java.util.List;

    import com.fasterxml.jackson.annotation.JsonFormat;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 质控样本信息表
    * </p>
*
* @author lizhihua
* @since 2020-02-11
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QcSampleInfo extends Model<QcSampleInfo> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 实验室服务器编号
     */
    @TableField("LAB_SERVER_NO")
    private String labServerNo;

    /**
    * 质控样本类型，字典：QC_SAMPLE_TYPE
    */
    @TableField("QC_SAMPLE_TYPE")
    private String qcSampleType;

    /**
    * 样本名称
    */
    @TableField("QC_SAMPLE_NAME")
    private String qcSampleName;

    /**
    * 样本编号
    */
    @TableField("QC_SAMPLE_NO")
    private String qcSampleNo;

    /**
    * 质控人员类型， 字典：QC_PERSON_TYPE
    */
    @TableField("QC_PERSON_TYPE")
    private String qcPersonType;

    /**
    * 人员姓名
    */
    @TableField("QC_PERSON_NAME")
    private String qcPersonName;

    /**
    * 人员身份证号
    */
    @TableField("QC_PERSON_IDCARD_NO")
    private String qcPersonIdcardNo;

    /**
    * 性别
    */
    @TableField("QC_PERSON_GENDER")
    private String qcPersonGender;

    /**
    * 所属单位id
    */
    @TableField("QC_PERSON_ORG_ID")
    private String qcPersonOrgId;

    /**
    * 所属单位名称
    */
    @TableField("QC_PERSON_ORG_NAME")
    private String qcPersonOrgName;

    /**
     * 使用STR试剂盒id
     */
    @TableField("STR_PANEL_ID")
    private int strPanelId;

    /**
     * 使用STR试剂盒名称
     */
    @TableField("STR_PANEL_NAME")
    private String strPanelName;

    /**
     * 使用YSTR试剂盒id
     */
    @TableField("YSTR_PANEL_ID")
    private int yStrPanelId;

    /**
     * 使用YSTR试剂盒名称
     */
    @TableField("YSTR_PANEL_NAME")
    private String yStrPanelName;

    /**
     * STR基因信息
     */
    @TableField("STR_GENE_INFO")
    private String strGeneInfo;

    /**
     * YSTR基因信息
     */
    @TableField("YSTR_GENE_INFO")
    private String yStrGeneInfo;


    @TableField(exist = false)
    private List<DnaGeneInfoDetail> geneInfoDetail;

    /**
     * 基因信息页面显示
     */
    @TableField(exist = false)
    private List<GeneInfoDetail> geneInfoDetailBo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("CREATE_DATETIME")
    private transient LocalDateTime createDatetime;

        @TableField("CREATE_PERSON_NAME")
    private String createPersonName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("UPDATE_DATETIME")
    private transient LocalDateTime updateDatetime;

        @TableField("UPDATE_PERSON_NAME")
    private String updatePersonName;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


    public static class GeneInfoDetail {
        private String locusName;
        private String alleleOne;
        private String alleleTwo;


        public String getLocusName() {
            return locusName;
        }

        public void setLocusName(String locusName) {
            this.locusName = locusName;
        }

        public String getAlleleOne() {
            return alleleOne;
        }

        public void setAlleleOne(String alleleOne) {
            this.alleleOne = alleleOne;
        }

        public String getAlleleTwo() {
            return alleleTwo;
        }

        public void setAlleleTwo(String alleleTwo) {
            this.alleleTwo = alleleTwo;
        }
    }

}
