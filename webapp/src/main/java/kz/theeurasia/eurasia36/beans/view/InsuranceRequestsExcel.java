package kz.theeurasia.eurasia36.beans.view;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import com.lapsa.insurance.domain.InsuranceRequest;

public class InsuranceRequestsExcel {

    private static final BorderStyle DEFAULT_BORDER_STYLE = BorderStyle.THIN;

    private List<InsuranceRequest> list;
    private HSSFWorkbook workbook;
    private HSSFSheet sheet;

    private HSSFCellStyle headerCellStyle;
    private HSSFCellStyle textCellStyle;
    private HSSFCellStyle amountCellType;
    private HSSFCellStyle dateTimeCellType;
    private HSSFCellStyle numericCellType;

    public InsuranceRequestsExcel(List<InsuranceRequest> list) {
	this.list = Collections.unmodifiableList(list);
    }

    public HSSFWorkbook asExcellWorkbook() {
	if (workbook == null) {
	    workbook = new HSSFWorkbook();

	    sheet = workbook.createSheet("FirstSheet");

	    HSSFRow rowhead = sheet.createRow(1);

	    createHeaderCell(rowhead, 1, "Продукт");
	    createHeaderCell(rowhead, 2, "Тип");
	    createHeaderCell(rowhead, 3, "Номер");
	    createHeaderCell(rowhead, 4, "Дата создания");
	    createHeaderCell(rowhead, 5, "Дата обновления");
	    createHeaderCell(rowhead, 6, "Стоимость премии");
	    createHeaderCell(rowhead, 7, "Имя заявителя");
	    createHeaderCell(rowhead, 8, "Email заявителя");
	    createHeaderCell(rowhead, 9, "Телефон заявителя");

	    for (int i = 0; i < list.size(); i++) {
		InsuranceRequest ir = list.get(i);
		HSSFRow row = sheet.createRow(i + 2);
		createTextCell(row, 1, ir.getProductType());
		createTextCell(row, 2, ir.getType());
		createNumberCell(row, 3, ir.getId());
		createDateTimeCell(row, 4, ir.getCreated());
		createDateTimeCell(row, 5, ir.getUpdated());
		createAmountCell(row, 6, ir.getProduct().getCalculation().getCalculatedPremiumCost());
		createTextCell(row, 7, ir.getRequester().getName());
		createTextCell(row, 8, ir.getRequester().getEmail());
		createTextCell(row, 9,
			(ir.getRequester().getPhone() != null ? ir.getRequester().getPhone().getFormatted() : null));
	    }

	    for (int i = 1; i <= 9; i++)
		sheet.autoSizeColumn(i);
	}
	return workbook;
    }

    private HSSFCell createNumberCell(HSSFRow row, int number, int numberValue) {
	if (numericCellType == null) {
	    numericCellType = workbook.createCellStyle();
	    numericCellType.setVerticalAlignment(VerticalAlignment.TOP);
	    numericCellType.setBorderLeft(DEFAULT_BORDER_STYLE);
	    numericCellType.setBorderTop(DEFAULT_BORDER_STYLE);
	    numericCellType.setBorderRight(DEFAULT_BORDER_STYLE);
	    numericCellType.setBorderBottom(DEFAULT_BORDER_STYLE);
	}

	HSSFCell cell = row.createCell(number, CellType.NUMERIC);
	cell.setCellStyle(numericCellType);
	cell.setCellValue(numberValue);
	return cell;
    }

    public InputStream asExcelWorkbookInputStream() {
	try {
	    HSSFWorkbook workbook = asExcellWorkbook();
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    workbook.write(baos);
	    return new ByteArrayInputStream(baos.toByteArray());
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    private HSSFCell createTextCell(HSSFRow row, int number, String text) {
	if (textCellStyle == null) {
	    textCellStyle = workbook.createCellStyle();
	    textCellStyle.setAlignment(HorizontalAlignment.LEFT);
	    textCellStyle.setVerticalAlignment(VerticalAlignment.TOP);
	    textCellStyle.setWrapText(false);
	    textCellStyle.setBorderLeft(DEFAULT_BORDER_STYLE);
	    textCellStyle.setBorderTop(DEFAULT_BORDER_STYLE);
	    textCellStyle.setBorderRight(DEFAULT_BORDER_STYLE);
	    textCellStyle.setBorderBottom(DEFAULT_BORDER_STYLE);
	}
	HSSFCell cell = row.createCell(number, CellType.STRING);
	cell.setCellStyle(textCellStyle);
	cell.setCellValue(text);
	return cell;
    }

    private HSSFCell createTextCell(HSSFRow row, int number, Object object) {
	return createTextCell(row, number, object.toString());
    }

    private HSSFCell createHeaderCell(HSSFRow row, int number, String caption) {
	if (headerCellStyle == null) {
	    headerCellStyle = workbook.createCellStyle();
	    headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
	    headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	    headerCellStyle.setWrapText(true);
	    HSSFFont headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerCellStyle.setFont(headerFont);
	    headerCellStyle.setBorderLeft(DEFAULT_BORDER_STYLE);
	    headerCellStyle.setBorderTop(DEFAULT_BORDER_STYLE);
	    headerCellStyle.setBorderRight(DEFAULT_BORDER_STYLE);
	    headerCellStyle.setBorderBottom(DEFAULT_BORDER_STYLE);
	}
	HSSFCell cell = row.createCell(number, CellType.STRING);
	cell.setCellStyle(headerCellStyle);
	cell.setCellValue(caption);
	return cell;
    }

    private HSSFCell createAmountCell(HSSFRow row, int number, double amount) {
	if (amountCellType == null) {
	    HSSFDataFormat dataFormat = workbook.createDataFormat();
	    amountCellType = workbook.createCellStyle();
	    amountCellType.setVerticalAlignment(VerticalAlignment.TOP);
	    amountCellType.setDataFormat(dataFormat.getFormat("# ##0.00"));
	    amountCellType.setBorderLeft(DEFAULT_BORDER_STYLE);
	    amountCellType.setBorderTop(DEFAULT_BORDER_STYLE);
	    amountCellType.setBorderRight(DEFAULT_BORDER_STYLE);
	    amountCellType.setBorderBottom(DEFAULT_BORDER_STYLE);
	}
	HSSFCell cell = row.createCell(number, CellType.NUMERIC);
	cell.setCellStyle(amountCellType);
	cell.setCellValue(amount);
	return cell;
    }

    private HSSFCell createDateTimeCell(HSSFRow row, int number, Date dateTime) {
	if (dateTimeCellType == null) {
	    HSSFDataFormat dataFormat = workbook.createDataFormat();
	    dateTimeCellType = workbook.createCellStyle();
	    dateTimeCellType.setVerticalAlignment(VerticalAlignment.TOP);
	    dateTimeCellType.setDataFormat(dataFormat.getFormat("DD.MM.YY HH:MM"));
	    dateTimeCellType.setBorderLeft(DEFAULT_BORDER_STYLE);
	    dateTimeCellType.setBorderTop(DEFAULT_BORDER_STYLE);
	    dateTimeCellType.setBorderRight(DEFAULT_BORDER_STYLE);
	    dateTimeCellType.setBorderBottom(DEFAULT_BORDER_STYLE);
	}
	HSSFCell cell = row.createCell(number, CellType.NUMERIC);
	cell.setCellStyle(dateTimeCellType);
	cell.setCellValue(dateTime);
	return cell;
    }
}
