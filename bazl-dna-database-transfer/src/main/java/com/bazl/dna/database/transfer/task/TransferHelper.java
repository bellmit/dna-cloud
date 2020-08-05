package com.bazl.dna.database.transfer.task;


import com.bazl.dna.database.transfer.constants.TransferConstants;
import com.bazl.dna.database.transfer.utils.StringUtils;

/**
 * Created by Administrator on 2019/6/9.
 */
public class TransferHelper {


    /**
     * 判断案件类型是否为刑事案件
     * @param caseType
     * @return
     */
    public static boolean isCaseTypeCriminal(String caseType){
        return TransferConstants.CASE_TYPE_CRIMINAL.equals(caseType);
    }

    /**
     * 判断案件类型是否为非刑事案件
     * @param caseType
     * @return
     */
    public static boolean isCaseTypeNotCriminal(String caseType){
        return TransferConstants.CASE_TYPE_NOT_CRIMINAL_NEW_2.equals(caseType)
                || TransferConstants.CASE_TYPE_NOT_CRIMINAL_NEW_4.equals(caseType)
                || TransferConstants.CASE_TYPE_NOT_CRIMINAL_NEW_5.equals(caseType);
    }


    /**
     * 判断案件类型是否为民事案件
     * @param caseType
     * @return
     */
    public static boolean isCaseTypeCivil(String caseType){
        return TransferConstants.CASE_TYPE_CIVIL_NEW_3.equals(caseType);
    }

    /**
     * 判断样本入库类型是否为物证（或混合物证）
     * @return
     */
    public static boolean isInstoreSampleTypeEvidence(String instoreSampleType, String evidenceNo){
        if(TransferConstants.INSTORE_SAMPLE_TYPE_EVIDENCE.equals(instoreSampleType)
                || TransferConstants.INSTORE_SAMPLE_TYPE_MIX_EVIDENCE.equals(instoreSampleType)
                || (TransferConstants.INSTORE_SAMPLE_TYPE_YSTR.equals(instoreSampleType)
                && StringUtils.isNotBlank(evidenceNo))){
            return true;
        }

        return false;
    }


