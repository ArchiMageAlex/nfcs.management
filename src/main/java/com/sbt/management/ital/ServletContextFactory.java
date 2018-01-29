package com.sbt.management.ital;

import javax.servlet.ServletContext;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.web.context.ServletContextAware;

public class ServletContextFactory implements FactoryBean<ServletContext>, ServletContextAware {
	private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		this.servletContext = servletContext;
	}

	public ServletContext getObject() throws Exception {
		// TODO Auto-generated method stub
		return this.servletContext;
	}

	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return null;
	}

}