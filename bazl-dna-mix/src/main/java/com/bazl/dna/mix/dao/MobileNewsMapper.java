package com.bazl.dna.mix.dao;


import java.util.List;
import java.util.Map;

import com.bazl.dna.mix.model.po.MobileNews;
import org.springframework.stereotype.Repository;

@Repository
public interface MobileNewsMapper {
    int deleteByPrimaryKey(String id);

    int insert(MobileNews record);

    MobileNews selectByPrimaryKey(String id);

    List<MobileNews> selectAll();

    int updateByPrimaryKey(MobileNews record);

    Map<String, Object> getUserMobileNumber(String id);

    List<MobileNews> selectPageAll(MobileNews mobileNews);

    int selectPageCount(MobileNews mobileNews);

    int selectCount(MobileNews mobileNews);

    List<MobileNews> selectPcList(MobileNews mobileNews);

    List<MobileNews> getUserNewsList(MobileNews mobileNews);

    int selectByMobileFlag(String userid);
}