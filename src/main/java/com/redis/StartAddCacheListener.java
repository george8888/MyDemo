package com.redis;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
/*
 * 监听器，用于项目启动的时候初始化信息
 */
@Service


public class StartAddCacheListener implements ServletContextListener
{
	
     @Autowired
     private RedisCacheUtil redisCache;
  
     @Override
     public void contextDestroyed(ServletContextEvent arg0) {
	// TODO Auto-generated method stub
	
     }

     @Override
     public void contextInitialized(ServletContextEvent arg0) {
    	 // TODO Auto-generated method stub
    	 //spring 启动的时候缓存城市和国家等信息
	  		Map<String, String> cityMap = new HashMap<>();
	  		Map<String, String> countryMap = new HashMap<>();
  	    	cityMap.put("001", "北京");
  	    	cityMap.put("002", "上海");
  	    	cityMap.put("003", "广州");
  	    	cityMap.put("004", "深圳");
  	    	cityMap.put("005", "武汉");
  	    	countryMap.put("001", "中国");
  	    	countryMap.put("002", "日本");
  	    	countryMap.put("003", "美国");
  	    	countryMap.put("004", "德国");
  	    	countryMap.put("005", "俄罗斯");
  	    	redisCache.setCacheMap("cityMap", cityMap);
  	    	redisCache.setCacheMap("countryMap", countryMap);	
     }
}