package com.tang.tools;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelTools {

	private ExcelTools() {

	}

	private static void createTitle(HSSFWorkbook workbook, HSSFSheet sheet, String[] heads, String title) {
		HSSFRow row = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, heads.length - 1));

		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);

		HSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 12);
		style.setFont(font);

		HSSFCell cell = row.createCell(0);
		cell.setCellValue(title);
		cell.setCellStyle(style);
	}

	/**
	 * 
	 * @param workbook
	 * @param sheet
	 * @param heads
	 */
	public static void createHeader(HSSFWorkbook workbook, HSSFSheet sheet, String[] heads) {
		HSSFRow row = sheet.createRow(0);

		// 设置为居中加粗
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setBold(true);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setFont(font);
		style.setBorderBottom(BorderStyle.THIN); 	//下边框
		style.setBorderLeft(BorderStyle.THIN);		//左边框
		style.setBorderTop(BorderStyle.THIN);		//上边框
		style.setBorderRight(BorderStyle.THIN);		//右边框

		HSSFCell cell;
		for (int i = 0, len = heads.length; i < len; i++) {
			cell = row.createCell(i);
			cell.setCellValue(heads[i]);
			cell.setCellStyle(style);
		}
	}

	/**
	 * excel 导出数据
	 * 
	 * @param sheetName
	 * @param heads
	 * @param datas
	 * @param out
	 * @return
	 */
	public static boolean export(String sheetName, String title, String[] heads, Object[][] datas, Integer[] widths,
			OutputStream out) {

		Object value = null;
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFCellStyle style = workbook.createCellStyle();
			style.setBorderBottom(BorderStyle.THIN); // 下边框
			style.setBorderLeft(BorderStyle.THIN); // 左边框
			style.setBorderTop(BorderStyle.THIN); // 上边框
			style.setBorderRight(BorderStyle.THIN); // 右边框
			style.setWrapText(true);
			style.setVerticalAlignment(VerticalAlignment.CENTER);

			HSSFSheet sheet = workbook.createSheet(sheetName);
			// 设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
			for (int i = 0; i < widths.length; i++) {
				sheet.setColumnWidth(i, widths[i] * 256);
			}

			createTitle(workbook, sheet, heads, title);
			createHeader(workbook, sheet, heads);

			// 新增数据行，并且设置单元格数据
			int rowNum = 1;
			for (int i = 0; i < datas.length; i++) {
				HSSFRow row = sheet.createRow(rowNum);
				HSSFCell cell;
				for (int j = 0; j < datas[i].length; j++) {
					value = datas[i][j];
					cell = row.createCell(j);
					if (value instanceof Integer || value instanceof Long || value instanceof Short)
						cell.setCellValue((Integer) value);
					else if (value instanceof Double || value instanceof Float)
						cell.setCellValue((Double) value);
					else {
						if (value == null || "null".equals(value))
							value = "";
						cell.setCellValue(String.valueOf(value));
					}
					cell.setCellStyle(style);
				}
				rowNum++;
			}

			workbook.write(out);
			out.flush();
			// out.close();
			// workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	public void setDate(HSSFWorkbook workbook, HSSFRow row, String data) {
		// 设置日期格式
		HSSFCellStyle style = workbook.createCellStyle();
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

		HSSFCell cell = row.createCell(4);
		cell.setCellValue(data);
		cell.setCellStyle(style);
	}

	public void write(HSSFWorkbook workbook, String filepath) {

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filepath);
			workbook.write(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("写入的文件不存在");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("写入数据失败:" + e.getMessage());
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
