package com.itcast.dubbo.pro.service.impl;

import com.itcast.dubbo.ser.annotation.SheetHeader;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by zebon lu on 2017/6/1.
 */
public class ExportTableByJxl {


    public void exportTable(String fileName,List<? extends Object> list, Class<? extends Object> clazz){
        try {
            fileName = "excel导出测试" + ".xls";
            File file = new File("d:\\" + fileName);
            if (file.exists()) {
                file.delete();
            }
            OutputStream os = new FileOutputStream(file);
//            School school1 = new School("清华大学", "计算机专业", "高3", new Date());
//            School school2 = new School("北京大学", "法律专业", "中2", new Date());
//            School school3 = new School("北京理工大学", "金融专业", "中1", new Date());
//            School school4 = new School("南京理工大学", "电子专业", "低1", new Date());
//            School school5 = new School("南京大学", "计算机专业", "低2", new Date());
//            School school6 = new School("复旦大学", "通讯专业", "中3", new Date());
//            List<School> list = new ArrayList<>();
//            list.add(school1);
//            list.add(school2);
//            list.add(school3);
//            list.add(school4);
//            list.add(school5);
//            list.add(school6);
            WritableWorkbook workbook = WriteToeExcel(list, clazz, os);

//        Label xuexiao = new Label(0,0,"学校");
//        sheet.addCell(xuexiao);
//        Label zhuanye = new Label(1,0,"专业");
//        sheet.addCell(zhuanye);
//        Label jingzhengli = new Label(2,0,"专业竞争力");
//        sheet.addCell(jingzhengli);

            //把创建的内容写入到输出流中，并关闭输出流
            workbook.write();
            workbook.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }


    private WritableWorkbook WriteToeExcel(List<? extends Object> list, Class<? extends Object> clazz, OutputStream os) {
        WritableWorkbook workbook = null;
        try {
//        创建工作簿
            workbook = Workbook.createWorkbook(os);
//        创建新页
            WritableSheet sheet = workbook.createSheet("first sheet", 0);
//        表头
            Label labelTopic = null;
//        表内容
            Label labelContent = null;
//        处理表头
//        获取类的所有字段
            Field[] declaredFields = clazz.getDeclaredFields();
//        遍历
            for (int i = 0; i < declaredFields.length; i++) {
                //            获取指定注解
                SheetHeader sheetHeader = declaredFields[i].getAnnotation(SheetHeader.class);
                labelTopic = new Label(sheetHeader.index(), 0, sheetHeader.comments());
                sheet.addCell(labelTopic);
            }
//            处理表内容
            for (int i = 0; i < list.size(); i++) {
                Object o = list.get(i);
                for (int j = 0; j < declaredFields.length; j++) {
                    Field field = declaredFields[j];
                    SheetHeader sheetHeader = field.getAnnotation(SheetHeader.class);
//                    获取字段类型
                    Class<?> type = field.getType();
//                    获取get方法名
                    String fieldName = field.toString().substring(field.toString().lastIndexOf(".") + 1);
                    fieldName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
//                    调用get获取值
                    Method method = clazz.getDeclaredMethod(fieldName);
//                    处理时间类型
                    SimpleDateFormat simpleDateFormat = null;
                    if (type.toString().endsWith("Date")) {
                        simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                        labelContent = new Label(sheetHeader.index(), i + 1, simpleDateFormat.format(method.invoke(o)));
                    } else {
                        labelContent = new Label(sheetHeader.index(), i + 1, method.invoke(o).toString());
                    }
                    sheet.addCell(labelContent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }

}
