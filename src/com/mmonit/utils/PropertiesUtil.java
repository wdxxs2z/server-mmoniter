package com.mmonit.utils;

import java.io.InputStream;
import java.util.Properties;
/*
 * 功能：按照properties文件查找属性值
 * author:yuanyuan
 * **/
public class PropertiesUtil {
	
	public static String getProValue(String path,String key) {
		InputStream is;
		String propertieV = null;
		try {
			is = ClassLoader.getSystemResourceAsStream(path);
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
