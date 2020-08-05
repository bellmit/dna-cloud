package com.bazl.dna.database.core.response.datamodel;

import com.bazl.dna.database.service.model.po.MatterComparePerson;
import com.bazl.dna.database.service.model.po.ReviewCompare;
import com.bazl.dna.database.service.model.po.ToReportInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 封装首页显示数据对象
 * by lizhihua 2020-4-10
 */
public class HomePageDataModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<MatterComparePerson> matterComparePersonList;

    List<MatterComparePerson> matterCompareMatterList;

    ReviewCompare toReviewCompare;

    ToReportInfo toReportInfo;

    ComprehensiveInfo comprehensiveInfo;

    public List<MatterComparePerson> getMatterComparePersonList() {
        return matterComparePersonList;
    }

    public void setMatterComparePersonList(List<MatterComparePerson> matterComparePersonList) {
        this.matterComparePersonList = matterComparePersonList;
    }

    public List<MatterComparePerson> getMatterCompareMatterList() {
        return matterCompareMatterList;
    }

    public void setMatterCompareMatterList(List<MatterComparePerson> matterCompareMatterList) {
        this.matterCompareMatterList = matterCompareMatterList;
    }

    public ReviewCompare getToReviewCompare() {
        return toReviewCompare;
    }

    public void setToReviewCompare(ReviewCompare toReviewCompare) {
        this.toReviewCompare = toReviewCompare;
    }

    public ToReportInfo getToReportInfo() {
        return toReportInfo;
    }

    public void setToReportInfo(ToReportInfo toReportInfo) {
        this.toReportInfo = toReportInfo;
    }

    public ComprehensiveInfo getComprehensiveInfo() {
        return comprehensiveInfo;
    }

    public void setComprehensiveInfo(ComprehensiveInfo comprehensiveInfo) {
        this.comprehensiveInfo = comprehensiveInfo;
    }
}
