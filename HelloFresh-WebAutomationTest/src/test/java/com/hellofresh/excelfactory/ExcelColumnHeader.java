package com.hellofresh.excelfactory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface ExcelColumnHeader {
	String columnHeader();
	
	String dataType() default "string";
	
	String defaultValue() default "";
}
