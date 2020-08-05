package com.bazl.dna.lims.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.lims.dao.LimsSampleGeneMapper;
import com.bazl.dna.lims.model.po.CodisGeneInfo;
import com.bazl.dna.lims.model.po.LimsSampleGene;
import com.bazl.dna.lims.model.vo.LimsCaseInfoVo;
import com.bazl.dna.lims.model.vo.LimsSampleGeneVo;
import com.bazl.dna.lims.service.LimsSampleGeneService;

/**
 * @author wanghaiyang
 * @date 2019/2/26.
 */
@Service
public class LimsSampleGeneServiceImpl extends BaseService implements LimsSampleGeneService {

    @Autowired
    LimsSampleGeneMapper limsSampleGeneMapper;

    @Override
    public int deleteByPrimaryKey(String geneId) {
        return limsSampleGeneMapper.deleteByPrimaryKey(geneId);
    }

    @Override
    public int insert(LimsSampleGene record) {
        return limsSampleGeneMapper.insert(record);
    }

    @Override
    public LimsSampleGene selectByPrimaryKey(String geneId) {
        return limsSampleGeneMapper.selectByPrimaryKey(geneId);
    }

    @Override
    public List<LimsSampleGene> selectAll() {
        return limsSampleGeneMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(LimsSampleGene record) {
        return limsSampleGeneMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<LimsSampleGene> selectListByCaseId (String caseId) {
        return limsSampleGeneMapper.selectListByCaseId(caseId);
    }

    @Override
    public List<LimsSampleGeneVo> selecVOtList (LimsSampleGeneVo limsSampleGeneVo) {
        return limsSampleGeneMapper.selecVOtList(limsSampleGeneVo);
    }

    @Override
    public int updateBySampleId(String sampleId) {
        return limsSampleGeneMapper.updateBySampleId(sampleId);
    }

    @Override
    public void updateAuditStatus(LimsSampleGene limsSampleGene) {
        limsSampleGeneMapper.updateAuditStatus(limsSampleGene);
    }

    /**
     * 删除标记
     * @param limsSampleGene
     */
    @Override
    public void updateDeleteFlag(LimsSampleGene limsSampleGene) {
        limsSampleGeneMapper.updateDeleteFlag(limsSampleGene);
    }

    /**
     * 更新复检状态
     * @param limsSampleGene
     */
    @Override
    public void updateReviewStatus(LimsSampleGene limsSampleGene) {
        limsSampleGeneMapper.updateReviewStatus(limsSampleGene);
    }

    @Override
    public List<LimsSampleGene> selectAuditedBySampleId(String sampleId) {
        return limsSampleGeneMapper.selectAuditedBySampleId(sampleId);
    }

    @Override
    public List<LimsSampleGene> selectListBySampleId(String sampleId) {
        return limsSampleGeneMapper.selectListBySampleId(sampleId);
    }

    @Override
    public List<LimsSampleGene> selectListByAnalysisInfoId(String analysisInfoId) {
        return limsSampleGeneMapper.selectListByAnalysisInfoId(analysisInfoId);
    }

    @Override
    public List<LimsSampleGene> selectByGeneList(LimsSampleGene limsSampleGene) {
        return limsSampleGeneMapper.selectByGeneList(limsSampleGene);
    }
    
    @Override
    public int selectDetectionGeneCountByOrgCode(LimsCaseInfoVo limsCaseInfoVo) {
        return limsSampleGeneMapper.selectDetectionGeneCountByOrgCode(limsCaseInfoVo);
    }
    
    @Override
    public List<LimsSampleGeneVo> selectSampleGeneBySampleNo(String sampleNo) {
        return limsSampleGeneMapper.selectSampleGeneBySampleNo(sampleNo);
    }
    
    @Override
    public int updateGene(LimsSampleGeneVo limsSampleGeneVo) {
        JSONObject obj = new JSONObject();
        //转换为alims的基因型格式
        List<CodisGeneInfo> geneInfoList = limsSampleGeneVo.getGeneInfoList();
        if (geneInfoList.size() != 0) {
            for (CodisGeneInfo geneInfo : geneInfoList) {
                String panelName = null;
                JSONArray jar = new JSONArray();
                panelName = geneInfo.getGeneName();
                if (StringUtils.isBlank(geneInfo.getGeneVal3())) {
                    jar.add(geneInfo.getGeneVal1() + "," + geneInfo.getGeneVal2());
                } else if (StringUtils.isBlank(geneInfo.getGeneVal4()) && StringUtils.isNotBlank(geneInfo.getGeneVal3())) {
                    jar.add(geneInfo.getGeneVal1() + "," + geneInfo.getGeneVal2() + "," + geneInfo.getGeneVal3());
                } else if (StringUtils.isBlank(geneInfo.getGeneVal4())) {
                    jar.add(geneInfo.getGeneVal1() + "," + geneInfo.getGeneVal2() + "," + geneInfo.getGeneVal3() + "," + geneInfo.getGeneVal4());
                }
                obj.put(panelName, jar);
            }
            limsSampleGeneVo.setAlimsGeneFormat(obj.toJSONString());

            //转换为老系统的基因型格式
            List<LimsSampleGeneVo> limsSampleGeneList = limsSampleGeneMapper.selectSampleGeneBySampleNo(limsSampleGeneVo.getSampleNo());
            if (limsSampleGeneList.size() != 0) {
                for (LimsSampleGeneVo sampleGeneVo : limsSampleGeneList) {
                    //转换基因型格式
                    limsSampleGeneVo.setLimsGeneFormat(convertGenoTypeString(sampleGeneVo.getGeneMaeker(), geneInfoList));
                }
            }
        }

        try {
            if (StringUtils.isNotBlank(limsSampleGeneVo.getLimsGeneFormat())) {
                limsSampleGeneMapper.updateMarkerallel(limsSampleGeneVo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("更新lims库基因型出错" + e);
            return 0;
        }
        List<LimsSampleGeneVo> limsSampleGeneVoList = limsSampleGeneMapper.selectGeneIdBySampleNo(limsSampleGeneVo.getSampleNo());
        if (limsSampleGeneVoList.size() != 0) {
            for (LimsSampleGeneVo limsSampleGene : limsSampleGeneVoList) {
                if (StringUtils.isNotBlank(limsSampleGene.getEntity().getGeneId())) {
                    limsSampleGeneVo.setGeneId(limsSampleGene.getEntity().getGeneId());
                }
            }
            try {
                limsSampleGeneMapper.updateGeneInfo(limsSampleGeneVo);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("更新ALims库基因型出错" + e);
                return 0;
            }
        } else {
            logger.info("alims中无该样本的基因型信息，样本编号为：" + limsSampleGeneVo.getSampleNo());
            return 1;
        }
        return 1;
    }
    
  //转换基因型格式
    public static String convertGenoTypeString(String genotypeString, List<CodisGeneInfo> newGeneInfoList) {

        if (genotypeString == null || genotypeString.equals("")) {
            return null;
        }

        String[] splitedString = splitString(genotypeString, '[');
        String newGene = "";

        for (int i = 0; i < splitedString.length; i++) {
            for (int c = 0; c < newGeneInfoList.size(); c++) {
                int index1 = splitedString[i].indexOf("[");
                int index2 = splitedString[i].indexOf("]");

                String markerName = splitedString[i].substring(index1 + 1, index2);
                newGene += "[" + markerName + "]";

                String alleleStrings = splitedString[i].substring(index2 + 1, splitedString[i].length());
                String[] markerAlleles = splitString(alleleStrings, '<');
                if (markerAlleles == null) {
                    return null;
                }

                for (int j = 0; j < markerAlleles.length; j++) {
                    String[] alleleInfoString = splitString(markerAlleles[j], ',');
                    if (alleleInfoString.length > 0) {
                        if (markerName.equals(newGeneInfoList.get(i).getGeneName()) && j == 0) {
                            alleleInfoString[0] = newGeneInfoList.get(i).getGeneVal1();
                        } else if (markerName.equals(newGeneInfoList.get(i).getGeneName()) && j == 1) {
                            alleleInfoString[0] = newGeneInfoList.get(i).getGeneVal2();
                        } else if (markerName.equals(newGeneInfoList.get(i).getGeneName()) && j == 2) {
                            alleleInfoString[0] = newGeneInfoList.get(i).getGeneVal3();
                        } else if (markerName.equals(newGeneInfoList.get(i).getGeneName()) && j == 3) {
                            alleleInfoString[0] = newGeneInfoList.get(i).getGeneVal4();
                        }

                        String str = Arrays.toString(alleleInfoString);
                        String gene1 = str.substring(1);
                        String gene2 = gene1.substring(0, gene1.length() - 1);
                        newGene += "<" + gene2.replace(" ", "") + ">";

                    }
                }
                break;
            }
        }
        return newGene;
    }


    private static String[] splitString(String string, char chr) {
        if (string != null && !"".equals(string)) {
            Vector<Integer> indexs = new Vector<Integer>();
            char[] chars = string.toCharArray();
            for (int i = 0; i < string.length(); i++) {
                if (chars[i] == chr) {
                    indexs.add(Integer.valueOf(i));
                }
            }
            List<String> splitedString = new ArrayList<String>();
            for (int i = 0; i < indexs.size(); i++) {
                if (chr == '[' || chr == '<') {
                    if (i < indexs.size() - 1) {
                        int fromIndex = indexs.get(i);
                        int endIndex = indexs.get(i + 1);
                        if (null != string.substring(fromIndex, endIndex) && !"".equals(string.substring(fromIndex, endIndex))) {
                            splitedString.add(string.substring(fromIndex, endIndex));
                        }
                    } else {
                        if (null != string.substring(indexs.get(i), string.length()) && !"".equals(string.substring(indexs.get(i), string.length()))) {
                            splitedString.add(string.substring(indexs.get(i), string.length()));
                        }
                    }
                } else {
                    if (indexs.size() > 1) {
                        if (i == 0) {
                            if (null != string.substring(1, indexs.get(i)) && !"".equals(string.substring(1, indexs.get(i)))) {
                                splitedString.add(string.substring(1, indexs.get(i)));
                            }
                        } else {
                            int beginIndex = indexs.get(i - 1);
                            int endIndex = indexs.get(i);
                            if (null != string.substring(beginIndex + 1, endIndex) && !"".equals(string.substring(beginIndex + 1, endIndex))) {
                                splitedString.add(string.substring(beginIndex + 1, endIndex));
                            }
                        }
                        if (i == indexs.size() - 1) {
                            if (null != string.substring(indexs.get(indexs.size() - 1) + 1, string.length() - 1) &&
                                    !"".equals(string.substring(indexs.get(indexs.size() - 1) + 1, string.length() - 1))) {
                                splitedString.add(string.substring(indexs.get(indexs.size() - 1) + 1, string.length() - 1));
                            }
                        }
                    } else if (indexs.size() == 1) {
                        if (null != string.substring(1, indexs.get(i)) && !"".equals(string.substring(1, indexs.get(i)))) {
                            splitedString.add(string.substring(1, indexs.get(i)));
                        }
                        if (null != string.substring(indexs.get(i) + 1, string.length() - 1) &&
                                !"".equals(string.substring(indexs.get(i) + 1, string.length() - 1))) {
                            splitedString.add(string.substring(indexs.get(i) + 1, string.length() - 1));
                        }
                    }
                }
            }
            return splitedString.toArray(new String[0]);
        }
        return null;
    }

}
