package com.abchina.utils;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class MonitXml2O {
	
	public static ConcurrentHashMap<String, String> getMonitId(String substring, ConcurrentHashMap<String,String> map) {
		Document doc = null;
		map = new ConcurrentHashMap<String, String>();
		try {
			doc = DocumentHelper.parseText(substring);
			
			Element rootElt = doc.getRootElement();
			Iterator<Attribute> attributeIterator = rootElt.attributeIterator();
			while (attributeIterator.hasNext()) {// 遍历root节点的属性
				Attribute a = (Attribute) attributeIterator.next();
				//根据monit id 进程判断 是否是新节点
				String name = a.getName();
				String value = a.getValue();
				if(name.equals("id")){
					//判断任务是否在执行
					if(map.size()==0){
						map.put(value, substring);
					}
					if(map.get(value).matches(value)){	
						break;
					}
					map.put(value, substring);				
				}				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	public static String getRemoteHost(String mId, String mInfo) {
		Document doc = null;
		String address = "";

		try {
			doc = DocumentHelper.parseText(mInfo);
			
			Element rootElt = doc.getRootElement();
			Iterator<Attribute> attributeIterator = rootElt.attributeIterator();
			
			while (attributeIterator.hasNext()) {// 遍历root节点的属性
				Attribute a = (Attribute) attributeIterator.next();
				//根据monit id 进程判断 是否是新节点
				String name = a.getName();
				String monitId = a.getValue();
					
				if(name.equals("id")){
					if(monitId.equals(mId)){//判断map中的monitId为要找的id
						
						Iterator elementIterator = rootElt.elementIterator("server");
						while (elementIterator.hasNext()) {// 遍历server节点下的update等等
							
							Element serE = (Element) elementIterator.next();
							Iterator httpdIte = serE.elementIterator("httpd");
							
							while (httpdIte.hasNext()) {
								Element httpEle = (Element) httpdIte.next();
								address = httpEle.elementTextTrim("address");
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return address;
	}

}
