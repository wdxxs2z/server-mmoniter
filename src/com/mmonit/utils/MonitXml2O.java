package com.mmonit.utils;

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
	
	public static String getMonitId(String substring){
		String monitId = "";
		Document doc = null;
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
					monitId = value;
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return monitId;
		
	}
	
	public static String mqJSONDate(String substring){
		String eventJSONDate = "";
		Document doc = null;
		String monitId = "";
		StringBuffer stringBuffer = new StringBuffer();
		try {
			doc = DocumentHelper.parseText(substring);			
			Element rootElt = doc.getRootElement();
			Iterator<Attribute> attributeIterator = rootElt.attributeIterator();
			while(attributeIterator.hasNext()){
				Attribute a = (Attribute) attributeIterator.next();
				//根据monit id 进程判断 是否是新节点
				String name = a.getName();
				if(name.equals("id")){
					monitId = a.getValue();					
				}
			}
			stringBuffer.append("{\"" + monitId + "\":[");
			
			Iterator serverIterator = rootElt.elementIterator("server");
			String localhost = "";
			String address = "";
			while(serverIterator.hasNext()){
				Element serE = (Element) serverIterator.next();
				localhost = serE.elementTextTrim("localhostname");
				
				Iterator httpdIte = serE.elementIterator("httpd");
				while (httpdIte.hasNext()) {
					Element httpEle = (Element) httpdIte.next();
					address = httpEle.elementTextTrim("address");
				}				
			}
			stringBuffer.append("{\"hostname\"" + ":" + "\"" + localhost + "\"},");			
			stringBuffer.append("{\"ip\"" + ":" + "\"" + address + "\"},");
			
			Iterator eventIterator = rootElt.elementIterator("event");
			String servicename = "";
			String servicetype = "";
			String serviceaction = "";
			String eventmessage = "";
			while(eventIterator.hasNext()){
				Element eventEle = (Element)eventIterator.next();
				servicename = eventEle.elementTextTrim("service");
				servicetype = eventEle.elementTextTrim("type");
				serviceaction = eventEle.elementTextTrim("action");
				eventmessage = eventEle.elementTextTrim("message");
			}
			stringBuffer.append("{\"servicename\"" + ":" + "\"" + servicename + "\"},");
			stringBuffer.append("{\"servicetype\"" + ":" + "\"" + servicetype + "\"},");
			stringBuffer.append("{\"serviceaction\"" + ":" + "\"" + serviceaction + "\"},");
			stringBuffer.append("{\"eventmessage\"" + ":" + "\"" + eventmessage + "\"}");
			stringBuffer.append("]}");
			eventJSONDate = stringBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return eventJSONDate;
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
