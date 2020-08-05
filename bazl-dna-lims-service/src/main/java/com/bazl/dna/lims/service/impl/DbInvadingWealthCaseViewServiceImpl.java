package com.bazl.dna.lims.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.lims.common.Constants;
import com.bazl.dna.lims.model.view.DbInvadingWealthCaseView;
import com.bazl.dna.lims.service.DbInvadingWealthCaseViewService;
import com.bazl.dna.lims.dao.DbInvadingWealthCaseViewMapper;

/**
 * Created by wangliu on 2019/11/7.
 */
@Service
public class DbInvadingWealthCaseViewServiceImpl extends BaseService implements DbInvadingWealthCaseViewService {

    @Autowired
    DbInvadingWealthCaseViewMapper dbInvadingWealthCaseViewMapper;

    @Override
    public List<DbInvadingWealthCaseView> selectDelagateOrgList() {
        return dbInvadingWealthCaseViewMapper.selectDelagateOrgList();
    }

    @Override
    public List<DbInvadingWealthCaseView> sjCaseCountList(DbInvadingWealthCaseView query) {

        List<DbInvadingWealthCaseView> dbCaseCountList = new ArrayList<>();

        for (String limsCaseType : Constants.limsCaseType) {
            DbInvadingWealthCaseView dbCaseView = new DbInvadingWealthCaseView();
            dbCaseView.setDelegateOrg(query.getDelegateOrg());
            dbCaseView.setCaseType(limsCaseType.trim());
            dbCaseView.setCaseTypeList(Arrays.asList(limsCaseType.split(",")));

            dbCaseView.setAcceptDatetimeStart(query.getAcceptDatetimeStart());
            dbCaseView.setAcceptDatetimeEnd(query.getAcceptDatetimeEnd());

            List<DbInvadingWealthCaseView> sjCaseList = dbInvadingWealthCaseViewMapper.selectSjCaseByOrgCodeAndCaseTypeList(dbCaseView);
            DbInvadingWealthCaseView dbInvadingWealthCaseView = new DbInvadingWealthCaseView();
            /**查询完lims库的数据后转换案件性质方便去alims查**/
            String newLimsCaseType = changeCaseType(limsCaseType);

            dbInvadingWealthCaseView.setDelegateOrg(query.getDelegateOrg());
            dbInvadingWealthCaseView.setCaseType(newLimsCaseType);
            dbInvadingWealthCaseView.setDelegateOrgName(query.getDelegateOrgName());
            for (DbInvadingWealthCaseView sjCase : sjCaseList) {
                dbInvadingWealthCaseView.setNumber(dbInvadingWealthCaseView.getNumber() + Integer.parseInt(sjCase.getCaseCount()));
            }
            if (null != dbInvadingWealthCaseView.getCaseType()) {
                dbCaseCountList.add(dbInvadingWealthCaseView);
            }
        }

        DbInvadingWealthCaseView dbCaseView = new DbInvadingWealthCaseView();
        for (DbInvadingWealthCaseView dbCaseCount : dbCaseCountList) {
            if (dbCaseCount.getDelegateOrg().equals("110113")) {
                dbCaseView = new DbInvadingWealthCaseView();
                dbCaseView.setCaseTypeList(Arrays.asList(dbCaseCount.getCaseType().split(",")));
                dbCaseView.setDelegateOrg("110113000000");
                dbCaseView.setAcceptDatetimeStart(query.getAcceptDatetimeStart());
                dbCaseView.setAcceptDatetimeEnd(query.getAcceptDatetimeEnd());
                List<DbInvadingWealthCaseView> sjCaseList = dbInvadingWealthCaseViewMapper.selectSySjCaseByOrgCodeAndCaseTypeList(dbCaseView);
                dbCaseView.setDelegateOrgName("顺义分局");

                for (DbInvadingWealthCaseView sjCase : sjCaseList) {
                    dbCaseView.setNumber(dbCaseView.getNumber() + Integer.parseInt(sjCase.getCaseCount()));
                }
            }
        }

        if (null != dbCaseView.getCaseType()) {
            dbCaseCountList.add(dbCaseView);
        }

        return dbCaseCountList;
    }

