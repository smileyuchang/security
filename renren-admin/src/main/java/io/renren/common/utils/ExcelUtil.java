package io.renren.common.utils;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 解析excel
 */
public class ExcelUtil {

    //private static final Logger log = Logger.getLogger(ExcelUtilTest.class);
    private  static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);

    public static List<String[]> getExcelData(MultipartFile file) throws IOException{

        //检查excel
        checkFile(file);
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<String[]> list = new ArrayList<String[]>();
        if(workbook != null){
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if(sheet == null){
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum  = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环除了第一行的所有行
                for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if(row == null){
                        continue;
                    }
                    //获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    //获得当前行的列数
                    int lastCellNum = row.getLastCellNum();
                    String[] cells = new String[row.getLastCellNum()];
                    //循环当前行
                    for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){
                        Cell cell = row.getCell(cellNum);
                        cells[cellNum] = getCellValue(cell);
                    }
                    list.add(cells);
                }
            }
        }
        return list;
    }


    /**
     * 检查文件
     * @param file
     * @throws IOException
     */
    public static  void checkFile(MultipartFile file) throws IOException{
        //判断文件是否存在
        if(null == file){
            log.error("文件不存在！");
        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if(!fileName.endsWith("xls") && !fileName.endsWith("xlsx")){
            log.error(fileName + "不是excel文件");
        }
    }

    public static  Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.endsWith("xls")){
                //2003
                workbook = new HSSFWorkbook(is);
            }else if(fileName.endsWith("xlsx")){
                //2007 及2007以上
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return workbook;
    }

    public static String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //判断数据的类型
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC: //数字
                cellValue = stringDateProcess(cell);
                break;
            case Cell.CELL_TYPE_STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    private static String stringDateProcess(Cell cell) {

        String result = new String();
        if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
            SimpleDateFormat sdf = null;
            if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
                sdf = new SimpleDateFormat("HH:mm");
            } else {// 日期
                sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            }
            Date date = cell.getDateCellValue();
            result = sdf.format(date);
        } else if (cell.getCellStyle().getDataFormat() == 58) {
            // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            double value = cell.getNumericCellValue();
            Date date = DateUtil
                    .getJavaDate(value);
            result = sdf.format(date);
        } else {
            double value = cell.getNumericCellValue();
            CellStyle style = cell.getCellStyle();
            DecimalFormat format = new DecimalFormat();
            String temp = style.getDataFormatString();
            // 单元格设置成常规
            if (temp.equals("General")) {
                format.applyPattern("#");
            }
            result = format.format(value);
        }

        return result;
    }




    /*
     ***************************************
     * Description:list转excel，导出到浏览器
     *****************************************
     */
    public static <T> void listToExcel(List<T> list, LinkedHashMap<String, String> fieldMap, String sheetName,
                                       HttpServletResponse response) {

        // 设置默认文件名为当前时间：年月日时分秒
        String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
        // 设置response头信息
        response.reset();
        response.setContentType("application/vnd.ms-excel"); // 改成输出excel文件
        response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
        // 创建工作簿并发送到浏览器
        try {
            OutputStream out = response.getOutputStream();
            listToExcel(list, fieldMap, sheetName, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /*
     ***************************************
     * Description:导出excel2003：list 数据集合，fieldMap 类的英文属性和Excel中的中文列名的对应关系，sheetName
     * 工作表的名称，out 导出流
     *****************************************
     */
    public static <T> void listToExcel(List<T> list, LinkedHashMap<String, String> fieldMap, String sheetName,
                                       OutputStream out) throws Exception {
        if (list.size() == 0 || list == null) {
            throw new Exception("数据源中没有任何数据");
        }
        int sheetSize = list.size();
        if (sheetSize > 65535 || sheetSize < 1) {
            sheetSize = 65535;
        }
        // 创建工作簿并发送到OutputStream指定的地方
        WritableWorkbook wwb;
        try {
            wwb = jxl.Workbook.createWorkbook(out);
            // 1.计算一共有多少个工作表
            int sheetNum = list.size() % sheetSize == 0 ? list.size() / sheetSize : (list.size() / sheetSize + 1);
            // 2.创建相应的工作表，并向其中填充数据
            for (int i = 0; i < sheetNum; i++) {
                // 如果只有一个工作表的情况
                if (1 == sheetNum) {
                    WritableSheet sheet = wwb.createSheet(sheetName, i);
                    fillSheet(sheet, list, fieldMap, 0, list.size() - 1);
                    // 有多个工作表的情况
                } else {
                    WritableSheet sheet = wwb.createSheet(sheetName + (i + 1), i);
                    // 获取开始索引和结束索引
                    int firstIndex = i * sheetSize;
                    int lastIndex = (i + 1) * sheetSize - 1 > list.size() - 1 ? list.size() - 1
                            : (i + 1) * sheetSize - 1;
                    // 填充工作表
                    fillSheet(sheet, list, fieldMap, firstIndex, lastIndex);
                }
            }
            wwb.write();
            wwb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 向工作表中填充数据
    private static <T> void fillSheet(WritableSheet sheet, List<T> list, LinkedHashMap<String, String> fieldMap,
                                      int firstIndex, int lastIndex) throws Exception {

        // 定义存放英文字段名和中文字段名的数组
        String[] enFields = new String[fieldMap.size()];
        String[] cnFields = new String[fieldMap.size()];

        // 填充数组
        int count = 0;
        for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
            enFields[count] = entry.getKey();
            cnFields[count] = entry.getValue();
            count++;
        }
        // 填充表头
        for (int i = 0; i < cnFields.length; i++) {
            Label label = new Label(i, 0, cnFields[i]);
            sheet.addCell(label);
        }
        // 填充内容
        int rowNo = 1;
        for (int index = firstIndex; index <= lastIndex; index++) {
            // 获取单个对象
            T item = list.get(index);
            for (int i = 0; i < enFields.length; i++) {
                Object objValue = null;
                objValue = ReflectUtil.getNestedProperty(item, enFields[i]);
                String fieldValue = objValue == null ? "" : objValue.toString();
                Label label = new Label(i, rowNo, fieldValue);
                sheet.addCell(label);
            }
            rowNo++;
        }
        // 设置自动列宽
        setColumnAutoSize(sheet, 5);
    }

    private static void setColumnAutoSize(WritableSheet ws, int extraWith) {
        // 获取本列的最宽单元格的宽度
        for (int i = 0; i < ws.getColumns(); i++) {
            int colWith = 0;
            for (int j = 0; j < ws.getRows(); j++) {
                String content = ws.getCell(i, j).getContents().toString();
                int cellWith = content.length();
                if (colWith < cellWith) {
                    colWith = cellWith;
                }
            }
            // 设置单元格的宽度为最宽宽度+额外宽度
            ws.setColumnView(i, colWith + extraWith);
        }
    }

}
