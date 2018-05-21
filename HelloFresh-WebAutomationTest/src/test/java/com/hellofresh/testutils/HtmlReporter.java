package com.hellofresh.testutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.util.ResourceUtils;
/**
 * HtmlReporter - To create Html Report for every test case
 * @author kb
 *
 */

public class HtmlReporter {

	public void fn_FileCopy(String sour,String dest) 
	{
		try
		{
			Path des=Paths.get(dest);
			Path src=Paths.get(sour);
					
			Files.copy(src, des, StandardCopyOption.REPLACE_EXISTING);
			//fileTemp=null;		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String fn_CopyTemplateFiles(String outputpath,String resultTimeStamp) throws FileNotFoundException
	{
		File template=ResourceUtils.getFile("classpath:HtmlTemplate/ResultTemplate_Z.html");
		File css=ResourceUtils.getFile("classpath:HtmlTemplate/style.css");
		File filter=ResourceUtils.getFile("classpath:HtmlTemplate/filter.css");
		
		fn_FileCopy(template.getAbsolutePath(), outputpath+"/Result_"+resultTimeStamp+".html");
		fn_FileCopy(css.getAbsolutePath(), outputpath+"/style.css");
		fn_FileCopy(css.getAbsolutePath(), outputpath+"/filter.css");
		
		return outputpath+"/Result_"+resultTimeStamp+".html";
		
		
	}
	
	public String fn_CreateFolder(String filePath)throws Exception{
		/*String dateFolderName=new SimpleDateFormat("MM-dd-yyyy").format(Calendar.getInstance().getTime()).toString();
		String temp=path + dateFolderName + "\\" + testCaseName + "\\";*/
		File files = new File(filePath);
		new File(filePath).mkdirs();
		files=null;
		return filePath;
	}
	public void insertTDTagCentered(Writer writer,String data)
	{
		try{

			writer.write("<TD><CENTER>");
			writer.write(data);
			writer.write("</TD></CENTER>");

		}
		catch (IOException e) {
			//exception handling left as an exercise for the reader
			e.printStackTrace();
		}		

	}
	public void insertTDTag(Writer writer, String data){
		try{

			if(data == "FAIL"){
				writer.write("<TD><FONT color =\"#FF0000\">");		    	    
				writer.write("<B><CENTER>"+data+"</CENTER></B>");		    //more code		    	    
				writer.write("</FONT></TD>");

			}
			else if(data == "WARN")
			{
				writer.write("<TD><FONT color =\"#FF8C00\">");		    	    
				writer.write("<CENTER><B>"+data+"</B></CENTER>");		    //more code		    	    
				writer.write("</FONT></TD>");

			}
			else if(data == "PASS")
			{
				writer.write("<TD>");
				writer.write("<CENTER>"+data+"</CENTER>");		    //more code		    	    
				writer.write("</TD>");

			}
			else if(data == "INFO")
			{
				writer.write("<TD>");
				writer.write("<CENTER>"+data+"</CENTER>");		    //more code		    	    
				writer.write("</TD>");

			}


			else
			{
				writer.write("<TD>");
				if(data.contains("Expected")|| data.contains("Actual"))
				{
					//Expected Data
					writer.write(data.split("<BR>")[0].split("~")[0] + ":");			    		
					writer.write("<B><FONT color =\"#0000FF\">");
					writer.write(data.split("<BR>")[0].split("~")[1]);
					writer.write("</FONT></B>");
					writer.write("<BR>");
					//Actual Data
					writer.write(data.split("<BR>")[1].split("~")[0] + ":");			    		
					writer.write("<B><FONT color =\"#0000FF\">");
					writer.write(data.split("<BR>")[1].split("~")[1]);
					writer.write("</FONT></B>");
				}
				else
				{
					writer.write(data);		    //more code
				}
				writer.write("</TD>");				
			}
			//more code
		}catch (IOException e) {
			//exception handling left as an exercise for the reader
			e.printStackTrace();
		}
	}
	public void addTRTag(Writer writer){
		try{
			writer.write("<TR>");			

		}catch (IOException e) {
			//exception handling left as an exercise for the reader
			e.printStackTrace();
		}
	}
	public static void closeTRTag(Writer writer){
		try {
			writer.write("</TR>");
			//more code
		}catch (IOException e) {
			//exception handling left as an exercise for the reader
			e.printStackTrace();
		}
	}
	
	public void fn_insertRow(Writer writer,HtmlRecord hm) throws IOException, InterruptedException
	{
		//adding Test Case
		addTRTag(writer);

		//adding status
		insertTDTag(writer, hm.getStep());		
		//addin status
		
		insertTDTagCentered(writer, hm.getStatus());
		//adding Desc
		insertTDTag(writer, hm.getDescription());
		
		
		
		if(hm.getScreenshotPath()=="NA")
		{
			insertTDTagCentered(writer, "<CENTER>"+hm.getScreenshotPath()+"</CENTER>");
		}
		else
		{
			//target=\"_blank\"
			insertTDTagCentered(writer, "<CENTER><A HREF=\""+hm.getScreenshotPath()+"\" >Screenshot</A></CENTER>");			
		}
		closeTRTag(writer);


	}
	
	public void fn_updateTopTableHTML(TestCase tc) throws IOException, InterruptedException
	{
		try
		{
			FileInputStream fis=new FileInputStream(tc.getOutputPath());
			String content = IOUtils.toString(fis, "UTF-8");
			content = content.replaceAll("KEY_WORKFLOW_NAME", tc.getTestCaseName());
			content = content.replaceAll("KEY_EXECUTIONDATE", tc.getExecDate());
			content = content.replaceAll("KEY_APPLICATIONVERSION", tc.getAppName());
			content = content.replaceAll("KEY_START_TIME", tc.getStartTime());
			content = content.replaceAll("KEY_END_TIME", tc.getEndTime());
			content = content.replaceAll("KEY_DURATION_TIME", tc.calculateExecTime());
			content = content.replaceAll("KEY_PASS", Integer.toString(tc.getPassCount()));
			content = content.replaceAll("KEY_FAIL", Integer.toString(tc.getFailCount()));
			content = content.replaceAll("KEY_WARNING", Integer.toString(tc.getWarnCount()));
			FileOutputStream fos=new FileOutputStream(tc.getOutputPath());
			IOUtils.write(content,fos , "UTF-8");
			fis.close();
			fos.close();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void fn_WriteToHTMLReport(List<HtmlRecord> htmlRecords, TestCase tc) throws IOException, InterruptedException
	{
		Writer writer=new FileWriter(tc.getOutputPath(), true);
		
		for(HtmlRecord hm:htmlRecords)
		{
			fn_insertRow(writer, hm);
		}
		
		fn_addFilter(writer);	
		
		writer.flush();
		writer.close();
		fn_updateTopTableHTML(tc);
		

	}
	
	public void fn_addFilter(Writer writer)
	{
		try
		{
			writer.write("<script language=\"javascript\" type=\"text/javascript\">");
			writer.write("//<![CDATA[");
			writer.write("setFilterGrid(\"table1\");");
			writer.write("//]]>");
			writer.write("</script>");			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
