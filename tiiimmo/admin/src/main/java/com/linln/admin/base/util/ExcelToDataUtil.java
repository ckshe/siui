package com.linln.admin.base.util;

import org.apache.poi.ss.usermodel.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;

/**
 * @author zhaobc
 * @description excel封装
 * @date 2019-12-31
 */
public class ExcelToDataUtil {

    /**
     *  根据excel 列头对应数据转换成List<Map>数据 方便curd
     * @param sheet 文档对象
     * @param rowNumber 从第几行开始匹配
     * @param CellHead  列头数据map
     * @return
     */
    public static List<Map> ExcelConvertEntity(Sheet sheet, Integer rowNumber, Map<Integer, String> CellHead)
    {
        //map集合
        List<Map> entity  = new ArrayList<Map>();
        int rowNum = sheet.getLastRowNum(); //获取行数，最后一行编号     返回的4代表excel的第5行
        if(rowNum<1)
        {
            return null;
        }
        //从第i列 数据列开始循环
        for (int i = rowNumber; i <= rowNum; i++) {

            //当前行的所有字段集合
            Map<String,String> CellData = new HashMap<String,String>();
            Row row = sheet.getRow(i);//获取Excel的行，下标从0开始
            if (row == null) {
                //若行为空，则遍历下一行
                continue;
            }
            int minColIx = row.getFirstCellNum(); // 获取在某行第一个单元格的下标
            int maxColIx = row.getLastCellNum(); //// 获取在某行的列数
            for (int colIx = minColIx; colIx < maxColIx; colIx++) {
                Cell cell = row.getCell(colIx);//获取指定单元格，单元格从左到右下标从0开始
                if (cell == null) //excel为null时
                {
                    //若列为空，则遍历下一列
                    continue;
                }
                //cell.setCellType(CellType.STRING);//转换 否则number获取会报错
                String runText = ConvertVal(cell); //取值
                //将列的键对应列头
                CellData.put(CellHead.get(colIx),runText);
            }
            entity.add(CellData);
        }
        return entity;
    }
    /**
     * 获取列头名
     * @param sheet 文档对象
     * @param num 从第几行开始
     * @return map集合
     */
    public static  Map<Integer, String> GetCellHead(Sheet sheet, int num) {
        Map<Integer, String> CellHead = new HashMap<Integer, String>();
        Row row = sheet.getRow(num);
        if (row == null) {
            //若行为空，则返回null
            return null;
        }
        int minColIx = row.getFirstCellNum(); // 获取在某行第一个单元格的下标
        int maxColIx = row.getLastCellNum(); //// 获取在某行的列数
        for (int colIx = minColIx; colIx < maxColIx; colIx++) {
            Cell cell = row.getCell(colIx);//获取指定单元格，单元格从左到右下标从0开始
            String runText = "";
            if (cell == null) //excel为null时
            {
                //若列为空 则返回null
               runText = null;
            }else {
                cell.setCellType(CellType.STRING);//转换 否则number获取会报错
                runText = cell.getStringCellValue(); //取值
            }
            CellHead.put(colIx,runText);
        }
        return CellHead;
    }

