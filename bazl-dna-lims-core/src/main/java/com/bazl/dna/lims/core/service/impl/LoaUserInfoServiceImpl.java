package com.bazl.dna.lims.core.service.impl;

import com.bazl.dna.lims.core.common.Constants;
import com.bazl.dna.lims.core.dao.LoaUserInfoMapper;
import com.bazl.dna.lims.core.model.po.AmPersonalInfo;
import com.bazl.dna.lims.core.model.po.LoaUserInfo;
import com.bazl.dna.lims.core.service.LoaUserInfoService;
import com.bazl.dna.lims.core.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sun on 2018/12/20.
 */
@Service
public class LoaUserInfoServiceImpl extends BaseService implements LoaUserInfoService {

    @Autowired
    LoaUserInfoMapper loaUserInfoMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public LoaUserInfo selectByUserNameAndAreaCode(String loginName, String areaCode) {
        return loaUserInfoMapper.selectByUserNameAndAreaCode(loginName,areaCode);
//        String userkey = loginName  + ":" + areaCode;
//        LoaUserInfo user = (LoaUserInfo) redisTemplate.boundHashOps("loausers").get(userkey);
//        if(user==null){
//            System.out.println("没有此用户的数据，将此用户的数据放入缓存");
//            user = loaUserInfoMapper.selectByUserNameAndAreaCode(loginName,areaCode);
//            redisTemplate.boundHashOps("loausers").put(userkey,user);
//        }else {
//            System.out.println("从缓从中读取用户的数据");
//        }
//
//        return user;
    }


    @Override
    public LoaUserInfo selectByUserNameOnly(String loginName) {
        List<LoaUserInfo> loaUserInfoList = loaUserInfoMapper.selectByUserNameOnly(loginName);
        if(ListUtils.isNotNullAndEmptyList(loaUserInfoList)){
            loaUserInfoList = loaUserInfoList.stream().filter(user -> user.getActiveFlag().equals(Constants.FLAG_TRUE)).collect(Collectors.toList());
            return loaUserInfoList.get(0);
        }
        return null;

//        String userkey = loginName  + ":" + areaCode;
//        LoaUserInfo user = (LoaUserInfo) redisTemplate.boundHashOps("loausers").get(userkey);
//        if(user==null){
//            System.out.println("没有此用户的数据，将此用户的数据放入缓存");
//            user = loaUserInfoMapper.selectByUserNameAndAreaCode(loginName,areaCode);
//            redisTemplate.boundHashOps("loausers").put(userkey,user);
//        }else {
//            System.out.println("从缓从中读取用户的数据");
//        }
//
//        return user;
    }

    @Override
    public void addLoaUserInfo(LoaUserInfo loaUserInfo) {
        loaUserInfoMapper.addLoaUserInfo(loaUserInfo);
    }

    @Override
    public List<LoaUserInfo> queryloaUserInfoByPersonalId(String personalId) {
        return loaUserInfoMapper.queryloaUserInfoByPersonalId(personalId);
    }

    @Override
    public LoaUserInfo queryloaUserInfoByuserId(String userid) {
        return loaUserInfoMapper.queryloaUserInfoByuserId(userid);
    }

    @Override
    public void deleteloaUserInfoByUserId(String userId) {
        loaUserInfoMapper.deleteloaUserInfoByUserId(userId);
    }

    @Override
    public void updateloaUserInfoByUserId(AmPersonalInfo amPersonalInfo) {
        loaUserInfoMapper.updateloaUserInfoByUserId(amPersonalInfo);
    }

    @Override
    public void forbiddenLoaUserInfo(String personalId, String user_active_flase) {
        loaUserInfoMapper.forbiddenLoaUserInfo(personalId,user_active_flase);
    }

    @Override
    public void startusingLoaUserInfo(String personalId, String user_active_true) {
        loaUserInfoMapper.startusingLoaUserInfo(personalId,user_active_true);
    }

}