    @Override
    public List<DbInvadingWealthCaseView> rkWzCaseCountList(DbInvadingWealthCaseView query) {

        List<DbInvadingWealthCaseView> dbCaseCountList = new ArrayList<>();

        for (String limsCaseType : Constants.limsCaseType) {
            DbInvadingWealthCaseView dbCaseView = new DbInvadingWealthCaseView();
            dbCaseView.setDelegateOrg(query.getDelegateOrg());
            dbCaseView.setCaseType(limsCaseType.trim());
            dbCaseView.setCaseTypeList(Arrays.asList(limsCaseType.split(",")));

            dbCaseView.setAcceptDatetimeStart(query.getAcceptDatetimeStart());
            dbCaseView.setAcceptDatetimeEnd(query.getAcceptDatetimeEnd());

            List<DbInvadingWealthCaseView> sjCaseList = dbInvadingWealthCaseViewMapper.selectRkWzCaseByOrgCodeAndCaseTypeList(dbCaseView);
            DbInvadingWealthCaseView dbInvadingWealthCaseView = new DbInvadingWealthCaseView();
            /**查询完lims库的数据后转换案件性质方便去alims查**/
            String newLimsCaseType = changeCaseType(limsCaseType);

            dbInvadingWealthCaseView.setDelegateOrg(query.getDelegateOrg());
            dbInvadingWealthCaseView.setCaseType(newLimsCaseType);
            dbInvadingWealthCaseView.setDelegateOrgName(query.getDelegateOrgName());
            for (DbInvadingWealthCaseView sjCase : sjCaseList) {
                dbInvadingWealthCaseView.setNumber(dbInvadingWealthCaseView.getNumber() + Integer.parseInt(sjCase.getCaseCount()));
            }
            if (null != dbInvadingWealthCaseView.getCaseType()) {
                dbCaseCountList.add(dbInvadingWealthCaseView);
            }
        }

        DbInvadingWealthCaseView dbCaseView = new DbInvadingWealthCaseView();
        for (DbInvadingWealthCaseView dbCaseCount : dbCaseCountList) {
            if (dbCaseCount.getDelegateOrg().equals("110113")) {
                dbCaseView = new DbInvadingWealthCaseView();
                dbCaseView.setCaseTypeList(Arrays.asList(dbCaseCount.getCaseType().split(",")));
                dbCaseView.setDelegateOrg("110113000000");
                dbCaseView.setAcceptDatetimeStart(query.getAcceptDatetimeStart());
                dbCaseView.setAcceptDatetimeEnd(query.getAcceptDatetimeEnd());
                List<DbInvadingWealthCaseView> sjCaseList = dbInvadingWealthCaseViewMapper.selectSyRkWzCaseByOrgCodeAndCaseTypeList(dbCaseView);
                dbCaseView.setDelegateOrgName("顺义分局");

                for (DbInvadingWealthCaseView sjCase : sjCaseList) {
                    dbCaseView.setNumber(dbCaseView.getNumber() + Integer.parseInt(sjCase.getCaseCount()));
                }
            }
        }

        if (null != dbCaseView.getCaseType()) {
            dbCaseCountList.add(dbCaseView);
        }

        return dbCaseCountList;
    }

