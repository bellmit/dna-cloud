package com.bazl.dna.mix.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.bazl.dna.mix.model.po.Contributor;
import com.bazl.dna.mix.model.po.ContributorGene;
import com.bazl.dna.mix.model.po.GeneAllele;
import com.bazl.dna.mix.model.vo.RapiGeneComparisonVo;

/**
 * @Author: lzn
 * @Date: 2019/7/28 10:15
 * @Version: 1.0
 */
public class AnalysisPDFUtils {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(AnalysisPDFUtils.class);
    
    /**
     * 解析pdf
     * @param splitReportFiles
     * @return
     * @throws Exception
     */
//    public static RapiGeneComparisonVo getDataPDFDat(MultipartFile splitReportFiles) throws Exception {
//        RapiGeneComparisonVo  rapiGeneComparisonVo=new RapiGeneComparisonVo();
//        try {
//            //创建fileDir对象
//            String dPath = FileUtils.getProperties("/application.yml", "filePath");
//
//            File fileDir = new File(dPath);
//            //判断文件夹是否存在
//            if (!fileDir.exists()) {
//                //不存在的话,创建文件夹
//                fileDir.mkdirs();
//            }
//
//            // 获取文件名字
//            String fileName = splitReportFiles.getOriginalFilename();
//            //实现文件的上传
//            splitReportFiles.transferTo(new File(dPath+fileName));
//            String filepPath = dPath + fileName;
//            List<Contributor> contributorList = new ArrayList<>();
////            String filepPathS="C:\\Users\\Administrator\\Desktop\\StrMixpdf\\3P-1reference.pdf";
//            if (StringUtils.isNotBlank(filepPath)) {
//                String content = null;
//
//                boolean sort = true;
//                int startPage = 1;
//                int endPage = Integer.MAX_VALUE;
//                //pdf路径
//                PDDocument document = PDDocument.load(new File(filepPath));
//                PDFTextStripper pts = new PDFTextStripper();
//                endPage = document.getNumberOfPages();
//                pts.setSortByPosition(sort);
//                pts.setStartPage(startPage);
//                pts.setEndPage(endPage);
//                //获取文件内容
//                content = pts.getText(document);
//
//                //获取权重
//                String peopleCount = null;
//                String contributorWeight = null;
//                peopleCount = content.substring(content.indexOf("SUMMARY OF CONTRIBUTORS"), content.indexOf("VARIANCE CHARTS"));
//                String[] contributorCount = peopleCount.split("\\r?\\n");
//                for (int i = 0; i < contributorCount.length; i++) {
//                    contributorWeight = contributorCount[3];
//                }
//                String[] sampleA1 = contributorWeight.split("\\s+");
//                List<String> contributorWeightList = new ArrayList<String>();
//                for (int i = 2; i < sampleA1.length; i++) {
//                    contributorWeightList.add(sampleA1[i]);
//                }
//                // 获取贡献者的信息
//                String componentInterpretation = null;
//                componentInterpretation = content.substring(content.indexOf("COMPONENT INTERPRETATION"), content.indexOf("COMPONENT INTERPRETATION SUMMARY ≥ 99%"));
//
//                String[] contributorGeneArr = componentInterpretation.split("\\r?\\n");
//                List<String> list2 = new ArrayList<>();
//                // 将数据放入到新的List里面
//                for (int i = 0; i < contributorGeneArr.length; i++) {
//                    if (!contributorGeneArr[i].contains("CASE REF NO:")
//                            && !contributorGeneArr[i].contains("LOCUS GENOTYPE WEIGHT COMPONENT ≥ 99%")
//                            && !contributorGeneArr[i].contains("Assumed contributor")
//                            && !contributorGeneArr[i].contains("Questioned contributor")
//                            && !contributorGeneArr[i].contains("COMPONENT INTERPRETATION")
//                            && !contributorGeneArr[i].contains("USER:")) {
//                        list2.add(contributorGeneArr[i]);
//                    }
//                }
//                //转list成数组的形式
//                String[] sampleA2 = list2.toArray(new String[list2.size()]);
//                //基因名称
//                String geneNameInfo = null;
//                String contributo = null;
//                String contributoPrefix = "CONTRIBUTOR";
//                int index = 1;
//                String contributorName = contributoPrefix + " " + index;
//                //下一个贡献者的标题
//                String nextContributorName = null;
//                //当前贡献者的标题
//                String currentContributorName = null;
//
//                Contributor contributor = null;
//                List<ContributorGene> contributorGeneList = null;
//                ContributorGene contributorGene = null;
//                List<GeneAllele> geneAlleleList = null;
//
//                //循环所有贡献者的信息
//                for (int i = 0; i < sampleA2.length; i++) {
//                    contributo = sampleA2[i];
//                    String[] contributoss = contributo.split("\\s+");
//
//                    //如果为空跳出本次循环
//                    if (contributoss.length == 0) {
//                        continue;
//                    }
//
//                    if(contributo.contains(contributorName)&& contributor == null){
//                        contributor = new Contributor();
//                        contributor.setContributorWeight(contributorWeightList.get(index - 1));
//                        contributor.setContributorName(contributorName);
//
//                        contributorGeneList = new ArrayList<>();
//
//                        currentContributorName=contributoPrefix+ " " + index;
//                        nextContributorName = contributoPrefix + " " + (++index);
//                        continue;
//                    }else if(contributo.startsWith(contributoPrefix)&& !contributo.contains(contributorName)
//                            && contributor != null && contributo.contains(nextContributorName)){
//
//                        contributorGene.setGeneName(geneNameInfo);
//                        if (ListUtils.isNotNullAndEmptyList(geneAlleleList)) {
//                            contributorGene.setGeneAlleleList(geneAlleleList);
//                        }
//                        contributorGeneList.add(contributorGene);
//
//                        contributor.setContributorGeneList(contributorGeneList);
//                        if(ListUtils.isNotNullAndEmptyList(contributorGeneList)){
//                            contributor.setGeneCount(contributorGeneList.size());
//                        }
//                        contributorList.add(contributor);
//
//                        contributor = new Contributor();
//                        contributor.setContributorName(nextContributorName);
//                        contributor.setContributorWeight(contributorWeightList.get(index - 1));
//
//                        contributorGene = null;
//                        geneNameInfo = null;
//
//                        contributorGeneList = new ArrayList<>();
//                        currentContributorName=contributoPrefix+ " " + index;
//                        nextContributorName = contributoPrefix + " " + (++index);
//
//                        continue;
//                    }
//                    //如果当前贡献者标题重复，则跳出
//                    if(contributo.contains(currentContributorName)){
//                        continue;
//                    }
//                    if (contributoss.length > 5 || contributoss.length <= 1) {
//                        if (contributorGene == null || geneNameInfo == null) {
//                            contributorGene = new ContributorGene();
//                            geneAlleleList = new ArrayList<>();
//                        }else{
//                            contributorGene.setGeneName(geneNameInfo);
//                            if (ListUtils.isNotNullAndEmptyList(geneAlleleList)) {
//                                contributorGene.setGeneAlleleList(geneAlleleList);
//                            }
//
//                            contributorGeneList.add(contributorGene);
//
//                            contributorGene = new ContributorGene();
//                            geneAlleleList = new ArrayList<>();
//                        }
//
//                        geneNameInfo = contributoss[0];
//                        //如果只有基因座名称，没有等位基因位点，则跳过
//                        if(contributoss.length <= 1){
//                            continue;
//                        }
//
//                        GeneAllele geneAllele = new GeneAllele();
//                        geneAllele.setAllele(contributoss[1] + contributoss[2]);
//                        geneAllele.setWeights(contributoss[3]);
//                        geneAlleleList.add(geneAllele);
//                    } else {
//                        GeneAllele geneAllele = new GeneAllele();
//                        geneAllele.setAllele(contributoss[0] + contributoss[1]);
//                        geneAllele.setWeights(contributoss[2]);
//                        geneAlleleList.add(geneAllele);
//                    }
//                }
//
//                contributorGene.setGeneName(geneNameInfo);
//                if (ListUtils.isNotNullAndEmptyList(geneAlleleList)) {
//                    contributorGene.setGeneAlleleList(geneAlleleList);
//                }
//                contributorGeneList.add(contributorGene);
//
//                contributor.setContributorGeneList(contributorGeneList);
//                if(ListUtils.isNotNullAndEmptyList(contributorGeneList)){
//                    contributor.setGeneCount(contributorGeneList.size());
//                }
//                contributorList.add(contributor);
//                if(ListUtils.isNotNullAndEmptyList(contributorList)){
//                    rapiGeneComparisonVo.setContributorCount(contributorList.size());
//                }
//                rapiGeneComparisonVo.setContributorList(contributorList);
//
//                if (document != null) {
//                    document.close();
//                }
//            }
//        } catch (Exception e) {
//            logger.error("解析strMix拆分报告错误：", e);
//        }
//        return   rapiGeneComparisonVo;
//    }




