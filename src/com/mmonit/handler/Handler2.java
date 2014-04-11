package com.mmonit.handler;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.mmonit.bean.MonitBean;

public class Handler2 implements Runnable {

	private ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
	MonitBean monitBean = new MonitBean();
	
	public Handler2(ConcurrentHashMap<String, String> concurrentHashMap, MonitBean monitBean){
		this.concurrentHashMap = concurrentHashMap;
		this.monitBean = monitBean;
		
	}
	
	
	@Override
	public void run() {
		try {
			if(concurrentHashMap==null){
				return;
			}
			System.out.println(concurrentHashMap.size());
			Iterator<Entry<String, String>> iterator = concurrentHashMap.entrySet().iterator();
			while(iterator.hasNext()){
				Entry<String, String> next = iterator.next();
				String mId = next.getKey();
				String mInfo = next.getValue();
				System.out.println(mId + "---->" +mInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
	}
}
