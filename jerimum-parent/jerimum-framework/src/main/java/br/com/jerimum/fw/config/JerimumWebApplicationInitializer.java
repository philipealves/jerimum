package br.com.jerimum.fw.config;

import java.io.File;

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
 * Startup application class.
 * 
 * @author Dali Freire - dalifreire@gmail.com
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

        String jerimumEnvironmentPath = getEnvironmentPath();
        boolean externalConfig = JerimumEnvironment.isExternalEnvironment(jerimumEnvironmentPath);
        String logbackConfigurationFile = StringUtils.remove(jerimumEnvironmentPath, "/application.properties") + "/logback.xml";
        logbackConfigurationFile = StringUtils.remove(logbackConfigurationFile, "file:");
        if (!externalConfig) {
            try {
                logbackConfigurationFile = getClass().getResource("/META-INF/environment/" + jerimumEnvironmentPath + "/logback.xml").getFile();
                jerimumEnvironmentPath = "classpath:META-INF/environment/" + jerimumEnvironmentPath + "/application.properties";
            } catch (Exception e) {
                System.err.println("###### Configuration files not found! -> " + jerimumEnvironmentPath + " ######");
                throw e;
            }
        }
        String jerimumConfigurationFile = StringUtils.replace(logbackConfigurationFile, "logback.xml", "application.properties");

        System.setProperty("spring.config.location", externalConfig ? String.format("file:/%s", jerimumConfigurationFile) : jerimumEnvironmentPath);
        System.setProperty("logback.configurationFile", logbackConfigurationFile);
        System.setProperty("jerimum.configurationFile", jerimumConfigurationFile);
        if (!externalConfig) {
            System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, JerimumEnvironment.getEnvironment());
            System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, JerimumEnvironment.getEnvironment());
        }

        new LoggerConfigurator(logbackConfigurationFile).configure();

        LoggerUtils.logInfo(this.getClass(), "================================================================");
        LoggerUtils.logInfo(this.getClass(), ".:  Starting Application :.");
        LoggerUtils.logInfo(this.getClass(), ".:  Application name: {} :.", servletContext.getServletContextName());
        LoggerUtils.logInfo(this.getClass(), ".:  Jerimum configuration file: {} :.", jerimumConfigurationFile);
        LoggerUtils.logInfo(this.getClass(), ".:  Logback configuration file: {} :.", logbackConfigurationFile);

        servletContext.addListener(new RequestContextListener());

        ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", new DispatcherServlet());
        registration.setInitParameter("contextClass", AnnotationConfigWebApplicationContext.class.getName());
        registration.setInitParameter("contextConfigLocation", getConfigurationClass().getName());
        registration.setLoadOnStartup(1);
        registration.addMapping("/rest/*");

    }

    private String getEnvironmentPath() {

        String applicationEnvironment = getEnvironmentJVMParam();
        if (StringUtils.isNotBlank(applicationEnvironment)) {
            String applicationEnvironmentValue = System.getProperty(applicationEnvironment, null);
            if (StringUtils.isNotBlank(applicationEnvironmentValue)) {
                applicationEnvironmentValue = StringUtils.replace(applicationEnvironmentValue, "\\", "/");
                applicationEnvironmentValue = StringUtils.removeEnd(applicationEnvironmentValue, File.separator);
                applicationEnvironmentValue = StringUtils.trimToNull(applicationEnvironmentValue);
                return applicationEnvironmentValue;
            }
        }
        return JerimumEnvironment.getEnvironment();
    }

    public abstract Class<?> getConfigurationClass();

    public abstract String getEnvironmentJVMParam();

}
