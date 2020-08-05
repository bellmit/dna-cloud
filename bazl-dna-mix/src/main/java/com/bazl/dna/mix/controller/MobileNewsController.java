package com.bazl.dna.mix.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.mix.constants.Constants;
import com.bazl.dna.mix.constants.ErrorCodes;
import com.bazl.dna.mix.constants.ErrorInfo;
import com.bazl.dna.mix.controller.base.BaseController;
import com.bazl.dna.mix.controller.base.error.ErrorCode;
import com.bazl.dna.mix.controller.base.error.ErrorMsgManager;
import com.bazl.dna.mix.controller.base.error.IException;
import com.bazl.dna.mix.model.po.MobileNews;
import com.bazl.dna.mix.service.MobileNewsService;
import com.bazl.dna.mix.utils.ListUtils;
import com.bazl.dna.util.RequestUtils;

/**
 * Created by Administrator on 2018/12/24.
 */
@RestController
@RequestMapping("/mobileNews")
public class MobileNewsController extends BaseController {

	@Autowired
	private MobileNewsService mobileNewsService;

	/**
	 * 绑定用户消息提示
	 * 
	 * @return
	 * @throws IException
	 */
	@RequestMapping(value = "/getMessage")
	public ResponseData sequencingAnalysis() throws IException {
		try {
			// 获得用户对象
			AuthUser user = RequestUtils.getAuthUser();
			if (null != user.getId()) {
				MobileNews mobileNews = new MobileNews();
				mobileNews.setType(11);// 信息类型
				mobileNews.setUserid(user.getId());// 绑定用户
				List<MobileNews> list = mobileNewsService.getUserNewsList(mobileNews);
				return new ResponseData(list);
			} else {
				return new ResponseData(ErrorCode.ERR_DATA_EMPTY,
						ErrorMsgManager.GetErrorMsg(ErrorCode.ERR_DATA_EMPTY));
			}
		} catch (Exception e) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
	}

	/**
	 * 查看用户已读未读数
	 */
	@RequestMapping(value = "/getUserMobileNewsNumber")
	public ResponseData getUserMobileNewsNumber() {
		try {
            String mobileNew = "0";//无新消息
			// 获得用户对象
			AuthUser user = RequestUtils.getAuthUser();
			if (user != null && null != user.getId()) {
				MobileNews mobileNews = new MobileNews();
				mobileNews.setType(11);// 信息类型
				mobileNews.setUserid(user.getId());// 绑定用户
				Map<String, Object> map = mobileNewsService.getUserMobileNumber(mobileNews.getUserid());
                int i = mobileNewsService.selectByMobileFlag(user.getId());
                if (i != 0){
                    mobileNew = "1";
                }
                map.put("mobileNew",mobileNew);
                return new ResponseData(map);
			} else {
				return new ResponseData(ErrorCode.ERR_DATA_EMPTY,
						ErrorMsgManager.GetErrorMsg(ErrorCode.ERR_DATA_EMPTY));
			}
		} catch (Exception e) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}

	}

	/**
	 * 根据消息id删除消息
	 */
	@RequestMapping(value = "/deleteMobileNewsById")
	public ResponseData deleteMobileNewsById(String id) {
		if (id != null && id != "") {
			int type = mobileNewsService.deleteByPrimaryKey(id);
			return new ResponseData(type);
		}
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	/**
	 * 清空当前用户所有消息
	 */
	@RequestMapping(value = "/deleteAllMobileNew")
	public ResponseData deleteAllMobileNew() {
		try {
			// 获得用户对象
			AuthUser user = RequestUtils.getAuthUser();
			if (null != user.getId()) {
				MobileNews mobileNews = new MobileNews();
				mobileNews.setType(11);// 信息类型
				mobileNews.setUserid(user.getId());// 绑定用户
				List<MobileNews> selectPageAll = mobileNewsService.getUserNewsList(mobileNews);
				if (ListUtils.isNotNullAndEmptyList(selectPageAll)) {
					for (MobileNews news : selectPageAll) {
						mobileNewsService.deleteByPrimaryKey(news.getId());
					}
				}
				return new ResponseData();
			}
		} catch (Exception e) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	/**
	 * 查看信息详情
	 */
	@RequestMapping(value = "/getMobileNews")
	public ResponseData getMobileNews(String id) {
		if (StringUtils.isNotBlank(id)) {
			MobileNews getmobileNews = mobileNewsService.selectByPrimaryKey(id);
			if (null != getmobileNews && 0 == getmobileNews.getState()) {
				getmobileNews.setState(1);
				mobileNewsService.updateByPrimaryKey(getmobileNews);
			}
			return new ResponseData(getmobileNews);
		}
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	/**
	 * 查看消息列表
	 */
	@RequestMapping(value = "/findList")
	public ResponseData findList(String userId, int page, String title, Integer state) {
		MobileNews mobileNews = new MobileNews();
		if (StringUtils.isNotBlank(userId) && page != 0) {
			PageInfo pageInfo = new PageInfo();
			pageInfo.setPage(page);
			mobileNews.setUserid(userId);
			mobileNews.setTitle(title);
			mobileNews.setState(state);
			List<MobileNews> list = mobileNewsService.selectPageAll(mobileNews, pageInfo);
			int count = mobileNewsService.selectPageCount(mobileNews);
			pageInfo.setAllRecordCount(count);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pageInfo", pageInfo);
			map.put("list", list);
			return new ResponseData(map);
		}
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	/**
	 * 查看用户已读未读数
	 */
	@RequestMapping(value = "/getPcOrgMassage")
	public ResponseData getPcOrgMassage(@RequestBody MobileNews mobileNews) {
		if (null != mobileNews && !"".equals(mobileNews.getUserOrg())) {
			List<MobileNews> getmobileNews = mobileNewsService.selectPcList(mobileNews);
			return new ResponseData(getmobileNews);
		}
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	/*
	*   消息忽略
	* */
	@RequestMapping(value = "/neglectMassage", produces = "application/json;charset=UTF-8")
	public ResponseData neglectMassageList(@RequestBody List<String> idList) {
		if (ListUtils.isNotNullAndEmptyList(idList)){
            for (String str : idList){
                try {
                    MobileNews mobileNews = mobileNewsService.selectByPrimaryKey(str);
                    if (mobileNews != null){
                        mobileNews.setMobileFlag(Constants.MOBILE_FLAG_1);
                        mobileNewsService.updateByPrimaryKey(mobileNews);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("修改信息错误！" + e.getMessage());
                }
            }
            return new ResponseData(1,"成功！");
		}else {
			logger.error("传入参数为空！");
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
	}
}
