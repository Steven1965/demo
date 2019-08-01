package com.stevenlegg.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.stevenlegg.demo.interceptors.StudentServiceInterceptor;

@Configuration
public class StudentServiceConfig implements WebMvcConfigurer {
	
	   @Autowired
	   StudentServiceInterceptor studentServiceInterceptor;
	
	   @Override
	   public void addInterceptors(InterceptorRegistry registry) {
		   System.out.println("Adding Interceptors");
	      registry.addInterceptor(studentServiceInterceptor);
	   }
}
