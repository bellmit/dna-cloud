package com.bazl.dna.lims.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.lims.dao.LaboratoryInfoMapper;
import com.bazl.dna.lims.model.po.LaboratoryInfo;
import com.bazl.dna.lims.service.LaboratoryInfoService;
@Service
public class LaboratoryInfoServiceImpl extends BaseService implements LaboratoryInfoService {

    @Autowired
    LaboratoryInfoMapper laboratoryInfoMapper;

    @Override
    public List<LaboratoryInfo> queryList() {
        List<LaboratoryInfo> list = new ArrayList<>();
        try {
            list = laboratoryInfoMapper.queryList();
        }catch(Exception ex){
            logger.error("invoke LaboratoryInfoService.queryList error.", ex);
        }

        return list;
    }

    @Override
    public LaboratoryInfo queryById(LaboratoryInfo laboratoryInfo) {
        return laboratoryInfoMapper.queryByLab(laboratoryInfo);
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void addOne(LaboratoryInfo laboratoryInfo) {

    }

    @Override
    public void updateOne(LaboratoryInfo laboratoryInfo) {
        laboratoryInfoMapper.updateLabInfo(laboratoryInfo);
    }
    
    /**
     *查询全部实验室列表
     */
    @Override
    public List<LaboratoryInfo> selectAll(){
        try{
            return laboratoryInfoMapper.queryList();
        }catch(Exception ex){
            logger.error("invoke laboratoryInfoService.selectAll error.", ex);
        }
        return new ArrayList<LaboratoryInfo>();
    }

	@Override
	public LaboratoryInfo queryByLab(LaboratoryInfo laboratoryInfo) {
		return laboratoryInfoMapper.queryByLab(laboratoryInfo);
	}

	@Override
	public List<LaboratoryInfo> findListByMonitorType(Integer monitorType) {
		try{
            return laboratoryInfoMapper.findListByMonitorType(monitorType);
        }catch(Exception ex){
            logger.error("findList error.", ex);
        }
        return new ArrayList<LaboratoryInfo>();
	}

	@Override
	public LaboratoryInfo queryById(String id) {
		return laboratoryInfoMapper.queryById(id);
	}

//    @Override
//    public LaboratoryInfo queryByClientIP() {
//        LaboratoryInfo laboratoryInfo=null;
//        try {
//            InetAddress addr=InetAddress.getLocalHost();
//            String ip=addr.getHostAddress();//拿到访问客户端ip
//            //获取所有单位的实验室信息
//            List<LaboratoryInfo> laboratoryInfoList=laboratoryInfoMapper.queryList();
//            for(int i=0;i<laboratoryInfoList.size();i++){
//                if(IPsegmentUtil.inIpsegment(ip,laboratoryInfoList.get(i).getIpSegment())){
//                    laboratoryInfo=laboratoryInfoList.get(i);
//                    break;
//                }
//            }
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//            logger.error("访问服务器的客户端IP错误："+e.getMessage());
//        }
//        return laboratoryInfo;
//    }
}
