package kasse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import basar.domain.logic.BasarKasseFacade;

public enum KasseHolder {
	
	INSTANCE;
	
	public BasarKasseFacade getKasse(){
		ApplicationContext context = new ClassPathXmlApplicationContext("/spring/dao.xml", "/spring/remote.xml");
		BasarKasseFacade bean = context.getBean(BasarKasseFacade.class);
		return bean;
	}
	
}
