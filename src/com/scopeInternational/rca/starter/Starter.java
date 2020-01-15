package com.scopeInternational.rca.starter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.scopeInternational.rca.configurer.AppConfig;
import com.scopeInternational.rca.executor.CommandExecutor;

public class Starter {

	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
	    CommandExecutor commandExecutor = (CommandExecutor)applicationContext.getBean("commandExecutor");
	    commandExecutor.execute();
	}

}
