package com.bazl.dna.mix.service;

import java.util.List;
import java.util.Map;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.mix.model.po.MobileNews;

public interface MobileNewsService {

	int deleteByPrimaryKey(String id);

    int insert(MobileNews record);

    MobileNews selectByPrimaryKey(String id);

    List<MobileNews> selectAll();

    int updateByPrimaryKey(MobileNews record);

    Map<String, Object> getUserMobileNumber(String id);

    List<MobileNews> selectPageAll(MobileNews mobileNews, PageInfo pageInfo);

    int selectPageCount(MobileNews mobileNews);

    int selectCount(MobileNews mobileNews);

    List<MobileNews> selectPcList(MobileNews mobileNews);

    List<MobileNews> getUserNewsList(MobileNews mobileNews);

    int selectByMobileFlag(String userid);
}
