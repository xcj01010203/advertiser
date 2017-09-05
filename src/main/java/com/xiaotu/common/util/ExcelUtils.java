package com.xiaotu.common.util;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 
 *利用poi导出  导入 读取excel文件
 * @author wangyanlong
 *
 */
public class ExcelUtils {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    /**
     * 导出广告分析类型表
     * 
     * @param list
     * @param response
     * @throws IOException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void exportPropsInfoForExcel(List<Map<String, Object>> list,String roleNames,HttpServletResponse response,Map<String, String> exportColoum,String crewName) throws IOException, IllegalArgumentException, IllegalAccessException{
    	String title = "《"+crewName+"》"+roleNames+"列表";
    	HSSFWorkbook wb =exportExcel(list,exportColoum,title);
    	String fileName = title+sdf.format(new Date())+".xls";
        response.reset();
        response.setContentType("application/msexcel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+new String(fileName.getBytes("gb2312"), "iso8859-1")); 
        OutputStream out = response.getOutputStream();
        wb.write(out);
    }
    
    /**
     * 拼装excel
     * 
     * @param list 需要导出的数据集合
     * @param map 标题列   key:标题名称，value:实体对应的属性名称
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static HSSFWorkbook exportExcel(List<Map<String, Object>> list,Map<String, String> map,String titleData) throws IllegalArgumentException, IllegalAccessException {
    	int rowsNum = 0;
    	int cellNum = 0;
    	//创建HSSFWorkbook对象
		HSSFWorkbook wb = new HSSFWorkbook();
		//创建HSSFSheet对象
		HSSFSheet sheet = wb.createSheet("sheet1");
		Cell cell = null;
		if(StringUtils.isNotBlank(titleData)){
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, map.size()-1));
			HSSFRow rowTitle = sheet.createRow(rowsNum);
			HSSFCellStyle style = wb.createCellStyle(); // 样式对象      
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直      
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
			cell = rowTitle.createCell(0);
			cell.setCellValue(titleData);
			
			
			HSSFFont font=wb.createFont();
            font.setColor(HSSFColor.BLACK.index);//HSSFColor.VIOLET.index //字体颜色
            font.setFontHeightInPoints((short)12);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);         //字体增粗
            //把字体应用到当前的样式
            style.setFont(font);
		
            cell.setCellStyle(style);
			
			rowsNum ++;
		}		
		
		
		
		Set<String> keySet = map.keySet();//要导出的列名
		Iterator<String> it = keySet.iterator();
		//创建标题栏
		//创建HSSFRow对象
		HSSFRow row = sheet.createRow(rowsNum);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		cellStyle.setLocked(true);
		
		while(it.hasNext()){
			sheet.setColumnWidth(cellNum, 3766);//设置列宽
			//创建HSSFCell对象
			cell=row.createCell(cellNum);
			String title = it.next();
			//设置单元格的值
			cell.setCellValue(title);
			cell.setCellStyle(cellStyle);
			cellNum ++;
		}
		
		for(Map<String, Object> backMap:list){
			cellNum = 0;
			rowsNum ++;
			row = sheet.createRow(rowsNum);
			it = keySet.iterator();
			while(it.hasNext()){
				String value = it.next();//属性名称
				String attr = map.get(value);
					
				Set<String> backSet = backMap.keySet();
				Iterator<String> backIt = backSet.iterator();
				while(backIt.hasNext()){
					String backKey = backIt.next();
					if(attr.equals(backKey)){
						Object backValue = backMap.get(backKey);
						cell=row.createCell(cellNum);
						if(backValue==null){
							cell.setCellType(Cell.CELL_TYPE_STRING);
						}else{
							//backValue ="";
							cell.setCellType(Cell.CELL_TYPE_STRING);	
							cell.setCellValue(backValue.toString());
							int width = backValue.toString().length()*512;//设置单元格宽度
							if(width<3766){
								width = 3766;
							}
							//sheet.setColumnWidth(cellNum, width);//设置列宽
						}
						cellNum++;
						
						break;
					}
				}
			}
		}
		
		//输出Excel文件
		return wb;
	}
}