    /**
     * 根据不同类型 获取Cell值后转换
     * @param cell
     * @return
     */
    public static String ConvertVal(Cell cell) {
        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        String returnText = "";
        switch (cell.getCellType()) {
            case STRING:
                returnText = cell.getStringCellValue();
                break;
            case NUMERIC:
                if(DateUtil.isCellDateFormatted(cell))
                {
                    returnText = valueOf(formater.format(cell.getDateCellValue()));
                }
                else {
                    returnText = valueOf(cell.getNumericCellValue());
                }
                break;
            case FORMULA:
                try {
                    cell.setCellType(CellType.STRING);
                    returnText = cell.getStringCellValue();
                }catch(Exception e)
                {
                    returnText = valueOf(cell.getCellFormula());
                }
                break;
            case BOOLEAN:
                returnText = valueOf(cell.getBooleanCellValue());
                break;
            case ERROR:
                returnText = valueOf(cell.getErrorCellValue());
                break;
            case BLANK:
                //nothing to do
                break;
            default:
                break;
        }
        return returnText;
    }
    public static <T> T Map2Object(Map<String, Object> map, Class<T> clazz) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (map == null) {
            return null;
        }
        T obj = null;
        try {
            // 使用newInstance来创建对象
            obj = clazz.newInstance();
            // 获取类中的所有字段
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                // 判断是拥有某个修饰符
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                // 当字段使用private修饰时，需要加上
                field.setAccessible(true);
                // 获取参数类型名字
                String filedTypeName = field.getType().getName();
                // 判断是否为时间类型，使用equalsIgnoreCase比较字符串，不区分大小写
                // 给obj的属性赋值
                if (filedTypeName.equalsIgnoreCase("java.util.date")) {
                    String datetimestamp = (String) map.get(field.getName());
                    if (datetimestamp.equalsIgnoreCase("null")) {
                        field.set(obj, null);
                    } else {
                        field.set(obj, sdf.parse(datetimestamp));
                    }
                }
                else if(filedTypeName.equalsIgnoreCase("java.sql.Timestamp"))
                {
                    String datetimestamp = (String) map.get(field.getName());
                    if (map.get(field.getName()) == null) {
                        continue;
                    } else {
                        field.set(obj, sdf.parse(datetimestamp));
                    }
                }
                else if(filedTypeName.equalsIgnoreCase("java.lang.Integer") || filedTypeName.equalsIgnoreCase("int"))
                {
                    if(map.get(field.getName()) ==null)
                        continue;
                    field.set(obj, Double.valueOf(valueOf(map.get(field.getName()))).intValue());
                }
                else if(filedTypeName.equalsIgnoreCase("java.lang.Double") || filedTypeName.equalsIgnoreCase("double"))
                {
                    if(map.get(field.getName()) ==null)
                        continue;
                    field.set(obj, (Double) map.get(field.getName()));
                }
                else {
                    if(map.get(field.getName()) ==null)
                        continue;
                    field.set(obj, map.get(field.getName()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
    public static List<Map> Map2Entity(List<Map> sourceMap, Map<String,String> keyVal ){
        List<Map> resultMap = new ArrayList<Map>();
        for (int i = 0; i < sourceMap.size(); i++) {
            Map<String,Object> newData = new HashMap<String,Object>();
            for(Object key : keyVal.keySet())
            {
                newData.put(keyVal.get(key), String.valueOf(sourceMap.get(i).get(key)));
            }
            resultMap.add(newData);
        }
        return resultMap;
    }
    /**
     * 动态转换对象实体示例
     *             Workbook wb = WorkbookFactory.create(file.getInputStream());
     *             Sheet sheet = wb.getSheetAt(0);
     *             //获取列头
     *             Map<Integer, String> CellHead = ExcelToDataUtil.GetCellHead(sheet, 2);
     *             //获取对应字段
     *             Map<String,String> keyVal = new HashMap<String,String>();
     *             keyVal.put("机台","imId");
     *             keyVal.put("模具号","mouldSerial");
     *             keyVal.put("产品名称", "productSerial");
     *             keyVal.put("订单数量         单位:片", "taskCount");
     *             keyVal.put("备注", "description");
     *             keyVal.put("注塑排期", "planFinishTime");
     *             keyVal.put("出模数", "mouldCount");
     *             //获取内容
     *             List<Map> entity = ExcelToDataUtil.Map2Entity(ExcelToDataUtil.ExcelConvertEntity(sheet, 4,CellHead ),keyVal);
     *             List<TaskSheet> tasksheet = new ArrayList<TaskSheet>();
     *             for(int label =0; label<entity.size();label++)
     *             {
     *                 tasksheet.add(ExcelToDataUtil.Map2Object(entity.get(label),TaskSheet.class));
     *             }
     */
}