    @Override
    public List<DbInvadingWealthCaseView> sjSampleCountList(DbInvadingWealthCaseView query) {

        List<DbInvadingWealthCaseView> dbCaseCountList = new ArrayList<>();

        for (String limsCaseType : Constants.limsCaseType) {
            DbInvadingWealthCaseView dbCaseView = new DbInvadingWealthCaseView();
            dbCaseView.setDelegateOrg(query.getDelegateOrg());
            dbCaseView.setCaseType(limsCaseType.trim());
            dbCaseView.setCaseTypeList(Arrays.asList(limsCaseType.split(",")));

            dbCaseView.setAcceptDatetimeStart(query.getAcceptDatetimeStart());
            dbCaseView.setAcceptDatetimeEnd(query.getAcceptDatetimeEnd());

            List<DbInvadingWealthCaseView> sjCaseList = dbInvadingWealthCaseViewMapper.selectSjSampleByOrgCodeAndCaseTypeList(dbCaseView);
            DbInvadingWealthCaseView dbInvadingWealthCaseView = new DbInvadingWealthCaseView();
            /**查询完lims库的数据后转换案件性质方便去alims查**/
            String newLimsCaseType = changeCaseType(limsCaseType);

            dbInvadingWealthCaseView.setDelegateOrg(query.getDelegateOrg());
            dbInvadingWealthCaseView.setCaseType(newLimsCaseType);
            dbInvadingWealthCaseView.setDelegateOrgName(query.getDelegateOrgName());
            for (DbInvadingWealthCaseView sjCase : sjCaseList) {
                dbInvadingWealthCaseView.setNumber(dbInvadingWealthCaseView.getNumber() + Integer.parseInt(sjCase.getCaseCount()));
            }
            if (null != dbInvadingWealthCaseView.getCaseType()) {
                dbCaseCountList.add(dbInvadingWealthCaseView);
            }
        }

        DbInvadingWealthCaseView dbCaseView = new DbInvadingWealthCaseView();
        for (DbInvadingWealthCaseView dbCaseCount : dbCaseCountList) {
            if (dbCaseCount.getDelegateOrg().equals("110113")) {
                dbCaseView = new DbInvadingWealthCaseView();
                dbCaseView.setCaseTypeList(Arrays.asList(dbCaseCount.getCaseType().split(",")));
                dbCaseView.setDelegateOrg("110113000000");
                dbCaseView.setAcceptDatetimeStart(query.getAcceptDatetimeStart());
                dbCaseView.setAcceptDatetimeEnd(query.getAcceptDatetimeEnd());
                List<DbInvadingWealthCaseView> sjCaseList = dbInvadingWealthCaseViewMapper.selectSySjSampleByOrgCodeAndCaseTypeList(dbCaseView);
                dbCaseView.setDelegateOrgName("顺义分局");

                for (DbInvadingWealthCaseView sjCase : sjCaseList) {
                    dbCaseView.setNumber(dbCaseView.getNumber() + Integer.parseInt(sjCase.getCaseCount()));
                }
            }
        }

        if (null != dbCaseView.getCaseType()) {
            dbCaseCountList.add(dbCaseView);
        }

        return dbCaseCountList;
    }

