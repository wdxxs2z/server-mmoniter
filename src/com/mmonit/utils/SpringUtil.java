package com.mmonit.utils;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public final class SpringUtil {

    private static ClassPathXmlApplicationContext  ctx = null;
    
    public static void start(){
    	ctx = new ClassPathXmlApplicationContext("classpath:beans.xml");
    	ctx.start();
    }
    public static Object getBean(String beanName){
         return ctx.getBean(beanName);
    }  
    
    public static Object getBean(Class c){
        return ctx.getBean(c);
   }  
}
