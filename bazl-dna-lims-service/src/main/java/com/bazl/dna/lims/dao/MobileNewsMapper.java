package com.bazl.dna.lims.dao;

import java.util.List;
import java.util.Map;

import com.bazl.dna.lims.model.po.MobileNews;

public interface MobileNewsMapper {
    int deleteByPrimaryKey(String id);

    int insert(MobileNews record);

    MobileNews selectByPrimaryKey(String id);

    List<MobileNews> selectAll();

    int updateByPrimaryKey(MobileNews record);

    Map<String, Object> getUserMobileNewsNumber(String id);

    List<MobileNews> selectPageAll(MobileNews mobileNews);

    int selectPageCount (MobileNews mobileNews);

    int selectCount (MobileNews mobileNews);

    List<MobileNews> selectPcList(MobileNews mobileNews);

    List<MobileNews> getUserNewsList(MobileNews mobileNews);
}