    @Override
    public List<DbInvadingWealthCaseView> rkSampleCountList(DbInvadingWealthCaseView query) {

        List<DbInvadingWealthCaseView> dbCaseCountList = new ArrayList<>();

        for (String limsCaseType : Constants.limsCaseType) {
            DbInvadingWealthCaseView dbCaseView = new DbInvadingWealthCaseView();
            dbCaseView.setDelegateOrg(query.getDelegateOrg());
            dbCaseView.setCaseType(limsCaseType.trim());
            dbCaseView.setCaseTypeList(Arrays.asList(limsCaseType.split(",")));

            dbCaseView.setAcceptDatetimeStart(query.getAcceptDatetimeStart());
            dbCaseView.setAcceptDatetimeEnd(query.getAcceptDatetimeEnd());

            List<DbInvadingWealthCaseView> sjCaseList = dbInvadingWealthCaseViewMapper.selectRkSampleByOrgCodeAndCaseTypeList(dbCaseView);
            DbInvadingWealthCaseView dbInvadingWealthCaseView = new DbInvadingWealthCaseView();
            /**查询完lims库的数据后转换案件性质方便去alims查**/
            String newLimsCaseType = changeCaseType(limsCaseType);

            dbInvadingWealthCaseView.setDelegateOrg(query.getDelegateOrg());
            dbInvadingWealthCaseView.setCaseType(newLimsCaseType);
            dbInvadingWealthCaseView.setDelegateOrgName(query.getDelegateOrgName());
            for (DbInvadingWealthCaseView sjCase : sjCaseList) {
                dbInvadingWealthCaseView.setNumber(dbInvadingWealthCaseView.getNumber() + Integer.parseInt(sjCase.getCaseCount()));
            }
            if (null != dbInvadingWealthCaseView.getCaseType()) {
                dbCaseCountList.add(dbInvadingWealthCaseView);
            }
        }

        DbInvadingWealthCaseView dbCaseView = new DbInvadingWealthCaseView();
        for (DbInvadingWealthCaseView dbCaseCount : dbCaseCountList) {
            if (dbCaseCount.getDelegateOrg().equals("110113")) {
                dbCaseView = new DbInvadingWealthCaseView();
                dbCaseView.setCaseTypeList(Arrays.asList(dbCaseCount.getCaseType().split(",")));
                dbCaseView.setDelegateOrg("110113000000");
                dbCaseView.setAcceptDatetimeStart(query.getAcceptDatetimeStart());
                dbCaseView.setAcceptDatetimeEnd(query.getAcceptDatetimeEnd());
                List<DbInvadingWealthCaseView> sjCaseList = dbInvadingWealthCaseViewMapper.selectSyRkSampleByOrgCodeAndCaseTypeList(dbCaseView);
                dbCaseView.setDelegateOrgName("顺义分局");

                for (DbInvadingWealthCaseView sjCase : sjCaseList) {
                    dbCaseView.setNumber(dbCaseView.getNumber() + Integer.parseInt(sjCase.getCaseCount()));
                }
            }
        }
        if (null != dbCaseView.getCaseType()) {
            dbCaseCountList.add(dbCaseView);
        }
        return dbCaseCountList;
    }

    @Override
    public List<DbInvadingWealthCaseView> sjCaseCountByYearList(DbInvadingWealthCaseView query) {

        List<DbInvadingWealthCaseView> dbCaseCountList = new ArrayList<>();

        DbInvadingWealthCaseView dbCaseView = new DbInvadingWealthCaseView();
        dbCaseView.setDelegateOrg(query.getDelegateOrg());
        dbCaseView.setAcceptDatetime(query.getAcceptDatetime());
        dbCaseView.setDelegateOrgName(query.getDelegateOrgName());
        List<DbInvadingWealthCaseView> sjCaseList = dbInvadingWealthCaseViewMapper.selectSjCaseCountByOrgCodeAndYear(dbCaseView);

        for (DbInvadingWealthCaseView sjCase : sjCaseList) {
            dbCaseView.setNumber(dbCaseView.getNumber() + Integer.parseInt(sjCase.getCaseCount()));
        }

        dbCaseCountList.add(dbCaseView);

        return dbCaseCountList;
    }

    @Override
    public List<DbInvadingWealthCaseView> sySjCaseCountByYearList(List<DbInvadingWealthCaseView> dbCaseCountList) {

        for (DbInvadingWealthCaseView dbCaseCount : dbCaseCountList) {
            if (dbCaseCount.getDelegateOrg().equals("110113")) {
                dbCaseCount.setDelegateOrg("110113000000");
                dbCaseCount.setAcceptDatetime(dbCaseCount.getAcceptDatetime());
                List<DbInvadingWealthCaseView> sjCaseList = dbInvadingWealthCaseViewMapper.selectSySjCaseCountByOrgCodeAndYear(dbCaseCount);
                dbCaseCount.setDelegateOrgName("顺义分局");
                for (DbInvadingWealthCaseView sjCase : sjCaseList) {
                    dbCaseCount.setNumber(dbCaseCount.getNumber() + Integer.parseInt(sjCase.getCaseCount()));
                }
            }
        }

        return dbCaseCountList;
    }

