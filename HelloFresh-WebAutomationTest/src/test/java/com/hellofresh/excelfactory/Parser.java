package com.hellofresh.excelfactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Parser
{
  private Class clazz;
  private ExcelFactoryType excelFactoryType;
  private boolean skipHeader;
  private Map<String, Field> fieldsMap;
  private boolean breakAfterEmptyRow;
  
  public Parser(Class clazz, ExcelFactoryType excelFactoryType)
    throws Exception
  {
    this.clazz = clazz;
    this.excelFactoryType = excelFactoryType;
    this.breakAfterEmptyRow = true;
    if (clazz.isAnnotationPresent(ExcelBean.class))
    {
      this.fieldsMap = new HashMap();
      
      Field[] fields = clazz.getDeclaredFields();
      for (Field field : fields) {
        switch (this.excelFactoryType)
        {
        case COLUMN_INDEX_BASED_EXTRACTION: 
          prepareColumnIndexBasedFieldMap(field); break;
        case COLUMN_NAME_BASED_EXTRACTION: 
          prepareColumnHeaderBasedFieldMap(field);
        }
      }
    }
    else
    {
      throw new Exception("Provided class is not annotated with ExcelBean");
    }
  }
  
  private void prepareColumnIndexBasedFieldMap(Field field)
  {
    if (field.isAnnotationPresent(ExcelColumnIndex.class))
    {
      field.setAccessible(true);
      ExcelColumnIndex column = (ExcelColumnIndex)field.getAnnotation(ExcelColumnIndex.class);
      String key = String.valueOf(column.columnIndex());
      this.fieldsMap.put(key, field);
    }
  }
  
  private void prepareColumnHeaderBasedFieldMap(Field field)
  {
    if (field.isAnnotationPresent(ExcelColumnHeader.class))
    {
      field.setAccessible(true);
      ExcelColumnHeader column = (ExcelColumnHeader)field.getAnnotation(ExcelColumnHeader.class);
      String key = column.columnHeader();
      this.fieldsMap.put(key, field);
    }
  }
  
  private String getDataTypeFor(Field field)
  {
    String dataType = null;
    switch (this.excelFactoryType)
    {
    case COLUMN_INDEX_BASED_EXTRACTION: 
      ExcelColumnIndex indexColumn = (ExcelColumnIndex)field.getAnnotation(ExcelColumnIndex.class);
      dataType = indexColumn.dataType();
      break;
    case COLUMN_NAME_BASED_EXTRACTION: 
      ExcelColumnHeader headerColumn = (ExcelColumnHeader)field.getAnnotation(ExcelColumnHeader.class);
      dataType = headerColumn.dataType();
    }
    return dataType;
  }
  
  private String getDefaultValueFor(Field field)
  {
    String defaultValue = null;
    switch (this.excelFactoryType)
    {
    case COLUMN_INDEX_BASED_EXTRACTION: 
      ExcelColumnIndex indexColumn = (ExcelColumnIndex)field.getAnnotation(ExcelColumnIndex.class);
      defaultValue = indexColumn.defaultValue();
      break;
    case COLUMN_NAME_BASED_EXTRACTION: 
      ExcelColumnHeader headerColumn = (ExcelColumnHeader)field.getAnnotation(ExcelColumnHeader.class);
      defaultValue = headerColumn.defaultValue();
    }
    return defaultValue;
  }
  
  public List<Object> parse(File file)
    throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, ParseException
  {
    List<Object> result = new ArrayList();
    Workbook invoiceWorkbook = WorkbookFactory.create(file);
    
    Sheet sheet = invoiceWorkbook.getSheetAt(0);
    Row firstRow;
    if (this.excelFactoryType == ExcelFactoryType.COLUMN_NAME_BASED_EXTRACTION)
    {
      firstRow = sheet.getRow(0);
      for (Cell column : firstRow)
      {
        Field field = (Field)this.fieldsMap.get(column.getStringCellValue());
        if (field != null)
        {
          this.fieldsMap.remove(column.getStringCellValue());
          this.fieldsMap.put(String.valueOf(column.getColumnIndex()), field);
        }
      }
    }
    for (Row row : sheet) {
      if (this.excelFactoryType == ExcelFactoryType.COLUMN_INDEX_BASED_EXTRACTION ? 
        (row.getRowNum() != 0) && (!this.skipHeader) : 
        
        (this.excelFactoryType != ExcelFactoryType.COLUMN_NAME_BASED_EXTRACTION) || 
        (row.getRowNum() != 0)) {
        if (!isEmptyRow(row))
        {
          Object beanObj = getBeanForARow(row);
          result.add(beanObj);
        }
        else
        {
          if (this.breakAfterEmptyRow) {
            break;
          }
        }
      }
    }
    return result;
  }
  
  public Object getBeanForARow(Row row)
    throws InstantiationException, IllegalAccessException, IllegalArgumentException, ParseException
  {
    Object classObj = this.clazz.newInstance();
    for (int i = 0; i < row.getLastCellNum(); i++)
    {
      Cell cell = row.getCell(i);
      if (cell != null)
      {
        if (cell.getCellType() == 0)
        {
          if (DateUtil.isCellDateFormatted(cell))
          {
            java.util.Date date = cell.getDateCellValue();
            cell.setCellType(1);
            cell.setCellValue(new SimpleDateFormat("dd-MM-yyyy").format(date));
          }
          else
          {
            cell.setCellType(1);
          }
        }
        else {
          cell.setCellType(1);
        }
        String value = cell.getStringCellValue() == null ? null : cell.getStringCellValue().trim();
        setCellValueBasedOnDesiredExcelFactoryType(classObj, value, i);
      }
      else
      {
        setCellValueBasedOnDesiredExcelFactoryType(classObj, null, i);
      }
    }
    return classObj;
  }
  
  private void setCellValueBasedOnDesiredExcelFactoryType(Object classObj, String columnValue, int columnIndex)
    throws IllegalArgumentException, IllegalAccessException, ParseException
  {
    Field field = (Field)this.fieldsMap.get(String.valueOf(columnIndex));
    if (field != null)
    {
      if ((columnValue == null) || (columnValue.trim().isEmpty())) {
        columnValue = getDefaultValueFor(field);
      }
      if ((columnValue != null) && (!columnValue.trim().isEmpty()))
      {
        String dataType = getDataTypeFor(field);
        switch (dataType)
        {
        case "int": 
          field.set(classObj, Integer.valueOf(Integer.parseInt(columnValue)));
          break;
        case "long": 
          field.set(classObj, Long.valueOf(Long.parseLong(columnValue)));
          break;
        case "bool": 
          field.set(classObj, Boolean.valueOf(Boolean.parseBoolean(columnValue)));
          break;
        case "double": 
          Double data = Double.valueOf(Double.parseDouble(columnValue));
          field.set(classObj, data);
          break;
        case "date": 
          field.set(classObj, dateParser(columnValue));
          break;
        default: 
          field.set(classObj, columnValue);
        }
      }
    }
  }
  
  private java.sql.Date dateParser(String value)
  {
    if ((value != null) && (!value.isEmpty()))
    {
      String[] formats = { "dd-MM-yyyy" };
      try
      {
        java.util.Date date = DateUtils.parseDate(value, formats);
        return new java.sql.Date(date.getTime());
      }
      catch (ParseException e)
      {
        e.printStackTrace();
        
        return null;
      }
    }
    return null;
  }
  
  boolean isEmptyRow(Row row)
  {
    boolean isEmptyRow = true;
    for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++)
    {
      Cell cell = row.getCell(cellNum);
      if ((cell != null) && (cell.getCellType() != 3) && (StringUtils.isNotBlank(cell.toString()))) {
        isEmptyRow = false;
      }
    }
    return isEmptyRow;
  }
  
  public boolean isBreakAfterEmptyRow()
  {
    return this.breakAfterEmptyRow;
  }
  
  public void setBreakAfterEmptyRow(boolean breakAfterEmptyRow)
  {
    this.breakAfterEmptyRow = breakAfterEmptyRow;
  }
  
  public boolean isSkipHeader()
  {
    return this.skipHeader;
  }
  
  public void setSkipHeader(boolean skipHeader)
  {
    this.skipHeader = skipHeader;
  }
}