package com.k12.common.util.excel;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 导出Excel文件 added by renyarong, 2013.8.6
 * 
 * @author renyarong
 * 
 */
public class ExportExcel {

    /**
     * 工作簿对象
     */
    private HSSFWorkbook wb;

    // 自定义HSSFWorkbook，读取excel时使用
    public ExportExcel(HSSFWorkbook wb) {
        super();
        this.wb = wb;
    }

    // 创建一个workbook草稿
    public ExportExcel() {
        super();
        wb = new HSSFWorkbook();
    }

    public void setWb(HSSFWorkbook wb) {
        this.wb = wb;
    }

    /**
     * 导出Excel表格
     * @param os 要导出的输出流对象
     * @throws IOException 
     */
    public void export(OutputStream os) throws IOException {
        wb.write(os);
        os.flush();
        os.close();
    }

    /**
     * 连续导出多个Excel表格
     *
     * @param os 输出流
     * @param closeStreamFlag 是否导出完就关闭输出流<br>
     *            如果置为false，需在导出所有表格后自己手动调用flushExportStream方法。
     * @throws IOException
     * @see flushExportStream
     */
    public void export(OutputStream os, boolean closeStreamFlag) throws IOException {
        wb.write(os);
        if (closeStreamFlag) {
            os.flush();
            os.close();
        }
    }

    /**
     * 功能描述: 刷新输出缓存，导出表格<br>
     *
     * @param os
     * @throws IOException
     * @see export
     */
    public static void flushExportStream(OutputStream os) throws IOException {
        os.flush();
        os.close();
    }

    /**
     * 增加一个横排工作簿
     * @param bookSheet
     */
    public void addSheet(BookSheet<?> bookSheet) {
        HSSFSheet sheet = wb.createSheet(bookSheet.getSheetName());
        sheet.setDefaultColumnWidth(20);
        List<?> dataList = bookSheet.getDataList();
        List<String> columnNames = new ArrayList<String>();
        List<String> filedNames = new ArrayList<String>();
        Field[] fields = bookSheet.getClazz().getDeclaredFields();
        ColumnName columnName = null;
        // 得到对象的属性名，以方便下面取值
        for (Field field : fields) {
            if (null != (columnName = field.getAnnotation(ColumnName.class))) {
                columnNames.add(columnName.value());
                filedNames.add(field.getName());
            }
        }

        int columnNum = columnNames.size();
        int rowNum = dataList.size();

        // 创建第0行，标题
        HSSFRow rowSup = sheet.createRow(0);
        HSSFCell cell = rowSup.createCell(0);
        cell.setCellValue(bookSheet.getTitle());

        // 创建第1行，列名
        rowSup = sheet.createRow(1);

        // 设置表格单元格样式
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setWrapText(true);

        // 填入列名
        for (int i = 0; i < columnNum; i++) {
            cell = rowSup.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(columnNames.get(i));
        }

        // 填入数据
        Map<String, Object> map = null;
        for (int i = 0; i < rowNum; i++) {
            rowSup = sheet.createRow(i + 2);
            map = transBean2Map(dataList.get(i));
            for (int j = 0; j < columnNum; j++) {
                cell = rowSup.createCell(j);
                cell.setCellStyle(cellStyle);
                String cellValue = String.valueOf(map.get(filedNames.get(j)));
                cell.setCellValue(("null").equals(cellValue) ? "" : cellValue);
                if(cellValue.length()>100){
               	 sheet.setColumnWidth((short) j, (short) (100 * 150));
               }
            }
        }
    }


	/**
     * 增加一个竖排工作簿
     * @param bookSheet
     */
	public void addVerticalSheet(BookSheet<?> bookSheet) {
	    HSSFSheet sheet = wb.createSheet(bookSheet.getSheetName());
        sheet.setDefaultColumnWidth(35);    
       List<?> dataList = bookSheet.getDataList();
       List<String> columnNames = new ArrayList<String>();
       List<String> fileNames = new ArrayList<String>();
       Field[] fields = bookSheet.getClazz().getDeclaredFields();
       ColumnName columnName = null;
       // 得到对象的属性名，以方便下面取值
       for (Field field : fields) {
           if (null != (columnName = field.getAnnotation(ColumnName.class))) {
               columnNames.add(columnName.value());
               fileNames.add(field.getName());
           }
       }
       int rowNum = columnNames.size();
       // 创建第0行，标题
       HSSFRow rowSup = sheet.createRow(0);
       HSSFCell cell = rowSup.createCell(0);
       cell.setCellValue(bookSheet.getTitle());
       //设置表格单元格样式
       HSSFCellStyle cellStyle = wb.createCellStyle(); 
       cellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
       cellStyle.setWrapText(true);
       // 竖排填入列名
       for (int i = 0; i < rowNum; i++) {
           rowSup = sheet.createRow( i+1);
           cell = rowSup.createCell(0);
           cell.setCellStyle(cellStyle); 
           cell.setCellValue(columnNames.get(i));
       }
       //竖排填入数据
       Map<String, Object> map = transBean2Map(dataList.get(0));
           for (int j = 0; j < rowNum; j++) {
               rowSup = sheet.getRow( j+1);
               cell = rowSup.createCell(1);
               cell.setCellStyle(cellStyle); 
               cell.setCellValue(String.valueOf(map.get(fileNames.get(j))));
           }
	}
    
    public static Map<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("转换对象为Map出错", e);
        }
        return map;
    }
}
