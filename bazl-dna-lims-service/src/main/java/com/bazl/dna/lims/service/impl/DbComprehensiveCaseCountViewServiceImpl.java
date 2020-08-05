package com.bazl.dna.lims.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.lims.model.po.DictItem;
import com.bazl.dna.lims.model.view.DbComprehensiveCaseCountView;
import com.bazl.dna.lims.service.DbComprehensiveCaseCountViewService;
import com.bazl.dna.lims.service.DictItemService;
import com.bazl.dna.lims.dao.DbComprehensiveCaseCountViewMapper;

/**
 * Created by wangliu on 2019/11/11.
 */
@Service
public class DbComprehensiveCaseCountViewServiceImpl extends BaseService implements DbComprehensiveCaseCountViewService {

    @Autowired
    DbComprehensiveCaseCountViewMapper dbComprehensiveCaseCountViewMapper;
    @Autowired
    DictItemService dictItemService;

    @Override
    public List<DbComprehensiveCaseCountView> selectDelagateOrgList() {
        return dbComprehensiveCaseCountViewMapper.selectDelagateOrgList();
    }

    @Override
    public List<DbComprehensiveCaseCountView> selectCountByOrgCodeList(DbComprehensiveCaseCountView dbInvadingWealthCaseView) {
        DbComprehensiveCaseCountView comprehensiveCase = new DbComprehensiveCaseCountView();
        List<DbComprehensiveCaseCountView> comprehensiveCaseCountList = new ArrayList<>();
        if (StringUtils.isNotBlank(dbInvadingWealthCaseView.getCaseType())) {
            DbComprehensiveCaseCountView comprehensiveCaseCnt = new DbComprehensiveCaseCountView();
            //转案件性质（根据页面传的名字转为code）
            comprehensiveCaseCnt = queryCaseType(dbInvadingWealthCaseView);
            //转换案件性质 先查老系统的数据
            comprehensiveCaseCnt = queryCaseTypeLims(comprehensiveCaseCnt);
            comprehensiveCase.setCaseTypeList(comprehensiveCaseCnt.getCaseTypeList());
        }

        //时间查询处理
        if (null != dbInvadingWealthCaseView.getAcceptDatetimeStart()) {
            comprehensiveCase.setAcceptDatetimeStart(dbInvadingWealthCaseView.getAcceptDatetimeStart());
        }
        if (null != dbInvadingWealthCaseView.getAcceptDatetimeEnd()) {
            comprehensiveCase.setAcceptDatetimeEnd(dbInvadingWealthCaseView.getAcceptDatetimeEnd());
        }

        try {
            comprehensiveCase.setDelegateOrg(dbInvadingWealthCaseView.getDelegateOrg());
            //送检案件数
            List<DbComprehensiveCaseCountView> sjCaseList = dbComprehensiveCaseCountViewMapper.selectSjCaseByOrgCodeList(comprehensiveCase);
            for (DbComprehensiveCaseCountView sjCase : sjCaseList) {
                comprehensiveCase.setCaseNumber(comprehensiveCase.getCaseNumber() + Integer.parseInt(sjCase.getCaseCount()));
            }
            //送检物证数
            List<DbComprehensiveCaseCountView> sjSampleList = dbComprehensiveCaseCountViewMapper.selectSjSampleByOrgCodeList(comprehensiveCase);
            for (DbComprehensiveCaseCountView sjSample : sjSampleList) {
                comprehensiveCase.setWzSampleNumber(comprehensiveCase.getWzSampleNumber() + Integer.parseInt(sjSample.getCaseCount()));
            }
            //出具鉴定数
            List<DbComprehensiveCaseCountView> caseBookList = dbComprehensiveCaseCountViewMapper.selectCaseBooksByOrgCodeList(comprehensiveCase);
            for (DbComprehensiveCaseCountView caseBook : caseBookList) {
                comprehensiveCase.setCaseBooksNumber(comprehensiveCase.getCaseBooksNumber() + Integer.parseInt(caseBook.getCaseCount()));
            }

            comprehensiveCase.setDelegateOrgName(dbInvadingWealthCaseView.getDelegateOrgName());
            if (null != comprehensiveCase.getDelegateOrg()) {
                comprehensiveCaseCountList.add(comprehensiveCase);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询lims数据出错！");
        }
        return comprehensiveCaseCountList;
    }

    @Override
    public List<DbComprehensiveCaseCountView> selectCountByOrgCodeSyList(List<DbComprehensiveCaseCountView> dbInvadingWealthCaseViewList, DbComprehensiveCaseCountView dbComprehensiveCaseCountView) {

        if (dbInvadingWealthCaseViewList.size() != 0) {
            for (DbComprehensiveCaseCountView dbInvadingWealthCaseView : dbInvadingWealthCaseViewList) {
                if (dbInvadingWealthCaseView.getDelegateOrg().equals("110113")) {
                    if (null != dbComprehensiveCaseCountView.getCaseType()) {
                        DbComprehensiveCaseCountView comprehensiveCaseCnt = new DbComprehensiveCaseCountView();
                        //案件性质条件处理
                        comprehensiveCaseCnt = queryCaseType(dbComprehensiveCaseCountView);
                        dbInvadingWealthCaseView.setCaseTypeList(comprehensiveCaseCnt.getCaseTypeList());
                    }
                    if (null != dbComprehensiveCaseCountView.getAcceptDatetimeStart()) {
                        dbInvadingWealthCaseView.setAcceptDatetimeStart(dbComprehensiveCaseCountView.getAcceptDatetimeStart());
                    }
                    if (null != dbComprehensiveCaseCountView.getAcceptDatetimeEnd()) {
                        dbInvadingWealthCaseView.setAcceptDatetimeEnd(dbComprehensiveCaseCountView.getAcceptDatetimeEnd());
                    }

                    dbInvadingWealthCaseView.setDelegateOrg("110113000000");

                    try {
                        //送检案件数sy
                        List<DbComprehensiveCaseCountView> sySjCaseList = dbComprehensiveCaseCountViewMapper.selectSySjCaseByOrgCodeList(dbInvadingWealthCaseView);
                        for (DbComprehensiveCaseCountView sjCase : sySjCaseList) {
                            dbInvadingWealthCaseView.setCaseNumber(dbInvadingWealthCaseView.getCaseNumber() + Integer.parseInt(sjCase.getCaseCount()));
                        }
                        //送检物证数sy
                        List<DbComprehensiveCaseCountView> sySjSampleList = dbComprehensiveCaseCountViewMapper.selectSySjSampleByOrgCodeList(dbInvadingWealthCaseView);
                        for (DbComprehensiveCaseCountView sjSample : sySjSampleList) {
                            dbInvadingWealthCaseView.setWzSampleNumber(dbInvadingWealthCaseView.getWzSampleNumber() + Integer.parseInt(sjSample.getCaseCount()));
                        }
                        //出具鉴定数sy
                        List<DbComprehensiveCaseCountView> syCaseBookList = dbComprehensiveCaseCountViewMapper.selectSyCaseBooksByOrgCodeList(dbInvadingWealthCaseView);
                        for (DbComprehensiveCaseCountView caseBook : syCaseBookList) {
                            dbInvadingWealthCaseView.setCaseBooksNumber(dbInvadingWealthCaseView.getCaseBooksNumber() + Integer.parseInt(caseBook.getCaseCount()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("查询sylims数据出错！");
                    }

                }
            }
        }
        return dbInvadingWealthCaseViewList;
    }

    @Override
    public List<DbComprehensiveCaseCountView> selectCaseSendBooksCountList(DbComprehensiveCaseCountView dbInvadingWealthCaseView) {
        DbComprehensiveCaseCountView comprehensiveCase = new DbComprehensiveCaseCountView();
        List<DbComprehensiveCaseCountView> comprehensiveCaseCountList = new ArrayList<>();
        if (StringUtils.isNotBlank(dbInvadingWealthCaseView.getCaseType())) {
            DbComprehensiveCaseCountView comprehensiveCaseCnt = new DbComprehensiveCaseCountView();
            //转案件性质（根据页面传的名字转为code）
            comprehensiveCaseCnt = queryCaseType(dbInvadingWealthCaseView);
            //转换案件性质 先查老系统的数据
            comprehensiveCaseCnt = queryCaseTypeLims(comprehensiveCaseCnt);
            comprehensiveCase.setCaseTypeList(comprehensiveCaseCnt.getCaseTypeList());
        }

        //时间查询处理
        if (null != dbInvadingWealthCaseView.getAcceptDatetimeStart()) {
            comprehensiveCase.setAcceptDatetimeStart(dbInvadingWealthCaseView.getAcceptDatetimeStart());
        }
        if (null != dbInvadingWealthCaseView.getAcceptDatetimeEnd()) {
            comprehensiveCase.setAcceptDatetimeEnd(dbInvadingWealthCaseView.getAcceptDatetimeEnd());
        }

        try {
            comprehensiveCase.setDelegateOrg(dbInvadingWealthCaseView.getDelegateOrg());
            //出具鉴定数
            List<DbComprehensiveCaseCountView> caseBookList = dbComprehensiveCaseCountViewMapper.selectCaseBooksByOrgCodeList(comprehensiveCase);
            for (DbComprehensiveCaseCountView caseBook : caseBookList) {
                comprehensiveCase.setCaseBooksNumber(comprehensiveCase.getCaseBooksNumber() + Integer.parseInt(caseBook.getCaseCount()));
            }

            comprehensiveCase.setDelegateOrgName(dbInvadingWealthCaseView.getDelegateOrgName());
            if (null != comprehensiveCase.getDelegateOrg()) {
                comprehensiveCaseCountList.add(comprehensiveCase);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询lims数据出错！");
        }
        return comprehensiveCaseCountList;
    }

    @Override
    public List<DbComprehensiveCaseCountView> selectSyCaseSendBooksCountList(List<DbComprehensiveCaseCountView> dbInvadingWealthCaseViewList, DbComprehensiveCaseCountView dbComprehensiveCaseCountView) {
        if (dbInvadingWealthCaseViewList.size() != 0) {
            for (DbComprehensiveCaseCountView dbInvadingWealthCaseView : dbInvadingWealthCaseViewList) {
                if (dbInvadingWealthCaseView.getDelegateOrg().equals("110113")) {
                    if (null != dbComprehensiveCaseCountView.getCaseType()) {
                        DbComprehensiveCaseCountView comprehensiveCaseCnt = new DbComprehensiveCaseCountView();
                        //案件性质条件处理
                        comprehensiveCaseCnt = queryCaseType(dbComprehensiveCaseCountView);
                        dbInvadingWealthCaseView.setCaseTypeList(comprehensiveCaseCnt.getCaseTypeList());
                    }
                    if (null != dbComprehensiveCaseCountView.getAcceptDatetimeStart()) {
                        dbInvadingWealthCaseView.setAcceptDatetimeStart(dbComprehensiveCaseCountView.getAcceptDatetimeStart());
                    }
                    if (null != dbComprehensiveCaseCountView.getAcceptDatetimeEnd()) {
                        dbInvadingWealthCaseView.setAcceptDatetimeEnd(dbComprehensiveCaseCountView.getAcceptDatetimeEnd());
                    }

                    dbInvadingWealthCaseView.setDelegateOrg("110113000000");

                    try {
                        //出具鉴定数sy
                        List<DbComprehensiveCaseCountView> syCaseBookList = dbComprehensiveCaseCountViewMapper.selectSyCaseBooksByOrgCodeList(dbInvadingWealthCaseView);
                        for (DbComprehensiveCaseCountView caseBook : syCaseBookList) {
                            dbInvadingWealthCaseView.setCaseBooksNumber(dbInvadingWealthCaseView.getCaseBooksNumber() + Integer.parseInt(caseBook.getCaseCount()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("查询sylims数据出错！");
                    }

                }
            }
        }
        return dbInvadingWealthCaseViewList;
    }

    /**
     * 转换案件性质（名字转code）
     *
     * @param query
     * @return
     */
    private DbComprehensiveCaseCountView queryCaseType(DbComprehensiveCaseCountView query) {
        String caseType = "";
        List<String> list = new ArrayList<>();
        if (StringUtils.isNotBlank(query.getCaseType())) {
            List<DictItem> dictItemList = dictItemService.selectCaseTypeList();

            String[] caseProperty = query.getCaseType().split(",");
            for (int i = 0; i < caseProperty.length; i++) {
                for (DictItem dictItem : dictItemList) {
                    if (caseProperty[i].equals(dictItem.getDictName())) {
                        caseType = dictItem.getDictCode();
                        list.add(caseType);
                    }
                }
            }
            query.setCaseTypeList(list);
        }
        return query;
    }

    /**
     * 转换案件性质（新系统的性质转为老lims的性质）
     *
     * @param query
     * @return
     */
    private DbComprehensiveCaseCountView queryCaseTypeLims(DbComprehensiveCaseCountView query) {
        if (null != query.getCaseTypeList()) {
            for (String list : query.getCaseTypeList()) {
                if (list.equals("01")) {
                    list = "1,010";
                } else if (list.equals("02")) {
                    list = "2,20";
                } else if (list.equals("03")) {
                    list = "3,30";
                } else if (list.equals("03")) {
                    list = "3,30";
                } else if (list.equals("04")) {
                    list = "4,41";
                } else if (list.equals("05")) {
                    list = "5,50";
                } else if (list.equals("07")) {
                    list = "7,70";
                } else if (list.equals("08")) {
                    list = "8";
                } else if (list.equals("09")) {
                    list = "6,60";
                } else if (list.equals("10")) {
                    list = "11,110";
                } else if (list.equals("11")) {
                    list = "10,100";
                } else if (list.equals("12")) {
                    list = "12,120";
                } else if (list.equals("13")) {
                    list = "13,130";
                } else if (list.equals("14")) {
                    list = "14,140";
                } else if (list.equals("15")) {
                    list = "15,150";
                } else if (list.equals("16")) {
                    list = "37";
                } else if (list.equals("17")) {
                    list = "132";
                } else if (list.equals("18")) {
                    list = "32";
                } else if (list.equals("19")) {
                    list = " ";
                } else if (list.equals("20")) {
                    list = "9,90";
                } else if (list.equals("21")) {
                    list = "19,190";
                } else if (list.equals("22")) {
                    list = "42,4";
                } else if (list.equals("23")) {
                    list = "192,1920";
                } else if (list.equals("24")) {
                    list = "13,13-1,130,3,30,14,140,15,150,132,32,312,31";
                } else if (list.equals("26")) {
                    list = "312";
                } else if (list.equals("27")) {
                    list = "31";
                }
            }
        }

        return query;
    }
}
