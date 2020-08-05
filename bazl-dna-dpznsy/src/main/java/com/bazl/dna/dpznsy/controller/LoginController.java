package com.bazl.dna.dpznsy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.dpznsy.controller.base.BaseController;
import com.bazl.dna.dpznsy.controller.base.ResultBean;
import com.bazl.dna.dpznsy.controller.base.error.ErrorCode;
import com.bazl.dna.dpznsy.controller.base.error.ErrorMsgManager;

/**
 * Created by chaiyajie on 2020/2/12.
 */

@RestController
public class LoginController extends BaseController {

    /*
    *      用户登录
    * */
    /*@RequestMapping(value ="/loginUser", produces = "application/json;charset=UTF-8")
    public ResultBean loginUser(HttpSession session, String loginName, String loginPassword, String status) throws Exception {
        if (StringUtils.isNotBlank(loginName) && StringUtils.isNotBlank(loginPassword) && StringUtils.isNotBlank(status)){
            Map<String, Object> result = new HashMap<>();
            //授权认证
            AuthUser userInfo = RequestUtils.getAuthUser();
            //用户过期时间
//            subject.getSession().setTimeout(1800000);
            try {
                //完成登录
                //获得用户对象
                if (userInfo != null && null != userInfo.getId()){
                    AmPersonalInfo amPersonalInfo = amPersonalInfoService.selectByPrimaryKey(userInfo.getId());
                    if (amPersonalInfo != null){
                        //专业
                        String workNow = amPersonalInfo.getWorkNow();
                        if (workNow != null){
                            result.put("policeWork", workNow);
                        }else {
                            result.put("policeWork", "0");
                        }
                        //用户姓名
                        result.put("personName", amPersonalInfo.getFullName());
                    }else {
                        return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "人员信息为空！");
                    }
                    //所属分局
                    if (StringUtils.isNotBlank(userInfo.getOrgId()) && userInfo.getOrgId().contains("110023")) {
                        result.put("orgId", "110230000000");
                        result.put("orgName", "八支队");
                    }else {
                        result.put("orgId", userInfo.getOrgId());
                        OrgInfo orgInfo = orgInfoService.selectByPrimaryKey(userInfo.getOrgId());
                        if (orgInfo != null){
                            result.put("orgName", orgInfo.getOrgName());
                        }else {
                            result.put("orgName", "");
                        }
                    }
                    //存入session
                    //session.setAttribute("userInfo", userInfo);
                    result.put("userName", userInfo.getUsername());
                    result.put("status", status);
                    //用户id
                    result.put("personalId", amPersonalInfo.getPersonalId());
                }else {
                    return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "用户信息为空！");
                }
                return new ResultBean(ResultBean.CODE_SUCCESS, 0, result, ResultBean.NAME_SUCCESS);

            } catch (Exception e) {
                logger.error("登录失败！！！"+e.getMessage());
                return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                        ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
            }
        }else {
            logger.error("登录失败！！！用户名或密码为空");
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQBODY_INVALID_MSGTYPE, 0, "用户名或密码不能为空！");
        }
    }*/

    /**
     * 注销
     */
    @RequestMapping(value ="/logOutUser", produces = "application/json;charset=UTF-8")
    public ResultBean logOutUser() {
        try {
            return new ResultBean(ResultBean.CODE_SUCCESS, 0, "已退出登录！", ResultBean.NAME_SUCCESS);
        } catch (Exception e) {
            logger.error("注销失败！！！"+e.getMessage());
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }
}
