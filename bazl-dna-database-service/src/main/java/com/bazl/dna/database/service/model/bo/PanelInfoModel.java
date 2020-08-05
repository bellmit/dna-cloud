package com.bazl.dna.database.service.model.bo;

import com.bazl.dna.database.service.model.po.DnaPanelInfo;
import com.bazl.dna.database.service.model.po.DnaPanelLocus;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Liuchang on 2020/5/28.
 */
public class PanelInfoModel  implements Serializable {


    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;

    //试剂盒信息
    private DnaPanelInfo panelInfo;
    //基因座关联信息
    private List<DnaPanelLocus> locusInfoList;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public List<DnaPanelLocus> getLocusInfoList() {
        return locusInfoList;
    }

    public void setLocusInfoList(List<DnaPanelLocus> locusInfoList) {
        this.locusInfoList = locusInfoList;
    }

    public DnaPanelInfo getPanelInfo() {
        return panelInfo;
    }

    public void setPanelInfo(DnaPanelInfo panelInfo) {
        this.panelInfo = panelInfo;
    }
}
