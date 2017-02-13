package com.telematics.test2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class Log4jTest 
{
	private static Logger logger = LoggerFactory.getLogger(Log4jTest.class);  

    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
    	String a = "aaaaaaaaaa";
    	String b = "bbbbbbbbbb";

        // 记录debug级别的信息  
        logger.debug("This is debug message.");  
        // 记录info级别的信息  
        logger.info("This is info message.");  
       //记录warn级别的日志
        logger.warn("This is warn message.");  
        // 记录error级别的信息  
        logger.error("This is error message.");  
        
        logger.debug("这是一个测试的log，a=【{}】,b=【{}】",a,b);
    }  
}