    @Override
    public List<DbInvadingWealthCaseView> rkWzCaseCountByYearList(DbInvadingWealthCaseView query) {
        List<DbInvadingWealthCaseView> dbCaseCountList = new ArrayList<>();

        DbInvadingWealthCaseView dbCaseView = new DbInvadingWealthCaseView();
        dbCaseView.setDelegateOrg(query.getDelegateOrg());
        dbCaseView.setAcceptDatetime(query.getAcceptDatetime());
        dbCaseView.setDelegateOrgName(query.getDelegateOrgName());
        List<DbInvadingWealthCaseView> sjCaseList = dbInvadingWealthCaseViewMapper.selectRkWzCaseByOrgCodeAndYear(dbCaseView);

        for (DbInvadingWealthCaseView sjCase : sjCaseList) {
            dbCaseView.setNumber(dbCaseView.getNumber() + Integer.parseInt(sjCase.getCaseCount()));
        }

        dbCaseCountList.add(dbCaseView);

        return dbCaseCountList;
    }


    @Override
    public List<DbInvadingWealthCaseView> syRkWzCaseCountByYearList(List<DbInvadingWealthCaseView> dbCaseCountList) {
        for (DbInvadingWealthCaseView dbCaseCount : dbCaseCountList) {
            if (dbCaseCount.getDelegateOrg().equals("110113")) {
                dbCaseCount.setDelegateOrg("110113000000");
                dbCaseCount.setAcceptDatetime(dbCaseCount.getAcceptDatetime());
                List<DbInvadingWealthCaseView> sjCaseList = dbInvadingWealthCaseViewMapper.selectSyRkWzCaseByOrgCodeAndYear(dbCaseCount);
                dbCaseCount.setDelegateOrgName("顺义分局");
                for (DbInvadingWealthCaseView sjCase : sjCaseList) {
                    dbCaseCount.setNumber(dbCaseCount.getNumber() + Integer.parseInt(sjCase.getCaseCount()));
                }
            }
        }
        return dbCaseCountList;
    }

    @Override
    public List<DbInvadingWealthCaseView> rkSampleCountByYearList(DbInvadingWealthCaseView query) {
        List<DbInvadingWealthCaseView> dbCaseCountList = new ArrayList<>();

        DbInvadingWealthCaseView dbCaseView = new DbInvadingWealthCaseView();
        dbCaseView.setDelegateOrg(query.getDelegateOrg());
        dbCaseView.setAcceptDatetime(query.getAcceptDatetime());
        dbCaseView.setDelegateOrgName(query.getDelegateOrgName());
        List<DbInvadingWealthCaseView> sjCaseList = dbInvadingWealthCaseViewMapper.selectRkSampleByOrgCodeAndYear(dbCaseView);

        for (DbInvadingWealthCaseView sjCase : sjCaseList) {
            dbCaseView.setNumber(dbCaseView.getNumber() + Integer.parseInt(sjCase.getCaseCount()));
        }

        dbCaseCountList.add(dbCaseView);

        return dbCaseCountList;
    }

    @Override
    public List<DbInvadingWealthCaseView> syRkSampleCountByYearList(List<DbInvadingWealthCaseView> dbCaseCountList) {
        for (DbInvadingWealthCaseView dbCaseCount : dbCaseCountList) {
            if (dbCaseCount.getDelegateOrg().equals("110113")) {
                dbCaseCount.setDelegateOrg("110113000000");
                dbCaseCount.setAcceptDatetime(dbCaseCount.getAcceptDatetime());
                List<DbInvadingWealthCaseView> sjCaseList = dbInvadingWealthCaseViewMapper.selectSyRkSampleByOrgCodeAndYear(dbCaseCount);
                dbCaseCount.setDelegateOrgName("顺义分局");
                for (DbInvadingWealthCaseView sjCase : sjCaseList) {
                    dbCaseCount.setNumber(dbCaseCount.getNumber() + Integer.parseInt(sjCase.getCaseCount()));
                }
            }
        }
        return dbCaseCountList;
    }

