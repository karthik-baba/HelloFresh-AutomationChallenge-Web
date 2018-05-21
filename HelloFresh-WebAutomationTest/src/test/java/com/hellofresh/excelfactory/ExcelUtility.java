package com.hellofresh.excelfactory;

import java.io.File;
import java.util.List;

import org.springframework.util.ResourceUtils;

public class ExcelUtility {
    public static List<? extends Object> fn_GetExcelData(String fileName, Class<?> type) throws Exception
    {
        Parser parser=new Parser(type,ExcelFactoryType.COLUMN_NAME_BASED_EXTRACTION);
        File file=ResourceUtils.getFile("classpath:TestData/"+fileName+".xlsx");        
        List<? extends Object> result=parser.parse(file);
        return result;
    }
    public static void main(String args[])
    {
    	
    }

}


