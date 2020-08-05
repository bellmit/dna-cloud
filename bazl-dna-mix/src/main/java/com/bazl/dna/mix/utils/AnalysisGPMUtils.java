package com.bazl.dna.mix.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import com.bazl.dna.mix.model.po.Contributor;
import com.bazl.dna.mix.model.po.ContributorGene;
import com.bazl.dna.mix.model.po.GeneAllele;
import com.bazl.dna.mix.model.vo.RapiGeneComparisonVo;

/**
 * 解析GPM PDF
 * 
 * @author zhaoyong
 *
 */
public class AnalysisGPMUtils {
	
	/**
	 * 解析GPM PDF
	 * 
	 * @param splitReportFiles
	 * @return
	 */
	public static RapiGeneComparisonVo build(HttpServletRequest request, MultipartFile splitReportFiles, String filePath) throws Exception {
		// 获取文件名
        final File file = File.createTempFile(UUID.randomUUID().toString(), ".tmp", new File(filePath));
        splitReportFiles.transferTo(file);
        
		int endPage = Integer.MAX_VALUE;
		
		// pdf路径
		PDDocument document = PDDocument.load(file);
		PDFTextStripper pts = new PDFTextStripper();
		endPage = document.getNumberOfPages();
		pts.setSortByPosition(true);
		pts.setStartPage(1);
		pts.setEndPage(endPage);
		// 获取文件内容
		String content = pts.getText(document);
		RapiGeneComparisonVo result = gpmBuilder(content);
		document.close();
		
		deleteFile(file);
		return result;
	}

	private static void deleteFile(File... files) {
		for (File file : files) {
			if (file.exists()) {
				file.delete();
			}
		}
	}

	
	public static RapiGeneComparisonVo gpmBuilder(String content) {
		RapiGeneComparisonVo result = new RapiGeneComparisonVo();
		
		//贡献者数
		String contributorCountPos = content.substring(content.indexOf("Number of contributors"), content.indexOf("Hypothesis"));
		String contributorCount = StringUtils.trim(StringUtils.substringAfterLast(contributorCountPos, " "));
		result.setContributorCount(Integer.parseInt(contributorCount));
		
		List<Contributor> contributorList = new ArrayList<Contributor>();
		for (int i = 0; i < result.getContributorCount(); i++) {
			String start = i+1+"";
			String end = i+2+"";
			String cstring = null;
			if (start.equals(contributorCount)) {
				cstring = StringUtils.substring(content, StringUtils.indexOf(content, "Contributor "+start), 
						StringUtils.indexOf(content, "Deconvolution: all components"));
			} else {
				cstring = StringUtils.substring(content, StringUtils.indexOf(content, "Contributor "+start), 
						StringUtils.indexOf(content, "Contributor "+end));
			}
			 
			String[] array = cstring.split("\\r?\\n");
			List<String> filterList = new ArrayList<>();
			for (int j = 0; j < array.length; j++) {
				if (!array[j].contains("Contributor")
					&& !array[j].contains("Locus Genotype Weight Component")
					&& !array[j].contains("Seite")
					) {
					String arr = StringUtils.replace(array[j], ", ", ",");
					if (StringUtils.trimToNull(StringUtils.substringAfterLast(arr, "%")) == null) {
						arr += " 空";
					}
					filterList.add(arr);
				}
			}
			
			Contributor contributor = new Contributor();
			contributor.setContributorName("Contributor " + start);
			contributor.setContributorWeight("");
			List<ContributorGene> contributorGeneList = new ArrayList<ContributorGene>();
			
			String preString = "";
			Map<String, String> map3 = new HashMap<String, String>();
			for (String s : filterList) {
				String[] split = s.split("\\s+");
				
				ContributorGene gene = new ContributorGene();
				if (split.length == 4) {
					gene.setGeneName(split[0]);
					
					GeneAllele allele = new GeneAllele();
					allele.setAllele(split[1]);
					allele.setWeights(split[2]);
					if ("空".equals(split[3])) {
						allele.setNinetyNinePercentWeights(null);
					} else {
						allele.setNinetyNinePercentWeights(split[3]);
					}
					List<GeneAllele> geneAlleleList = new ArrayList<GeneAllele>();
					geneAlleleList.add(allele);
					gene.setGeneAlleleList(geneAlleleList);
					contributorGeneList.add(gene);
				} else if (split.length == 3) {
					// 轮空 
					String preGeneName = preString.split("\\s+")[0];
					map3.put(preGeneName, preGeneName + " " + s);
				} else if (split.length == 5) {
					gene.setGeneName(split[0] + " " + split[1]);
					
					GeneAllele allele = new GeneAllele();
					allele.setAllele(split[2]);
					allele.setWeights(split[3]);
					if ("空".equals(split[4])) {
						allele.setNinetyNinePercentWeights(null);
					} else {
						allele.setNinetyNinePercentWeights(split[4]);
					}
					List<GeneAllele> geneAlleleList = new ArrayList<GeneAllele>();
					geneAlleleList.add(allele);
					gene.setGeneAlleleList(geneAlleleList);
					contributorGeneList.add(gene);
				} else {
					System.out.println("error:"+split);
				}
				preString = s;
			}
			
			//补齐
			for (ContributorGene reGene : contributorGeneList) {
				List<GeneAllele> reList = reGene.getGeneAlleleList();
				String reGeneName = reGene.getGeneName();
				for (Iterator<Map.Entry<String, String>> it = map3.entrySet().iterator(); it.hasNext();) {
					Map.Entry<String, String> entry = it.next();
					final String key = entry.getKey();
					String value = entry.getValue();
					String[] valuesplit = value.split("\\s+");
					if (key.equals(reGeneName)) {
						GeneAllele allele = new GeneAllele(); 
						allele.setAllele(valuesplit[1]);
						allele.setWeights(valuesplit[2]);
						if ("空".equals(valuesplit[3])) {
							allele.setNinetyNinePercentWeights(null);
						} else {
							allele.setNinetyNinePercentWeights(valuesplit[3]);
						}
						
						reList.add(allele);
					}
				}
				
			}
			
			contributor.setContributorGeneList(contributorGeneList);
			contributor.setGeneCount(contributorGeneList.size());
			contributorList.add(contributor);
		}
		
		result.setContributorList(contributorList);
		return result;
	}
	
}
