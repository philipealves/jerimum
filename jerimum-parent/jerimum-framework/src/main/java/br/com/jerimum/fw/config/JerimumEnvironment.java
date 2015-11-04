package br.com.jerimum.fw.config;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import br.com.jerimum.fw.logging.LoggerUtils;

/**
 * 
 * @author Dali Freire - dalifreire@gmail.com
 * @since 10/2015
 */
public final class JerimumEnvironment {

    public static final String DEFAULT_ENVIRONMENT = "default";
    public static final String DEFAULT_TEST_ENVIRONMENT = "test";
    public static final String INTERNAL_ENVIRONMENT_CONFIG = "jerimum.internal.environment.config";
    public static final String EXTERNAL_ENVIRONMENT_CONFIG = "jerimum.external.environment.config";

    private static final String FILE_PROTOCOL = "file:";
    private static String environment;

    /**
     * Returns the value of the JVM property <i>jerimum.internal.environment.config</i>.
     * 
     * @return {@link String}
     */
    public static String getInternalEnvironmentConfigValue() {

        return System.getProperty(INTERNAL_ENVIRONMENT_CONFIG, null);
    }

    /**
     * Returns the value of the JVM property <i>jerimum.external.environment.config</i>.
     * 
     * @return {@link String}
     */
    public static String getExternalEnvironmentConfigValue() {

        String externalEnvironment = System.getProperty(EXTERNAL_ENVIRONMENT_CONFIG, null);
        externalEnvironment = StringUtils.replace(externalEnvironment, "\\", "/");

        if (StringUtils.isNotBlank(externalEnvironment) && !StringUtils.startsWith(externalEnvironment, FILE_PROTOCOL)) {
            externalEnvironment = FILE_PROTOCOL + externalEnvironment;
        }
        externalEnvironment = StringUtils.removeEnd(externalEnvironment, File.separator);
        return StringUtils.isNotBlank(externalEnvironment) ? externalEnvironment : null;
    }

    /**
     * Returns the configured environment. <br/>
     * The following precedence sequence will be returned: <br>
     * <ul>
     * <li>1) External environment. </li>
     * <li>2) Internal environment. </li>
     * <li>3) Default environment.  </li>
     * 
     * @return {@link String}
     */
    public static String getEnvironment() {

        try {

        	if (StringUtils.isBlank(environment)) {
        		
	        	String jerimumEnvironment = getExternalEnvironmentConfigValue();
	        	if (StringUtils.isBlank(jerimumEnvironment)) {
	        		jerimumEnvironment = getInternalEnvironmentConfigValue();
	        		if (StringUtils.isBlank(jerimumEnvironment)) {
	        			jerimumEnvironment = DEFAULT_ENVIRONMENT;
	                }
	        	}
	        	environment = jerimumEnvironment;
        	}
        	
            return environment;

        } catch (SecurityException e) {
            LoggerUtils.logWarning(JerimumEnvironment.class, "Unable to read system property: \"" + INTERNAL_ENVIRONMENT_CONFIG + "\"", e);
            return DEFAULT_ENVIRONMENT;
        }
    }
    
    /**
     * Return <code>true</code> if it is a external environment.
     * 
     * @return boolean
     */
    public static boolean isExternalEnvironment() {
    	return isExternalEnvironment(getEnvironment());
    }

    /**
     * Return <code>true</code> if it is a external environment.
     * 
     * @param environment The environment name.
     * @return boolean
     */
    public static boolean isExternalEnvironment(String environment) {
    	return StringUtils.startsWith(environment, FILE_PROTOCOL);
    }
    
}
