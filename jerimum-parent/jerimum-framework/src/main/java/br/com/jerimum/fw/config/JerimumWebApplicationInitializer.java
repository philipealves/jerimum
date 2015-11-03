package br.com.jerimum.fw.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.core.env.AbstractEnvironment;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import br.com.jerimum.fw.logging.LoggerUtils;

/**
 * 
 * @author Dali Freire - dalifreire@gmail.com
 * @since 10/2015
 */
public abstract class JerimumWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		String jerimumEnvironment = JerimumEnvironment.getEnvironment();

		System.setProperty("spring.config.location", jerimumEnvironment + ",classpath:META-INF/environment/" + jerimumEnvironment + "/application.properties");
		System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, jerimumEnvironment);
		System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, jerimumEnvironment);

		LoggerUtils.logInfo(this.getClass(), "================================================================");
		LoggerUtils.logInfo(this.getClass(), ".:  Starting Jerimum Application :.");
		LoggerUtils.logInfo(this.getClass(), ".:  Application name:	{} :.", servletContext.getServletContextName());
		LoggerUtils.logInfo(this.getClass(), ".:  Environment:	{} :.", jerimumEnvironment);

		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(getConfigurationClass());
		servletContext.addListener(new ContextLoaderListener(context));
		servletContext.addListener(new RequestContextListener());

		ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", new DispatcherServlet());
		registration.setInitParameter("contextClass", AnnotationConfigWebApplicationContext.class.getName());
		registration.setInitParameter("contextConfigLocation", getConfigurationClass().getName());
		registration.setLoadOnStartup(1);
		registration.addMapping("/rest/*");
		
	}

	public abstract Class<?> getConfigurationClass();

}