    @Override
    public List<DbInvadingWealthCaseView> sjSampleCountByYearList(DbInvadingWealthCaseView query) {
        List<DbInvadingWealthCaseView> dbCaseCountList = new ArrayList<>();

        DbInvadingWealthCaseView dbCaseView = new DbInvadingWealthCaseView();
        dbCaseView.setDelegateOrg(query.getDelegateOrg());
        dbCaseView.setAcceptDatetime(query.getAcceptDatetime());
        dbCaseView.setDelegateOrgName(query.getDelegateOrgName());
        List<DbInvadingWealthCaseView> sjCaseList = dbInvadingWealthCaseViewMapper.selectSjSampleByOrgCodeAndYear(dbCaseView);

        for (DbInvadingWealthCaseView sjCase : sjCaseList) {
            dbCaseView.setNumber(dbCaseView.getNumber() + Integer.parseInt(sjCase.getCaseCount()));
        }

        dbCaseCountList.add(dbCaseView);

        return dbCaseCountList;
    }

    @Override
    public List<DbInvadingWealthCaseView> sySjSampleCountByYearList(List<DbInvadingWealthCaseView> dbCaseCountList) {
        for (DbInvadingWealthCaseView dbCaseCount : dbCaseCountList) {
            if (dbCaseCount.getDelegateOrg().equals("110113")) {
                dbCaseCount.setDelegateOrg("110113000000");
                dbCaseCount.setAcceptDatetime(dbCaseCount.getAcceptDatetime());
                List<DbInvadingWealthCaseView> sjCaseList = dbInvadingWealthCaseViewMapper.selectSySjSampleByOrgCodeAndYear(dbCaseCount);
                dbCaseCount.setDelegateOrgName("顺义分局");
                for (DbInvadingWealthCaseView sjCase : sjCaseList) {
                    dbCaseCount.setNumber(dbCaseCount.getNumber() + Integer.parseInt(sjCase.getCaseCount()));
                }
            }
        }
        return dbCaseCountList;
    }

    @Override
    public List<DbInvadingWealthCaseView> selectSjCaseByYearAndCaseType(DbInvadingWealthCaseView query) {
        List<DbInvadingWealthCaseView> dbCaseCountList = new ArrayList<>();

        for (String limsCaseType : Constants.limsCaseType) {
            DbInvadingWealthCaseView dbCaseView = new DbInvadingWealthCaseView();
            dbCaseView.setCaseTypeList(Arrays.asList(limsCaseType.split(",")));
            dbCaseView.setAcceptDatetime(query.getAcceptDatetime());
            List<DbInvadingWealthCaseView> sjCaseList = dbInvadingWealthCaseViewMapper.selectSjCaseByYearAndCaseTypeList(dbCaseView);
            DbInvadingWealthCaseView dbInvadingWealthCaseView = new DbInvadingWealthCaseView();
            /**查询完lims库的数据后转换案件性质方便去alims查**/
            String newLimsCaseType = changeCaseType(limsCaseType);

            dbInvadingWealthCaseView.setCaseType(newLimsCaseType);
            for (DbInvadingWealthCaseView sjCase : sjCaseList) {
                dbInvadingWealthCaseView.setNumber(dbInvadingWealthCaseView.getNumber() + Integer.parseInt(sjCase.getCaseCount()));
            }
            if (null != dbInvadingWealthCaseView.getCaseType()) {
                dbCaseCountList.add(dbInvadingWealthCaseView);
            }
        }

        DbInvadingWealthCaseView dbCaseView = new DbInvadingWealthCaseView();
        for (DbInvadingWealthCaseView dbCaseCount : dbCaseCountList) {
            dbCaseView = new DbInvadingWealthCaseView();
            dbCaseView.setCaseTypeList(Arrays.asList(dbCaseCount.getCaseType().split(",")));
            dbCaseView.setAcceptDatetime(query.getAcceptDatetime());
            List<DbInvadingWealthCaseView> sjCaseList = dbInvadingWealthCaseViewMapper.selectSySjCaseByYeareAndCaseTypeList(dbCaseView);

            for (DbInvadingWealthCaseView sjCase : sjCaseList) {
                dbCaseView.setNumber(dbCaseView.getNumber() + Integer.parseInt(sjCase.getCaseCount()));
            }

        }

        if (null != dbCaseView.getCaseType()) {
            dbCaseCountList.add(dbCaseView);
        }
        return dbCaseCountList;
    }

