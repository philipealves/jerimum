package br.com.jerimum.fw.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import br.com.jerimum.fw.logging.LoggerConfigurator;
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

        String jerimumEnvironmentPath = JerimumEnvironment.getEnvironment();
        String logbackConfigurationFile = StringUtils.replace(jerimumEnvironmentPath, "application.properties", "logback.xml");
        if (!JerimumEnvironment.isExternalEnvironment(jerimumEnvironmentPath)) {
            logbackConfigurationFile = getClass().getResource("/META-INF/environment/" + jerimumEnvironmentPath + "/logback.xml").getFile();
            jerimumEnvironmentPath = "classpath:META-INF/environment/" + jerimumEnvironmentPath + "/application.properties";
        }

        String jerimumEnvironmentName = JerimumEnvironment.getEnvironment();
        System.setProperty("spring.config.location", jerimumEnvironmentPath);
        System.setProperty("logback.configurationFile", logbackConfigurationFile);
        System.setProperty("logging.config", logbackConfigurationFile);
        System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, jerimumEnvironmentName);
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, jerimumEnvironmentName);

        
        new LoggerConfigurator(logbackConfigurationFile).configure();
        
        LoggerUtils.logInfo(this.getClass(), "================================================================");
        LoggerUtils.logInfo(this.getClass(), ".:  Starting Jerimum Application :.");
        LoggerUtils.logInfo(this.getClass(), ".:  Application name: {} :.", servletContext.getServletContextName());
        LoggerUtils.logInfo(this.getClass(), ".:  Environment: {} :.", jerimumEnvironmentName);
        LoggerUtils.logInfo(this.getClass(), ".:  Logback configuration file: {} :.", logbackConfigurationFile);

        servletContext.addListener(new RequestContextListener());

        ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", new DispatcherServlet());
        registration.setInitParameter("contextClass", AnnotationConfigWebApplicationContext.class.getName());
        registration.setInitParameter("contextConfigLocation", getConfigurationClass().getName());
        registration.setLoadOnStartup(1);
        registration.addMapping("/rest/*");

    }

    public abstract Class<?> getConfigurationClass();

}
