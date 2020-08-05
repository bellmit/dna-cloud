package com.bazl.dna.dpznsy.model.bo;

import java.io.Serializable;

/**
 * Created by cyj on 2020/3/2.
 */
public class MobileNewsModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mobileId;//消息主键id
    private String title;//消息标题
    private String content;//消息内容
    private String userOrg;//发布机构
    private String majorNo;//专业
    private String createDatetime;//发布时间

    public String getMobileId() {
        return mobileId;
    }

    public void setMobileId(String mobileId) {
        this.mobileId = mobileId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserOrg() {
        return userOrg;
    }

    public void setUserOrg(String userOrg) {
        this.userOrg = userOrg;
    }

    public String getMajorNo() {
        return majorNo;
    }

    public void setMajorNo(String majorNo) {
        this.majorNo = majorNo;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }
}