    @Override
    public List<DbInvadingWealthCaseView> selectrkWzCaseByYearAndCaseType(DbInvadingWealthCaseView query) {
        List<DbInvadingWealthCaseView> dbCaseCountList = new ArrayList<>();

        for (String limsCaseType : Constants.limsCaseType) {
            DbInvadingWealthCaseView dbCaseView = new DbInvadingWealthCaseView();
            dbCaseView.setCaseTypeList(Arrays.asList(limsCaseType.split(",")));
            dbCaseView.setAcceptDatetime(query.getAcceptDatetime());
            List<DbInvadingWealthCaseView> sjCaseList = dbInvadingWealthCaseViewMapper.selectRkWzCaseByYearAndCaseTypeList(dbCaseView);
            DbInvadingWealthCaseView dbInvadingWealthCaseView = new DbInvadingWealthCaseView();
            /**查询完lims库的数据后转换案件性质方便去alims查**/
            String newLimsCaseType = changeCaseType(limsCaseType);

            dbInvadingWealthCaseView.setCaseType(newLimsCaseType);
            for (DbInvadingWealthCaseView sjCase : sjCaseList) {
                dbInvadingWealthCaseView.setNumber(dbInvadingWealthCaseView.getNumber() + Integer.parseInt(sjCase.getCaseCount()));
            }
            if (null != dbInvadingWealthCaseView.getCaseType()) {
                dbCaseCountList.add(dbInvadingWealthCaseView);
            }
        }

        DbInvadingWealthCaseView dbCaseView = new DbInvadingWealthCaseView();
        for (DbInvadingWealthCaseView dbCaseCount : dbCaseCountList) {
            dbCaseView = new DbInvadingWealthCaseView();
            dbCaseView.setCaseTypeList(Arrays.asList(dbCaseCount.getCaseType().split(",")));
            dbCaseView.setAcceptDatetime(query.getAcceptDatetime());
            List<DbInvadingWealthCaseView> sjCaseList = dbInvadingWealthCaseViewMapper.selectSyRkWzCaseByYearAndCaseTypeList(dbCaseView);

            for (DbInvadingWealthCaseView sjCase : sjCaseList) {
                dbCaseView.setNumber(dbCaseView.getNumber() + Integer.parseInt(sjCase.getCaseCount()));
            }

        }

        if (null != dbCaseView.getCaseType()) {
            dbCaseCountList.add(dbCaseView);
        }
        return dbCaseCountList;
    }

    @Override
    public List<DbInvadingWealthCaseView> selectsjSampleByYearAndCaseType(DbInvadingWealthCaseView query) {
        List<DbInvadingWealthCaseView> dbCaseCountList = new ArrayList<>();

        for (String limsCaseType : Constants.limsCaseType) {
            DbInvadingWealthCaseView dbCaseView = new DbInvadingWealthCaseView();
            dbCaseView.setCaseTypeList(Arrays.asList(limsCaseType.split(",")));
            dbCaseView.setAcceptDatetime(query.getAcceptDatetime());
            List<DbInvadingWealthCaseView> sjCaseList = dbInvadingWealthCaseViewMapper.selectSjSampleByYearAndCaseTypeList(dbCaseView);
            DbInvadingWealthCaseView dbInvadingWealthCaseView = new DbInvadingWealthCaseView();
            /**查询完lims库的数据后转换案件性质方便去alims查**/
            String newLimsCaseType = changeCaseType(limsCaseType);

            dbInvadingWealthCaseView.setCaseType(newLimsCaseType);
            for (DbInvadingWealthCaseView sjCase : sjCaseList) {
                dbInvadingWealthCaseView.setNumber(dbInvadingWealthCaseView.getNumber() + Integer.parseInt(sjCase.getCaseCount()));
            }
            if (null != dbInvadingWealthCaseView.getCaseType()) {
                dbCaseCountList.add(dbInvadingWealthCaseView);
            }
        }

        DbInvadingWealthCaseView dbCaseView = new DbInvadingWealthCaseView();
        for (DbInvadingWealthCaseView dbCaseCount : dbCaseCountList) {
            dbCaseView = new DbInvadingWealthCaseView();
            dbCaseView.setCaseTypeList(Arrays.asList(dbCaseCount.getCaseType().split(",")));
            dbCaseView.setAcceptDatetime(query.getAcceptDatetime());
            List<DbInvadingWealthCaseView> sjCaseList = dbInvadingWealthCaseViewMapper.selectSySjSampleByYearAndCaseTypeList(dbCaseView);

            for (DbInvadingWealthCaseView sjCase : sjCaseList) {
                dbCaseView.setNumber(dbCaseView.getNumber() + Integer.parseInt(sjCase.getCaseCount()));
            }
        }

        if (null != dbCaseView.getCaseType()) {
            dbCaseCountList.add(dbCaseView);
        }
        return dbCaseCountList;
    }

