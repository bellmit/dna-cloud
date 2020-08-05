package com.bazl.dna.util.excel;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * <p>
 * Description: Excel导入
 * </p>
 * 
 * @author lrm
 * @date 2018年10月17日
 */
public class ImportExcelUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImportExcelUtils.class);

	private ImportExcelUtils() {
	}

	/***
	 * 读取2007之前版本的Excel数据
	 * 
	 * @param is       Excel文件输入流
	 * @param sheetAt  Sheet所在页，默认第一页0
	 * @param titleRow 标题所在行，默认首行0
	 * @return
	 */
	public static ExcelData readXlsExcelData(InputStream is, Integer sheetAt, Integer titleRow) {
		ExcelData excelData = new ExcelData();
		HSSFSheet sheet = null;
		HSSFRow row;
		try (
			POIFSFileSystem fs = new POIFSFileSystem(is); 
			HSSFWorkbook wb = new HSSFWorkbook(fs);
		) {

			// 读取Sheet, 默认0
			sheet = wb.getSheetAt(sheetAt == null ? 0 : sheetAt);
			/***
			 * 1、获取Sheet名称
			 */
			String sheetName = sheet.getSheetName().trim();
			excelData.setSheetName(sheetName);

			/***
			 * 2、获取标题行，默认首行0
			 */
			row = sheet.getRow(titleRow == null ? 0 : titleRow);
			// 标题总列数
			int colNum = row.getPhysicalNumberOfCells();
			String[] title = new String[colNum];
			for (int i = 0; i < colNum; i++) {
				Object val = getHSSFCellValue(row.getCell((short) i));
				title[i] = val != null ? val.toString().trim() : "";
			}
			excelData.setColumnNames(title);

			/***
			 * 3、获取数据行，正文内容默认从标题行+1开始
			 */
			int rowNum = sheet.getLastRowNum();
			// 由于第0行和第一行已经合并了 在这里索引从2开始
			// 正文内容应该从第二行开始,第一行为表头的标题
			List<Object[]> dataList = Lists.newArrayList();
			for (int i = titleRow + 1; i <= rowNum; i++) {
				Object[] objs = new Object[colNum];
				row = sheet.getRow(i);
				int nullCells = checkNullCells(row);
				if (row == null || nullCells >= colNum)
					continue;
				int j = 0;
				while (j < colNum) {
					HSSFCell cell = row.getCell((short) j);
					Object val = null;
					if (cell != null && cell.getCellType() != CellType.BLANK) {
						val = getHSSFCellValue(cell);
					}
					objs[j] = val != null ? val.toString().trim() : val;
					j++;
				}
				dataList.add(objs);
			}
			excelData.setDataList(dataList);
		} catch (IOException e) {
			LOGGER.error("Error readXlsExcelData: ", e);
		}
		return excelData;
	}

	/***
	 * 读取2007之后版本的Excel数据
	 * 
	 * @param is       Excel文件输入流
	 * @param sheetAt  Sheet所在页，默认第一页0
	 * @param titleRow 标题所在行，默认首行0
	 * @return
	 */
	public static ExcelData readXlsxExcelData(InputStream is, Integer sheetAt, Integer titleRow) {
		ExcelData excelData = new ExcelData();
		try (XSSFWorkbook wb = new XSSFWorkbook(is);) {

			// 读取Sheet, 默认0
			XSSFSheet sheet = wb.getSheetAt(sheetAt == null ? 0 : sheetAt);
			/***
			 * 1、获取Sheet名称
			 */
			String sheetName = sheet.getSheetName().trim();
			excelData.setSheetName(sheetName);

			/***
			 * 2、获取标题行，默认首行0
			 */
			XSSFRow row = sheet.getRow(titleRow == null ? 0 : titleRow);
			// 标题总列数
			int colNum = row.getPhysicalNumberOfCells();
			String[] title = new String[colNum];
			for (int i = 0; i < colNum; i++) {
				Object val = getXSSFCellValue(row.getCell((short) i));
				title[i] = val != null ? val.toString().trim() : "";
			}
			excelData.setColumnNames(title);

			/***
			 * 3、获取数据行，正文内容默认从标题行+1开始
			 */
			int rowNum = sheet.getLastRowNum();
			// 由于第0行和第一行已经合并了 在这里索引从2开始
			// 正文内容应该从第二行开始,第一行为表头的标题
			List<Object[]> dataList = Lists.newArrayList();
			for (int i = titleRow + 1; i <= rowNum; i++) {
				Object[] objs = new Object[colNum];
				row = sheet.getRow(i);
				int nullCells = checkNullCells(row);
				if (row == null || nullCells >= colNum)
					continue;
				int j = 0;
				while (j < colNum) {
					XSSFCell cell = row.getCell((short) j);
					Object val = null;
					if (cell != null) {
						val = getXSSFCellValue(cell);
					}
					objs[j] = val != null ? val.toString().trim() : val;
					j++;
				}
				dataList.add(objs);
			}
			excelData.setDataList(dataList);
		} catch (IOException e) {
			LOGGER.error("Error readXlsxExcelData: ", e);
		}
		return excelData;
	}

	/***
	 * 读取Cell的值
	 * 
	 * @param cell
	 * @return
	 */
	private static Object getHSSFCellValue(HSSFCell cell) {
		Object value = null;
		switch (cell.getCellType()) {
		case STRING:
			value = cell.getStringCellValue().trim();
			break;
		case _NONE:
			value = null;
			break;
		case BOOLEAN:
			value = cell.getBooleanCellValue();
			break;
		case BLANK:
			value = null;
			break;
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				value = com.bazl.dna.util.DateUtil.dateToString(date, com.bazl.dna.util.DateUtil.DEFAULT_FORMAT);
			} else if (String.valueOf(cell.getNumericCellValue()).contains(".")) {
				DecimalFormat df = new DecimalFormat("#");
				value = df.format(cell.getNumericCellValue());
			} else {
				value = (cell + "").trim();
			}
			break;
		case FORMULA:// 公式型
			// 读公式计算值
			value = cell.getCellFormula();
			if (value != null && StringUtils.isNotEmpty(value.toString())
					&& value.toString().toUpperCase().contains("DATE")) {
				Date date = cell.getDateCellValue();
				value = com.bazl.dna.util.DateUtil.dateToString(date, com.bazl.dna.util.DateUtil.DEFAULT_FORMAT);
			} else {
				value = String.valueOf(cell.getNumericCellValue());
				if (value.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
					value = cell.getRichStringCellValue().toString();
				}
			}
			break;
		default:
			value = cell.toString();
		}
		return value;
	}

	/***
	 * 读取Cell的值
	 * 
	 * @param cell
	 * @return
	 */
	private static Object getXSSFCellValue(XSSFCell cell) {
		Object value = null;
		switch (cell.getCellType()) {
		case STRING:
			value = cell.getStringCellValue();
			break;
		case _NONE:
			value = "";
			break;
		case BOOLEAN:
			value = cell.getBooleanCellValue();
			break;
		case BLANK:
			value = null;
			break;
		case NUMERIC:
			// 处理double类型的 1.0===》1
			if (DateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				value = com.bazl.dna.util.DateUtil.dateToString(date, com.bazl.dna.util.DateUtil.DEFAULT_FORMAT);
			} else if (String.valueOf(cell.getNumericCellValue()).contains(".")) {
				DecimalFormat df = new DecimalFormat("#");
				value = df.format(cell.getNumericCellValue());
			} else {
				value = (cell + "").trim();
			}
			break;
		default:
			value = cell.toString();
		}
		return value;
	}

	/***
	 * 判断空单元格数
	 * 
	 * @param hssfRow
	 * @return
	 */
	private static int checkNullCells(XSSFRow xssfRow) {
		int num = 0;
		Iterator<Cell> cellItr = xssfRow.iterator();
		while (cellItr.hasNext()) {
			Cell c = cellItr.next();
			if (c.getCellType() == CellType.BLANK) {
				num++;
			}
		}
		return num;
	}

	/***
	 * 判断空单元格数
	 * 
	 * @param hssfRow
	 * @return
	 */
	private static int checkNullCells(HSSFRow hssfRow) {
		int num = 0;
		Iterator<Cell> cellItr = hssfRow.iterator();
		while (cellItr.hasNext()) {
			Cell c = cellItr.next();
			if (c.getCellType() == CellType.BLANK) {
				num++;
			}
		}
		return num;
	}

}