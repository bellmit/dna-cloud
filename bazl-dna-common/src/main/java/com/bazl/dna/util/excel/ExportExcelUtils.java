package com.bazl.dna.util.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.util.ClassesUtil;
import com.bazl.dna.util.DataUtils;
import com.bazl.dna.util.DateUtil;

/***
 * <p>Description: Excel导出</p>
 * @author lrm
 * @date 2018年10月17日
 */
public class ExportExcelUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExportExcelUtils.class);
	
	private ExportExcelUtils() {
	}

	/***
	 * Excel导出
	 * @param sheetName		Sheet名称
	 * @param columnName	表格列名
	 * @param dataList		列表数据
	 * @param response		响应输出
	 * @return
	 */
	public static void exportExcel(String sheetName, String[] columnName, 
			String[] titleKey, List<?> dataList,
			String filePath) {
		try {
			HSSFWorkbook workbook = exportExcel(sheetName, columnName, titleKey, dataList);
			
			FileOutputStream fileOut = new FileOutputStream(new File(filePath + File.separator + sheetName));
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			LOGGER.error("FileNotFoundException: ", e);
		} catch (IOException e) {
			LOGGER.error("IOException: ", e);
		} catch (Exception e) {
			LOGGER.error("Exception: ", e);
		}
	}
	
	/**
	 * 
	 * @param sheetName
	 * @param titleJson
	 * @param mixJson
	 * @param contributorList
	 * @param possibilityList
	 * @param sampleList
	 * @return
	 */
	public static HSSFWorkbook mixExportExcel(String sheetName, JSONObject titleJson,
			JSONObject mixJson, JSONArray contributorList, JSONArray possibilityList,
			JSONArray sampleList) {
		HSSFWorkbook workbook = new HSSFWorkbook(); 
		HSSFSheet sheet = workbook.createSheet(sheetName); 

		int genePosition = 7; // 基因座位置 第7列开始
		JSONArray geneArray = null;
		
		// 标题
		// --------------------------------------------
		if (titleJson != null) {
			int titleNumber = 0;
			HSSFCellStyle titleCellStyle = buildHSSFCellStyleForTitle(workbook, IndexedColors.PALE_BLUE);
			HSSFFont titleFont = buildFont(workbook);
			titleFont.setBold(true);
			titleCellStyle.setFont(titleFont);
			
			
			HSSFRow titleRow = sheet.createRow(titleNumber);
			setSampleRow(titleRow, titleJson, titleCellStyle);
			
			geneArray = titleJson.getJSONArray("gene");
			for (int i = 0; i < geneArray.size(); i++) {
				HSSFCell cell = titleRow.createCell(i+genePosition); 
				cell.setCellType(CellType.STRING); 
				cell.setCellValue(new HSSFRichTextString(geneArray.getString(i)));
				cell.setCellStyle(titleCellStyle); 
			}
		}
		
		
		// 混合样本
		// --------------------------------------------
		if (mixJson != null) {
			int mixNumber = 1;
			HSSFCellStyle cellStyle = buildHSSFCellStyleForTitle(workbook, IndexedColors.YELLOW);
			cellStyle.setFont(buildFont(workbook));
			
			HSSFRow row = sheet.createRow(mixNumber);
			setSampleRow(row, mixJson, cellStyle);
			
			JSONObject mixGeneJson = mixJson.getJSONObject("gene");
			for (int i = 0; i < geneArray.size(); i++) {
				HSSFCell cell = row.createCell(i+genePosition);
				cell.setCellType(CellType.STRING);
				cell.setCellValue(new HSSFRichTextString(mixGeneJson.getString(geneArray.getString(i))));
				cell.setCellStyle(cellStyle); 
			}
		}
		
		// 同案样本分型 已知贡献者
		// --------------------------------------------
		if (!contributorList.isEmpty()) {
			int sameRowNumber = 2;
			HSSFCellStyle cellStyle = buildHSSFCellStyleForTitle(workbook, IndexedColors.LIGHT_CORNFLOWER_BLUE);
			cellStyle.setFont(buildFontColor(workbook));
			
			for (int k = 0; k < contributorList.size(); k++) {
				HSSFRow row = sheet.createRow(k+sameRowNumber);
				HSSFCell c0 = row.createCell(0);
				c0.setCellValue("同案样本分型");
				c0.setCellStyle(cellStyle);
				
				JSONObject sampleGeneJson = contributorList.getJSONObject(k).getJSONObject("gene");
				for (int i = 0; i < geneArray.size(); i++) {
					HSSFCell cell = row.createCell(i+genePosition);
					cell.setCellType(CellType.STRING);
					cell.setCellValue(new HSSFRichTextString(sampleGeneJson.getString(geneArray.getString(i))));
					cell.setCellStyle(cellStyle); 
				}
			}
			
			sheet.addMergedRegion(new CellRangeAddress(sameRowNumber, sameRowNumber + contributorList.size() - 1, 0, genePosition - 1)); //合并单元格
		}
		
		// 可能贡献者位点（拆分）
		// --------------------------------------------
		if (!possibilityList.isEmpty()) {
			int pbNumber = 3 + contributorList.size();
			
			HSSFCellStyle cellStyle = buildHSSFCellStyleForTitle(workbook, IndexedColors.TAN);
			cellStyle.setFont(buildFontColor(workbook));
			
			for (int k = 0; k < possibilityList.size(); k++) {
				HSSFRow row = sheet.createRow(k+pbNumber);
				HSSFCell c0 = row.createCell(0);
				c0.setCellValue("可能贡献者位点（拆分）");
				c0.setCellStyle(cellStyle);
				
				JSONObject sampleGeneJson = possibilityList.getJSONObject(k).getJSONObject("gene");
				for (int i = 0; i < geneArray.size(); i++) {
					HSSFCell cell = row.createCell(i+genePosition);
					cell.setCellType(CellType.STRING);
					cell.setCellValue(new HSSFRichTextString(sampleGeneJson.getString(geneArray.getString(i))));
					cell.setCellStyle(cellStyle); 
				}
			}
			
			sheet.addMergedRegion(new CellRangeAddress(pbNumber, pbNumber + possibilityList.size() - 1, 0, genePosition - 1)); //合并单元格
		}
		
		// 样本列表
		// --------------------------------------------
		if (!sampleList.isEmpty()) {
			int sampleRowNumber = 3 + contributorList.size() + possibilityList.size();
			HSSFCellStyle cellStyle = buildHSSFCellStyleForContent(workbook);
			cellStyle.setFont(buildFont(workbook));
			
			for (int i = 0; i < sampleList.size(); i++) {
				JSONObject sampleJson = sampleList.getJSONObject(i);
				HSSFRow row = sheet.createRow(i + sampleRowNumber);
				setSampleRow(row, sampleJson, cellStyle);
				
				JSONObject sampleGeneJson = sampleJson.getJSONObject("gene");
				for (int j = 0; j < geneArray.size(); j++) {
					HSSFCell cell = row.createCell(j+genePosition);
					cell.setCellType(CellType.STRING);
					cell.setCellValue(new HSSFRichTextString(sampleGeneJson.getString(geneArray.getString(j))));
					cell.setCellStyle(cellStyle); 
				}
			}
		}
		
		
		adaptor(sheet, genePosition + geneArray.size());
		return workbook;
	}
	
	public static void adaptor(HSSFSheet sheet, int columnNum) {
		// 让列宽随着导出的列长自动适应
		for (int colNum = 0; colNum < columnNum; colNum++) {
			int columnWidth = sheet.getColumnWidth(colNum) / 256;
			HSSFRow currentRow = sheet.getRow(sheet.getLastRowNum());
			columnWidth = getColumnWidth(currentRow,colNum,columnWidth);
			if (columnWidth > 100) {
				columnWidth = 100;
			}
			sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
		}
	}
	
	/***
	 * Excel导出
	 * @param sheetName		Sheet名称
	 * @param columnName	表格列名
	 * @param dataList		列表数据
	 * @return
	 */
	public static HSSFWorkbook exportExcel(String sheetName, String[] columnName, 
			String[] titleKey, List<?> objectList) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对象
			HSSFSheet sheet = workbook.createSheet(sheetName); // 创建工作表
	
			// 设置标题单元格和内容单元格样式
			HSSFCellStyle titleCellStyle = buildHSSFCellStyleForTitle(workbook, IndexedColors.PALE_BLUE);
			titleCellStyle.setFont(buildFont(workbook));
			HSSFCellStyle contextStyle = buildHSSFCellStyleForContent(workbook);
			contextStyle.setFont(buildFont(workbook));
	
			// 定义所需列数
			int columnNum = columnName.length;
			Row row = sheet.createRow(0);
	
			// 设置标题
			for (int i = 0; i < columnNum; i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(columnName[i]);
				cell.setCellStyle(titleCellStyle);
			}
	
			// 设置内容
			for (int i = 0; i < objectList.size(); i++) {
				row = sheet.createRow(i+1);
				Object object = objectList.get(i);
				Field[] field = object.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
				String[] pname = new String[field.length];
				for (int j = 0; j < field.length; j++) {
					pname[j] = field[j].getName();
				}
	
				for (int n = 0; n < titleKey.length; n++) {
					for (int j = 0; j < pname.length; j++) {
						String[] str = titleKey[n].split("\\.");
						String name = str[0];
						if (name.equals(pname[j])) {
							/* 将属性名字第一个字符串设置成大写 */
							char[] c = name.toCharArray();
							char ch = Character.toUpperCase(c[0]);
							c[0] = ch;
							name = new String(c);
							Type type = field[j].getGenericType(); // 获取属性的类型
							//判断是否为基本类型或Bean型
							if (type.equals(String.class)) {
								Method m = object.getClass().getMethod("get" + name);
								String value = (String) m.invoke(object); 
								Cell cell = row.createCell(n);
								cell.setCellValue(value);
								cell.setCellStyle(contextStyle);
							} else if (type.equals(Integer.class)) {
								Method m = object.getClass().getMethod("get" + name);
								Integer value = (Integer) m.invoke(object); 
								if (value == null) {
									value = 0;
								}
								Cell cell = row.createCell(n);
								cell.setCellValue(value);
								cell.setCellStyle(contextStyle);
							} else if (type.equals(java.math.BigDecimal.class)) {
								Method m = object.getClass().getMethod("get" + name);
								java.math.BigDecimal value = (java.math.BigDecimal) m.invoke(object);
								double d = 0l;
								if (value != null) {
									d = value.doubleValue();
								}
								Cell cell = row.createCell(n);
								cell.setCellValue(d);
								cell.setCellStyle(contextStyle);
							} else if (type.equals(Double.class)) {
								Method m = object.getClass().getMethod("get" + name);
								Double value = (Double) m.invoke(object);
								if (value == null) {
									value = 0.0;
								}
								Cell cell = row.createCell(n);
								cell.setCellValue(value);
								cell.setCellStyle(contextStyle);
							} else if (type.equals(Long.class)) {
								Method m = object.getClass().getMethod("get" + name);
								Long value = (Long) m.invoke(object);
								if (value == null) {
									value = 0L;
								}
								Cell cell = row.createCell(n);
								cell.setCellValue(value);
								cell.setCellStyle(contextStyle);
							} else if (type.equals(java.sql.Timestamp.class)) {
								Method m = object.getClass().getMethod("get" + name);
								String dateString = StringUtils.trimToNull(DataUtils.getString(m.invoke(object)));
								String value = StringUtils.EMPTY;
								if (dateString != null) {
									value = DateUtil.dateToString(DateUtil.stringToDate(dateString, DateUtil.FULL_TIME_FORMAT), DateUtil.FULL_TIME_FORMAT);
								}
								Cell cell = row.createCell(n);
								cell.setCellValue(value);
								cell.setCellStyle(contextStyle);
							} else if(type.equals(java.util.Date.class)){
								Method m = object.getClass().getMethod("get" + name);
								java.util.Date d = (java.util.Date) m.invoke(object);
								String value = StringUtils.EMPTY;
								if (d != null) {
									value = DateUtil.dateToString(d, DateUtil.FULL_TIME_FORMAT);
								}
								Cell cell = row.createCell(n);
								cell.setCellValue(value);
								cell.setCellStyle(contextStyle);
							} else if(type.equals(java.time.LocalDateTime.class)){
								Method m = object.getClass().getMethod("get" + name);
								java.time.LocalDateTime d = (java.time.LocalDateTime) m.invoke(object);
								String value = StringUtils.EMPTY;
								if (d != null) {
									value = DateUtil.dateToString(DateUtil.localDateTimeToDate(d), DateUtil.FULL_TIME_FORMAT);
								}
								Cell cell = row.createCell(n);
								cell.setCellValue(value);
								cell.setCellStyle(contextStyle);
							} else {
								if (!ClassesUtil.isAtomic(type.getClass())) {
									String value = String.valueOf(invoke(object, titleKey[n]));
									Cell cell = row.createCell(n);
									cell.setCellValue(value);
									cell.setCellStyle(contextStyle);
								} else {
									Cell cell = row.createCell(n);
									cell.setCellValue("--");
									cell.setCellStyle(contextStyle);
								}
							}
							break;
						}
					}
				}
	
			}
	
			adaptor(sheet, columnNum);
			return workbook;
		} catch (Exception e) {
			LOGGER.error("Error exportExcel: ", e);
		}
		return null;
	}
	
	/**
	 * 反射获取对象
	 * @param object
	 * @param titleKey
	 * @return Object
	 */
	private static Object invoke(Object object, String arrayString) {
		try {
			String[] str = arrayString.split("\\.");
			String s1 = DataUtils.builderName(StringUtils.substringBefore(arrayString, "."));
			String s2 = DataUtils.builderName(StringUtils.substringAfter(arrayString, "."));
			Method m1 = object.getClass().getMethod("get" + s1);
			Object object1 = m1.invoke(object);
			if (object1 == null) {
				return null;
			}
			
			if (str.length == 2) {
				Method m2 = object1.getClass().getMethod("get" + s2);
				return m2.invoke(object1);
			} else {
				// 获取子对象
				return invoke(object1, s2);
			}
		} catch (Exception e) {
			LOGGER.error("Exception: ", e);
		}
		return null;
	}
	
	/***
	 * 根据单元格内容计算单元格宽度
	 * @param currentRow	当前行
	 * @param colNum		当前列号
	 * @param columnWidth	单元格宽度
	 * @return
	 */
	private static int getColumnWidth(HSSFRow currentRow,int colNum,int columnWidth){
		if (currentRow.getCell(colNum) != null) {
			// 取得当前的单元格
			HSSFCell currentCell = currentRow.getCell(colNum);
			// 如果当前单元格类型为字符串
			if (CellType.STRING.equals(currentCell.getCellType())) {
				int length = currentCell.getStringCellValue().getBytes(Charset.defaultCharset()).length;
				if (columnWidth < length) {
					// 将单元格里面值大小作为列宽度
					columnWidth = length;
				}
			}
		}
		return columnWidth;
	}

	/***
	 * 构造单元格样式-内容
	 * @param workbook	工作薄
	 * @return	单元格样式
	 */
	private static HSSFCellStyle buildHSSFCellStyleForContent(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		
		// 设置字体:
		HSSFFont font = buildFont(workbook);
		style.setFont(font);

		buildStyle(style);
		
		// 设置自动换行
		style.setWrapText(false);
		style.setLocked(false);	//单元格锁定
		return style;
	}

	/***
	 * 构造单元格样式-标题
	 * @param workbook	工作薄
	 * @return 单元格样式
	 */
	private static HSSFCellStyle buildHSSFCellStyleForTitle(HSSFWorkbook workbook, IndexedColors colors) {
		HSSFCellStyle style = workbook.createCellStyle();

		// 设置背景色:
		style.setFillForegroundColor(colors.getIndex());
		style.setFillPattern(FillPatternType.forInt(FillPatternType.SOLID_FOREGROUND.getCode()));

		buildStyle(style);
		
		// 设置自动换行:
		style.setWrapText(false);
		style.setLocked(true);	//单元格锁定
		return style;
	}
	
	public static void buildStyle(HSSFCellStyle style) {
		// 设置边框:
		style.setBorderBottom(BorderStyle.valueOf(BorderStyle.THIN.getCode()));	// 下边框
		style.setBorderLeft(BorderStyle.valueOf(BorderStyle.THIN.getCode()));	// 左边框
		style.setBorderTop(BorderStyle.valueOf(BorderStyle.THIN.getCode()));	// 上边框
		style.setBorderRight(BorderStyle.valueOf(BorderStyle.THIN.getCode()));	// 右边框

		// 设置边框颜色:
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());	// 设置底边框颜色
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());	// 设置左边框颜色
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());	// 设置顶边框颜色
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());	// 设置右边框颜色

		// 设置居中:
		style.setAlignment(HorizontalAlignment.forInt(HorizontalAlignment.CENTER.getCode()));		// 水平居中
		style.setVerticalAlignment(VerticalAlignment.forInt(VerticalAlignment.CENTER.getCode())); // 垂直居中对齐
	}
	
	/**
	 * 设置字体
	 * @param workbook
	 * @return
	 */
	private static HSSFFont buildFont(HSSFWorkbook workbook) {
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);	
		font.setFontName("宋体");					
		return font;
	}
	
	/**
	 * 红色字体
	 * @param workbook
	 * @return
	 */
	private static HSSFFont buildFontColor(HSSFWorkbook workbook) {
		HSSFFont font = buildFont(workbook);
		font.setColor(IndexedColors.RED.getIndex());
		return font;
	}
	
	
	private static void setSampleRow(HSSFRow row, JSONObject json, HSSFCellStyle cellStyle) {
		HSSFCell cell0 = row.createCell(0); 
		cell0.setCellType(CellType.STRING); 
		cell0.setCellValue(new HSSFRichTextString(json.getString("sampleCode")));
		cell0.setCellStyle(cellStyle);
		
		HSSFCell cell1 = row.createCell(1); 
		cell1.setCellType(CellType.STRING); 
		cell1.setCellValue(new HSSFRichTextString(json.getString("sampleName")));
		cell1.setCellStyle(cellStyle);
		
		HSSFCell cell2 = row.createCell(2); 
		cell2.setCellType(CellType.STRING); 
		cell2.setCellValue(new HSSFRichTextString(json.getString("card")));
		cell2.setCellStyle(cellStyle);
		
		HSSFCell cell3 = row.createCell(3); 
		cell3.setCellType(CellType.STRING); 
		cell3.setCellValue(new HSSFRichTextString(json.getString("type")));
		cell3.setCellStyle(cellStyle);
		
		HSSFCell cell4 = row.createCell(4); 
		cell4.setCellType(CellType.STRING); 
		cell4.setCellValue(new HSSFRichTextString(json.getString("server")));
		cell4.setCellStyle(cellStyle);
		
		HSSFCell cell5 = row.createCell(5); 
		cell5.setCellType(CellType.STRING); 
		cell5.setCellValue(new HSSFRichTextString(json.getString("identify")));
		cell5.setCellStyle(cellStyle);
		
		HSSFCell cell6 = row.createCell(6); 
		cell6.setCellType(CellType.STRING); 
		cell6.setCellValue(new HSSFRichTextString(json.getString("num")));
		cell6.setCellStyle(cellStyle);
	}

}