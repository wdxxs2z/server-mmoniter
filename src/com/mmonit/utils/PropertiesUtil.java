package com.mmonit.utils;

import java.io.InputStream;
import java.util.Properties;

import com.mmonit.main.ThreadPoolMonit;
/*
 * 功能：按照properties文件查找属性值
 * author:yuanyuan
 * **/
public class PropertiesUtil {
	
	public static String getProValue(String path,String key) {
		InputStream is;
		String propertieV = null;
		try {
			is = ThreadPoolMonit.class.getClassLoader().getResourceAsStream(path);
			Properties p=new Properties();
			p.load(is);
			propertieV = p.getProperty(key);
			is.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}		
		return propertieV;
	}
}