    @Override
    public List<DbInvadingWealthCaseView> selectrkSampleByYearAndCaseType(DbInvadingWealthCaseView query) {
        List<DbInvadingWealthCaseView> dbCaseCountList = new ArrayList<>();

        for (String limsCaseType : Constants.limsCaseType) {
            DbInvadingWealthCaseView dbCaseView = new DbInvadingWealthCaseView();
            dbCaseView.setCaseTypeList(Arrays.asList(limsCaseType.split(",")));
            dbCaseView.setAcceptDatetime(query.getAcceptDatetime());
            List<DbInvadingWealthCaseView> sjCaseList = dbInvadingWealthCaseViewMapper.selectRkSampleByYearAndCaseTypeList(dbCaseView);
            DbInvadingWealthCaseView dbInvadingWealthCaseView = new DbInvadingWealthCaseView();
            /**查询完lims库的数据后转换案件性质方便去alims查**/
            String newLimsCaseType = changeCaseType(limsCaseType);

            dbInvadingWealthCaseView.setCaseType(newLimsCaseType);
            for (DbInvadingWealthCaseView sjCase : sjCaseList) {
                dbInvadingWealthCaseView.setNumber(dbInvadingWealthCaseView.getNumber() + Integer.parseInt(sjCase.getCaseCount()));
            }
            if (null != dbInvadingWealthCaseView.getCaseType()) {
                dbCaseCountList.add(dbInvadingWealthCaseView);
            }
        }

        DbInvadingWealthCaseView dbCaseView = new DbInvadingWealthCaseView();
        for (DbInvadingWealthCaseView dbCaseCount : dbCaseCountList) {
            dbCaseView = new DbInvadingWealthCaseView();
            dbCaseView.setCaseTypeList(Arrays.asList(dbCaseCount.getCaseType().split(",")));
            dbCaseView.setAcceptDatetime(query.getAcceptDatetime());
            List<DbInvadingWealthCaseView> sjCaseList = dbInvadingWealthCaseViewMapper.selectSyRkSampleByYearAndCaseTypeList(dbCaseView);

            for (DbInvadingWealthCaseView sjCase : sjCaseList) {
                dbCaseView.setNumber(dbCaseView.getNumber() + Integer.parseInt(sjCase.getCaseCount()));
            }
        }

        if (null != dbCaseView.getCaseType()) {
            dbCaseCountList.add(dbCaseView);
        }
        return dbCaseCountList;
    }

    /**
     * 转换案件性质
     *
     * @param caseType
     * @return
     */
    private String changeCaseType(String caseType) {
        if (caseType.equals("13")) {
            caseType = "13,13-1,17";
        } else if (caseType.equals("4")) {
            caseType = "04,22";
        } else if (caseType.equals("16,37")) {
            caseType = "16";
        } else if (caseType.equals("3")) {
            caseType = "03";
        } else if (caseType.equals("19,190")) {
            caseType = "21";
        }
        return caseType;
    }

}