    public static RapiGeneComparisonVo getDataPDFDat(HttpServletRequest request, MultipartFile splitReportFiles, String filePath) throws Exception {
        RapiGeneComparisonVo  rapiGeneComparisonVo=new RapiGeneComparisonVo();
        try {

            List<Contributor> contributorList = new ArrayList<>();
            if (StringUtils.isNotBlank(splitReportFiles.getName())) {

            	    final File excelFile = File.createTempFile(UUID.randomUUID().toString(), ".tmp", new File(filePath));
                splitReportFiles.transferTo(excelFile);

                //你的业务逻辑
                String content = null;

                boolean sort = true;
                int startPage = 1;
                int endPage = Integer.MAX_VALUE;
                //pdf路径
                PDDocument document = PDDocument.load(excelFile);
                PDFTextStripper pts = new PDFTextStripper();
                endPage = document.getNumberOfPages();
                pts.setSortByPosition(sort);
                pts.setStartPage(startPage);
                pts.setEndPage(endPage);
                //获取文件内容
                content = pts.getText(document);

                //获取权重
                String peopleCount = null;
                String contributorWeight = null;
                peopleCount = content.substring(content.indexOf("SUMMARY OF CONTRIBUTORS"), content.indexOf("VARIANCE CHARTS"));
                String[] contributorCount = peopleCount.split("\\r?\\n");
                for (int i = 0; i < contributorCount.length; i++) {
                    contributorWeight = contributorCount[3];
                }
                String[] sampleA1 = contributorWeight.split("\\s+");
                List<String> contributorWeightList = new ArrayList<String>();
                for (int i = 2; i < sampleA1.length; i++) {
                    contributorWeightList.add(sampleA1[i]);
                }
                // 获取贡献者的信息
                String componentInterpretation = null;
                componentInterpretation = content.substring(content.indexOf("COMPONENT INTERPRETATION"), content.indexOf("COMPONENT INTERPRETATION SUMMARY ≥ 99%"));

                String[] contributorGeneArr = componentInterpretation.split("\\r?\\n");
                List<String> list2 = new ArrayList<>();
                // 将数据放入到新的List里面
                for (int i = 0; i < contributorGeneArr.length; i++) {
                    if (!contributorGeneArr[i].contains("CASE REF NO:")
                            && !contributorGeneArr[i].contains("LOCUS GENOTYPE WEIGHT COMPONENT ≥ 99%")
                            && !contributorGeneArr[i].contains("Assumed contributor")
                            && !contributorGeneArr[i].contains("Questioned contributor")
                            && !contributorGeneArr[i].contains("COMPONENT INTERPRETATION")
                            && !contributorGeneArr[i].contains("USER:")) {
                        list2.add(contributorGeneArr[i]);
                    }
                }
                //转list成数组的形式
                String[] sampleA2 = list2.toArray(new String[list2.size()]);
                //基因名称
                String geneNameInfo = null;
                String contributo = null;
                String contributoPrefix = "CONTRIBUTOR";
                int index = 1;
                String contributorName = contributoPrefix + " " + index;
                //下一个贡献者的标题
                String nextContributorName = null;
                //当前贡献者的标题
                String currentContributorName = null;

                Contributor contributor = null;
                List<ContributorGene> contributorGeneList = null;
                ContributorGene contributorGene = null;
                List<GeneAllele> geneAlleleList = null;

                //循环所有贡献者的信息
                for (int i = 0; i < sampleA2.length; i++) {
                    contributo = sampleA2[i];
                    String[] contributoss = contributo.split("\\s+");

                    //如果为空跳出本次循环
                    if (contributoss.length == 0) {
                        continue;
                    }

                    if(contributo.contains(contributorName)&& contributor == null){
                        contributor = new Contributor();
                        contributor.setContributorWeight(contributorWeightList.get(index - 1));
                        contributor.setContributorName(contributorName);

                        contributorGeneList = new ArrayList<>();

                        currentContributorName=contributoPrefix+ " " + index;
                        nextContributorName = contributoPrefix + " " + (++index);
                        continue;
                    }else if(contributo.startsWith(contributoPrefix)&& !contributo.contains(contributorName)
                            && contributor != null && contributo.contains(nextContributorName)){

                        contributorGene.setGeneName(geneNameInfo);
                        if (ListUtils.isNotNullAndEmptyList(geneAlleleList)) {
                            contributorGene.setGeneAlleleList(geneAlleleList);
                        }
                        contributorGeneList.add(contributorGene);

                        contributor.setContributorGeneList(contributorGeneList);
                        if(ListUtils.isNotNullAndEmptyList(contributorGeneList)){
                            contributor.setGeneCount(contributorGeneList.size());
                        }
                        contributorList.add(contributor);

                        contributor = new Contributor();
                        contributor.setContributorName(nextContributorName);
                        contributor.setContributorWeight(contributorWeightList.get(index - 1));

                        contributorGene = null;
                        geneNameInfo = null;

                        contributorGeneList = new ArrayList<>();
                        currentContributorName=contributoPrefix+ " " + index;
                        nextContributorName = contributoPrefix + " " + (++index);

                        continue;
                    }
                    //如果当前贡献者标题重复，则跳出
                    if(contributo.contains(currentContributorName)){
                        continue;
                    }
                    if (contributoss.length > 5 || contributoss.length <= 1) {
                        if (contributorGene == null || geneNameInfo == null) {
                            contributorGene = new ContributorGene();
                            geneAlleleList = new ArrayList<>();
                        }else{
                            contributorGene.setGeneName(geneNameInfo);
                            if (ListUtils.isNotNullAndEmptyList(geneAlleleList)) {
                                contributorGene.setGeneAlleleList(geneAlleleList);
                            }

                            contributorGeneList.add(contributorGene);

                            contributorGene = new ContributorGene();
                            geneAlleleList = new ArrayList<>();
                        }

                        geneNameInfo = contributoss[0];
                        //如果只有基因座名称，没有等位基因位点，则跳过
                        if(contributoss.length <= 1){
                            continue;
                        }

                        GeneAllele geneAllele = new GeneAllele();
                        geneAllele.setAllele(contributoss[1] + contributoss[2]);
                        geneAllele.setWeights(contributoss[3]);
                        geneAlleleList.add(geneAllele);
                    } else {
                        GeneAllele geneAllele = new GeneAllele();
                        geneAllele.setAllele(contributoss[0] + contributoss[1]);
                        geneAllele.setWeights(contributoss[2]);
                        geneAlleleList.add(geneAllele);
                    }
                }

                contributorGene.setGeneName(geneNameInfo);
                if (ListUtils.isNotNullAndEmptyList(geneAlleleList)) {
                    contributorGene.setGeneAlleleList(geneAlleleList);
                }
                contributorGeneList.add(contributorGene);

                contributor.setContributorGeneList(contributorGeneList);
                if(ListUtils.isNotNullAndEmptyList(contributorGeneList)){
                    contributor.setGeneCount(contributorGeneList.size());
                }
                contributorList.add(contributor);
                if(ListUtils.isNotNullAndEmptyList(contributorList)){
                    rapiGeneComparisonVo.setContributorCount(contributorList.size());
                }
                rapiGeneComparisonVo.setContributorList(contributorList);

                if (document != null) {
                    document.close();
                }
                deleteFile(excelFile);
            }
        } catch (Exception e) {
            logger.error("解析strMix拆分报告错误：", e);
        }
        return   rapiGeneComparisonVo;
    }

    private static void deleteFile(File... files){
        for(File file :files){

            if(file.exists()){
                file.delete();
            }
        }


    }

}