    /**
     * 将案件性质转换为国家库对应案件性质
     * @param caseProperty
     * @param caseInfoConfigs
     * @return
     */
    public static String convertToNationCaseProperty(String caseProperty, CaseInfoConfigs caseInfoConfigs){
        if(StringUtils.isBlank(caseProperty)){
            return caseInfoConfigs.getDefaultCaseProperty();
        }

        /**
         * 凶杀、伤害致死  转为 杀人
         */
        if (caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_XIONGSHA)
                || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_SHANGHAIZHISI)) {
            return caseInfoConfigs.getCasePropertyShaRen();
        }else if (caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_SHANGHAI)) {
            /**
             * 伤害  转为 伤害
             */
            return caseInfoConfigs.getCasePropertyShangHai();
        }else if (caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_QITADAOQIE)
                || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_RUSHIDAOQIE)
                || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_DAOQIEJIDONGCHE)
                || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_DAOQIECHENEICAIWU)
                || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_DAOQIANGGONGDI)
                || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_PALOUZUANCHUANG)
                || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_DAOQIEBAOXIANGUI)
                || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_PAQIE)
                || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_DAOQIE)
                || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_HUAPIANKAISUO)
                || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_DAOQIEDIANLAN)
        ) {
            /**
             * 其它盗窃、入室盗窃、盗窃机动车、盗窃车内财物、爬楼钻窗、盗窃保险柜、盗抢工地   转为 盗窃
             */
            return caseInfoConfigs.getCasePropertyDaoQie();
        }else if (caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_QIANGDUO)
                || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_QIANGJIE)) {
            /**
             * 抢夺、抢劫 转为 抢劫
             */

            return caseInfoConfigs.getCasePropertyQiangjie();
        }else if (caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_QIANGJIAN)) {
            /**
             * 强奸 转为 强奸
             */
            return caseInfoConfigs.getCasePropertyQiangJian();
        }else if (caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_JIAOTONGSHIGU)) {

            /**
             * 交通事故 转为 交通
             */

            return caseInfoConfigs.getCasePropertyJiaoTong();
        }else if (caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_ZHAPIAN)) {
            /**
             * 诈骗 转为诈骗
             */

            return caseInfoConfigs.getCasePropertyZhaPian();
        }else if (caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_ZOUSHIRENKOU)
                || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_FEIZHENGCHANGSIWANG)
                || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_ZHIAN)
                || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_QITA)
                || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_DAGUAI)
                || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_SHIYUANRENDING)
                || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_QINYUAN)
                || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_GAOKONGZHUIWU)
                || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_QITA)) {
            /**
             * 走失人口、治安、打拐、尸源认定、非正常死亡、其它、亲缘  转为 其它
             */

            return caseInfoConfigs.getCasePropertyQiTa();
        }

        return caseInfoConfigs.getDefaultCaseProperty();

    }

    /**
     * 将案件状态转换为国家库对应案件状态
     * @param caseStatus
     * @param caseInfoConfigs
     * @return
     */
    public static String convertToNationCaseStatus(String caseStatus, CaseInfoConfigs caseInfoConfigs){
        if(StringUtils.isBlank(caseStatus)){
            return caseInfoConfigs.getCaseStatusDefault();
        }

        if(caseStatus.equals(TransferConstants.CASE_STATUS_NEW_PENDING)
                || caseStatus.equals(TransferConstants.CASE_STATUS_NEW_ACCEPTED)
                || caseStatus.equals(TransferConstants.CASE_STATUS_NEW_REFUSED)){
            return caseInfoConfigs.getCaseStatusNotSolved();
        }else if(caseStatus.equals(TransferConstants.CASE_STATUS_NEW_FINISHED)){
            return caseInfoConfigs.getCaseStatusMatched();
        }

        return caseInfoConfigs.getCaseStatusDefault();
    }


    /**
     * 将案件级别转换为国家库对应案件级别
     * @param caseLevel
     * @param caseInfoConfigs
     * @return
     */
    public static String convertToNationCaseLevel(String caseLevel, CaseInfoConfigs caseInfoConfigs){
        if(StringUtils.isBlank(caseLevel)){
            return caseInfoConfigs.getCaseLevelDefault();
        }

        if(caseLevel.equals(TransferConstants.CASE_LEVEL_01)){
            return caseInfoConfigs.getCaseLevelSerious();
        }else if(caseLevel.equals(TransferConstants.CASE_LEVEL_02)){
            return caseInfoConfigs.getCaseLevelLarge();
        }else if(caseLevel.equals(TransferConstants.CASE_LEVEL_03)){
            return caseInfoConfigs.getCaseLevelMajor();
        }else if(caseLevel.equals(TransferConstants.CASE_LEVEL_04)){
            return caseInfoConfigs.getCaseLevelGeneral();
        }else if(caseLevel.equals(TransferConstants.CASE_LEVEL_05)){
            return caseInfoConfigs.getCaseLevelSlight();
        }else if(caseLevel.equals(TransferConstants.CASE_LEVEL_06)){
            return caseInfoConfigs.getCaseLevelOther();
        }

        return caseInfoConfigs.getCaseLevelDefault();
    }

    /**
     * 判断当前样本入库类型是否为失踪人口亲属
     * @param sampleType
     * @return
     */
    public static boolean isSampleTypeMissingPeople(String sampleType){
        if(TransferConstants.INSTORE_SAMPLE_TYPE_MISSING_RELATIVE.equals(sampleType)){
            return true;
        }

        return false;
    }


    /**
     * 将委托类型转换为国家库对应委托类型
     * @param caseType
     * @param caseInfoConfigs
     * @return
     */
    public static String convertToNationConsignmentType(String caseType, CaseInfoConfigs caseInfoConfigs){
        if(StringUtils.isBlank(caseType)){
            return caseInfoConfigs.getConsignmentTypeDefault();
        }

        if(caseType.equals(TransferConstants.CASE_TYPE_CRIMINAL)){
            return caseInfoConfigs.getConsignmentTypeCase();
        }

        return caseInfoConfigs.getConsignmentTypeDefault();
    }

    /**
     * 转换国家库证件类型
     * @param credentialType
     * @param caseInfoConfigs
     * @return
     */
    public static String convertToNationCredentialType(String credentialType, CaseInfoConfigs caseInfoConfigs){
        String credentialTypeDefault = caseInfoConfigs.getCredentialTypeDefault();

        if (TransferConstants.CERTIFICATE_TYPE_1.equals(credentialType)) {
            credentialTypeDefault = caseInfoConfigs.getCredentialTypePolicer();
        }else if (TransferConstants.CERTIFICATE_TYPE_2.equals(credentialType)) {
            credentialTypeDefault = caseInfoConfigs.getCredentialTypeIdcard();
        }else if (TransferConstants.CERTIFICATE_TYPE_3.equals(credentialType)) {
            credentialTypeDefault = caseInfoConfigs.getCredentialTypeSoldier();
        }else if (TransferConstants.CERTIFICATE_TYPE_4.equals(credentialType)) {
            credentialTypeDefault = caseInfoConfigs.getCredentialTypeStudent();
        }else if (TransferConstants.CERTIFICATE_TYPE_5.equals(credentialType)) {
            credentialTypeDefault = caseInfoConfigs.getCredentialTypeOther();
        }else if (TransferConstants.CERTIFICATE_TYPE_6.equals(credentialType)) {
            credentialTypeDefault = caseInfoConfigs.getCredentialTypePassport();
        }else if (TransferConstants.CERTIFICATE_TYPE_7.equals(credentialType)) {
            credentialTypeDefault = caseInfoConfigs.getCredentialTypePassport();
        }


        return credentialTypeDefault;
    }

    /**
     * 转换鉴定要求
     * @param identifyRequest
     * @return
     */
    public static String convertIdentifyRequest(String identifyRequest){
        String identifyRequestName = TransferConstants.IDENTIFY_REQUEST_SAME_NAME;
        if(identifyRequest.equals(TransferConstants.IDENTIFY_REQUIRED_SAME)){
            identifyRequestName = TransferConstants.IDENTIFY_REQUEST_SAME_NAME;
        }else if(identifyRequest.equals(TransferConstants.IDENTIFY_REQUIRED_RELATIVE)){
            identifyRequestName = TransferConstants.IDENTIFY_REQUEST_RELATIVE_NAME;
        }

        return identifyRequestName;
    }


    /**
     * 转换物证承载类型
     * @param evidenceCarrierType
     * @param caseInfoConfigs
     * @return
     */
    public static String convertEvidenceCarrierType(String evidenceCarrierType, CaseInfoConfigs caseInfoConfigs){
        String evidenceCarrierTypeDefault = caseInfoConfigs.getEvidenceCarrierTypeDefault();


        if(evidenceCarrierType.equals(TransferConstants.SAMPLE_TYPE_BLOOD)){
            evidenceCarrierTypeDefault = caseInfoConfigs.getEvidenceCarrierTypeBlood();
        }else if(evidenceCarrierType.equals(TransferConstants.SAMPLE_TYPE_SALIVA)){
            evidenceCarrierTypeDefault = caseInfoConfigs.getEvidenceCarrierTypeSaliva();
        }else{
            evidenceCarrierTypeDefault = caseInfoConfigs.getEvidenceCarrierTypeOther();
        }

        return evidenceCarrierTypeDefault;
    }

    /**
     * 转换性别
     * @param gender
     * @return
     */
    public static String convertNationGener(String gender){
        String genderDefault = TransferConstants.NATION_GENDER_UNKNOWN;
        if(TransferConstants.GENDER_1.equals(gender)){
            genderDefault = TransferConstants.NATION_GENDER_MALE;
        }else if(TransferConstants.GENDER_2.equals(gender)){
            genderDefault = TransferConstants.NATION_GENDER_FEMALE;
        }else if(TransferConstants.GENDER_3.equals(gender)){
            genderDefault = TransferConstants.NATION_GENDER_UNKNOWN;
        }

        return genderDefault;
    }

    /**
     * 转换样本类型
     * @param sampleType
     * @param caseInfoConfigs
     * @return
     */
    public static String convertNationSampleType(String sampleType, CaseInfoConfigs caseInfoConfigs){
        String sampleTypeDefault = caseInfoConfigs.getSampleTypeDefault();

        if(sampleType.equals(TransferConstants.SAMPLE_TYPE_BLOOD)){
            sampleTypeDefault = caseInfoConfigs.getSampleTypeBlood();
        }else if(sampleType.equals(TransferConstants.SAMPLE_TYPE_SALIVA)){
            sampleTypeDefault = caseInfoConfigs.getSampleTypeSaliva();
        }else if(sampleType.equals(TransferConstants.SAMPLE_TYPE_EXFOLIATED_CELLS)){
            sampleTypeDefault = caseInfoConfigs.getSampleTypeCells();
        }else if(sampleType.equals(TransferConstants.SAMPLE_TYPE_SPERM)){
            sampleTypeDefault = caseInfoConfigs.getSampleTypeSperm();
        }else if(sampleType.equals(TransferConstants.SAMPLE_TYPE_HAIR)){
            sampleTypeDefault = caseInfoConfigs.getSampleTypeHairs();
        }else if(sampleType.equals(TransferConstants.SAMPLE_TYPE_SKELETON_TOOTH)){
            sampleTypeDefault = caseInfoConfigs.getSampleTypeSkeleton();
        }else if(sampleType.equals(TransferConstants.SAMPLE_TYPE_MUSCLE_TISSUE)){
            sampleTypeDefault = caseInfoConfigs.getSampleTypeTissue();
        }else{
            sampleTypeDefault = caseInfoConfigs.getEvidenceCarrierTypeOther();
        }

        return sampleTypeDefault;
    }

    /**
     * 入库样本类别
     * @param instoreSampleType
     * @return
     */
    public static final String convertNationSampleCategory(String instoreSampleType){
        String instoreSampleTypeDefault = TransferConstants.NATION_SAMPLE_CATEGORY_EVIDENCE;
        if(TransferConstants.INSTORE_SAMPLE_TYPE_EVIDENCE.equals(instoreSampleType)
                || TransferConstants.INSTORE_SAMPLE_TYPE_MIX_EVIDENCE.equals(instoreSampleType)){
            instoreSampleTypeDefault = TransferConstants.NATION_SAMPLE_CATEGORY_EVIDENCE;
        }else if(TransferConstants.INSTORE_SAMPLE_TYPE_OFFENDER.equals(instoreSampleType)){
            instoreSampleTypeDefault = TransferConstants.NATION_SAMPLE_CATEGORY_OFFENDER;
        }else if(TransferConstants.INSTORE_SAMPLE_TYPE_SUSPECT.equals(instoreSampleType)){
            instoreSampleTypeDefault = TransferConstants.NATION_SAMPLE_CATEGORY_SUSPECT;
        }else if(TransferConstants.INSTORE_SAMPLE_TYPE_VICTIM.equals(instoreSampleType)){
            instoreSampleTypeDefault = TransferConstants.NATION_SAMPLE_CATEGORY_VICTIM;
        }else if(TransferConstants.INSTORE_SAMPLE_TYPE_MISSING.equals(instoreSampleType)){
            instoreSampleTypeDefault = TransferConstants.NATION_SAMPLE_CATEGORY_MISSING_VICTIM;
        }else if (TransferConstants.INSTORE_SAMPLE_TYPE_UNKNOWN.equals(instoreSampleType)) {
            instoreSampleTypeDefault = TransferConstants.NATION_SAMPLE_CATEGORY_KNOWN;
        } else if(TransferConstants.INSTORE_SAMPLE_TYPE_SUSPECT_RELATIVE.equals(instoreSampleType)
                || TransferConstants.INSTORE_SAMPLE_TYPE_VICTIM_RELATIVE.equals(instoreSampleType)
                || TransferConstants.INSTORE_SAMPLE_TYPE_MISSING_RELATIVE.equals(instoreSampleType)){
            instoreSampleTypeDefault = TransferConstants.NATION_SAMPLE_CATEGORY_CASE_PERSONS;
        }else if(TransferConstants.INSTORE_SAMPLE_TYPE_YSTR.equals(instoreSampleType)){
            instoreSampleTypeDefault = TransferConstants.NATION_SAMPLE_CATEGORY_EVIDENCE;
        }else if(TransferConstants.INSTORE_SAMPLE_TYPE_UNIDENTIFIED.equals(instoreSampleType)){
            instoreSampleTypeDefault = TransferConstants.NATION_SAMPLE_CATEGORY_OTHER_UNIDENTIFIED_PERSONS;
        }

        return instoreSampleTypeDefault;
    }

    public static String getXckyCallbackUrlByServerNoAndDelegateOrgCode(String delegateOrgCode, TransferNationParamsConfig transferNationParamsConfig){
        if(TransferConstants.LAB_SERVER_NO_BEIJING.equals(transferNationParamsConfig.getLabServerNo())
                || TransferConstants.LAB_SERVER_NO_FYZHX.equals(transferNationParamsConfig.getLabServerNo())){
            if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_DCH)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_DCH.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_DCH))){
                return transferNationParamsConfig.getXckyCallbackURL_DCH();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_XCH)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_XCH.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_XCH))){
                return transferNationParamsConfig.getXckyCallbackURL_XCH();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_CHY)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_CHY.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_CHY))){
                return transferNationParamsConfig.getXckyCallbackURL_CHY();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_FT)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_FT.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_FT))){
                return transferNationParamsConfig.getXckyCallbackURL_FT();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_SHJSH)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_SHJSH.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_SHJSH))){
                return transferNationParamsConfig.getXckyCallbackURL_SHJSH();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_HD)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_HD.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_HD))){
                return transferNationParamsConfig.getXckyCallbackURL_HD();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_MTG)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_MTG.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_MTG))){
                return transferNationParamsConfig.getXckyCallbackURL_MTG();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_FSH)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_FSH.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_FSH))){
                return transferNationParamsConfig.getXckyCallbackURL_FSH();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_TZ)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_TZ.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_TZ))){
                return transferNationParamsConfig.getXckyCallbackURL_TZH();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_SHY)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_SHY.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_SHY))){
                return transferNationParamsConfig.getXckyCallbackURL_SHY();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_CHP)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_CHP.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_CHP))){
                return transferNationParamsConfig.getXckyCallbackURL_CHP();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_DX)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_DX.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_DX))){
                return transferNationParamsConfig.getXckyCallbackURL_DX();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_HR)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_HR.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_HR))){
                return transferNationParamsConfig.getXckyCallbackURL_HR();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_PG)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_PG.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_PG))){
                return transferNationParamsConfig.getXckyCallbackURL_PG();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_MY)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_MY.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_MY))){
                return transferNationParamsConfig.getXckyCallbackURL_MY();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_YQ)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_YQ.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_YQ))){
                return transferNationParamsConfig.getXckyCallbackURL_YQ();
            }

            return transferNationParamsConfig.getXckyCallbackURL_BEIJING();
        }else if(TransferConstants.LAB_SERVER_NO_DCH.equals(transferNationParamsConfig.getLabServerNo())){
            return transferNationParamsConfig.getXckyCallbackURL_DCH();
        }else if(TransferConstants.LAB_SERVER_NO_XCH.equals(transferNationParamsConfig.getLabServerNo())){
            return transferNationParamsConfig.getXckyCallbackURL_XCH();
        }else if(TransferConstants.LAB_SERVER_NO_CHY.equals(transferNationParamsConfig.getLabServerNo())){
            return transferNationParamsConfig.getXckyCallbackURL_CHY();
        }else if(TransferConstants.LAB_SERVER_NO_FT.equals(transferNationParamsConfig.getLabServerNo())){
            return transferNationParamsConfig.getXckyCallbackURL_FT();
        }else if(TransferConstants.LAB_SERVER_NO_SHJSH.equals(transferNationParamsConfig.getLabServerNo())){
            return transferNationParamsConfig.getXckyCallbackURL_SHJSH();
        }else if(TransferConstants.LAB_SERVER_NO_HD.equals(transferNationParamsConfig.getLabServerNo())){
            return transferNationParamsConfig.getXckyCallbackURL_HD();
        }else if(TransferConstants.LAB_SERVER_NO_CHP.equals(transferNationParamsConfig.getLabServerNo())){
            return transferNationParamsConfig.getXckyCallbackURL_CHP();
        }else if(TransferConstants.LAB_SERVER_NO_DX.equals(transferNationParamsConfig.getLabServerNo())){
            return transferNationParamsConfig.getXckyCallbackURL_DX();
        }else if(TransferConstants.LAB_SERVER_NO_TZ.equals(transferNationParamsConfig.getLabServerNo())){
            return transferNationParamsConfig.getXckyCallbackURL_TZH();
        }else if(TransferConstants.LAB_SERVER_NO_SHY.equals(transferNationParamsConfig.getLabServerNo())){
            return transferNationParamsConfig.getXckyCallbackURL_SHY();
        }

        return transferNationParamsConfig.getXckyCallbackURL_BEIJING();
    }
}
