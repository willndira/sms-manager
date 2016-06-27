/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import util.HelperUtil;

/**
 *
 * @author Norrey Osako
 */
public class Reports implements Serializable{
    
    private static Map<String, CellStyle> createStyles(Workbook wb) {
        
        Map<String, CellStyle> styles = new HashMap<>();
        CellStyle style;
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short) 18);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFont(titleFont);
        styles.put("title", style);
        
        Font monthFont = wb.createFont();
        monthFont.setFontHeightInPoints((short) 11);
        monthFont.setColor(IndexedColors.WHITE.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(monthFont);
        style.setWrapText(true);
        styles.put("header", style);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put("cell", style);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("formula", style);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("formula_2", style);
        
        return styles;
    }
    
    public void generateXSL(List<SmsOutUserBean> smsOutUserBeans, int count) {
        try {
            
            HSSFWorkbook wb = new HSSFWorkbook();
            Map<String, CellStyle> styles = createStyles(wb);
            HSSFSheet sheet = wb.createSheet("Users_Sheet1");
            
            PrintSetup printSetup = sheet.getPrintSetup();
            printSetup.setLandscape(true);
            sheet.setFitToPage(true);
            sheet.setHorizontallyCenter(true);

            //title row
            Row titleRow = sheet.createRow(0);
            titleRow.setHeightInPoints(45);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("SMS OUT REPORT");
            titleCell.setCellStyle(styles.get("title"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$H$1"));
            
            String[] titles = {"Mobile", "Source Address", "Message", "Time Sent", "Last Update", "User", "Status", "Number of SMS",};
            
            HSSFRow row = sheet.createRow(1);
            row.setHeightInPoints(40);
            
            Cell headerCell;
            for (int i = 0; i < titles.length; i++) {
                headerCell = row.createCell(i);
                headerCell.setCellValue(titles[i]);
                headerCell.setCellStyle(styles.get("header"));
            }
            
            int rowNum = 2;
            for (SmsOutUserBean bean : smsOutUserBeans) {
                row = sheet.createRow(rowNum);
                row.createCell(0).setCellValue(bean.getSmsOutModel().getDestinationAddress());
                row.createCell(1).setCellValue(bean.getSmsOutModel().getSourceAddress());
                row.createCell(2).setCellValue(bean.getSmsOutModel().getMessagePayload());
                
                row.createCell(3).setCellValue(HelperUtil.conDateToString(bean.getSmsOutModel().getTimeSubmitted()));
                row.createCell(4).setCellValue(HelperUtil.conDateToString(bean.getSmsOutModel().getTimeProcessed()));
                
                row.createCell(5).setCellValue(bean.getUserBean().getUsername());
                row.createCell(6).setCellValue(bean.getSmsOutModel().getRealStatus());
                row.createCell(7).setCellValue(bean.getSmsOutModel().getSmsCount());
                
                rowNum++;
            }
            
            sheet.setColumnWidth(0, 20 * 256); //30 characters wide
            sheet.setColumnWidth(1, 15 * 256);
            for (int i = 2; i < 5; i++) {
                sheet.setColumnWidth(i, 20 * 256);  //6 characters wide
            }
            sheet.setColumnWidth(5, 10 * 256);
            
            sheet.setColumnWidth(6, 20 * 256);
            sheet.setColumnWidth(7, 10 * 256); //10 characters wide

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();
            res.setContentType("application/vnd.ms-excel");
            res.setHeader("Content-disposition", "attachment;filename=mydata.xls");
            
            ServletOutputStream out = res.getOutputStream();
            wb.write(out);
            out.flush();
            out.close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
