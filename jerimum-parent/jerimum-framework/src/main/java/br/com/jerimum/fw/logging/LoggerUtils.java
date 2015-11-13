package br.com.jerimum.fw.logging;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for logs.
 * 
 * @author Dali Freire: dalifreire@gmail.com
 * @since 10/2015
 */
public class LoggerUtils {

    private static Map<Class<?>, Logger> loggers = new HashMap<Class<?>, Logger>();

    private LoggerUtils() {

    }

    /**
     * Cria e retorna uma instancia de {@link Logger} para a classe passada como parametro.
     * 
     * @param classe
     * @return {@link Logger}
     */
    public static Logger getLogger(Class<?> classe) {

        Logger logger = loggers.get(classe);
        if (logger == null) {
            logger = LoggerFactory.getLogger(classe);
            loggers.put(classe, logger);
        }
        return logger;
    }

    /**
     * Verifica o estado do logger e caso esteja habilitado registra o log a nivel debug.
     * 
     * @param {@link Class} classe
     * @param {@link String} mensagem
     * @param {@link Object} params
     */
    public static void logDebug(Class<?> classe, String mensagem, Object... params) {

        logDebug(getLogger(classe), mensagem, params);
    }

    /**
     * Verifica o estado do logger e caso esteja habilitado registra o log a nivel debug.
     * 
     * @param {@link Logger} logger
     * @param {@link String} mensagem
     * @param {@link Object} params
     */
    public static void logDebug(Logger logger, String mensagem, Object... params) {
        if (logger.isDebugEnabled()) {
            logger.debug(mensagem, params);
        }
    }

    /**
     * Verifica o estado do logger e caso esteja habilitado registra o log a nivel info.
     * 
     * @param {@link Class} classe
     * @param {@link String} mensagem
     * @param {@link Object} params
     */
    public static void logInfo(Class<?> classe, String mensagem, Object... params) {

        logInfo(getLogger(classe), mensagem, params);
    }

    /**
     * Verifica o estado do logger e caso esteja habilitado registra o log a nivel info.
     * 
     * @param {@link Logger} logger
     * @param {@link String} mensagem
     * @param {@link Object} params
     */
    public static void logInfo(Logger logger, String mensagem, Object... params) {
        if (logger.isInfoEnabled()) {
            logger.info(mensagem, params);
        }
    }

    /**
     * Verifica o estado do logger e caso esteja habilitado registra o log a nivel error.
     * 
     * @param {@link Class} classe
     * @param {@link String} mensagem
     * @param {@link Object} params
     */
    public static void logError(Class<?> classe, String mensagem, Object... params) {

        logError(getLogger(classe), mensagem, params);
    }

    /**
     * Verifica o estado do logger e caso esteja habilitado registra o log a nivel error.
     * 
     * @param {@link Logger} logger
     * @param {@link String} mensagem
     * @param {@link Object} params
     */
    public static void logError(Logger logger, String mensagem, Object... params) {
        if (logger.isErrorEnabled()) {
            logger.error(mensagem, params);
        }
    }

    /**
     * Verifica o estado do logger e caso esteja habilitado registra o log a nivel error.
     * 
     * @param {@link Class} classe
     * @param {@link String} mensagem
     * @param {@link Object} throwable
     */
    public static void logError(Class<?> classe, String mensagem, Throwable throwable) {

        logError(getLogger(classe), mensagem, throwable);
    }

    /**
     * Verifica o estado do logger e caso esteja habilitado registra o log a nivel error.
     * 
     * @param {@link Logger} logger
     * @param {@link String} mensagem
     * @param {@link Object} throwable
     */
    public static void logError(Logger logger, String mensagem, Throwable throwable) {
        if (logger.isErrorEnabled()) {
            logger.error(mensagem, throwable);
        }
    }

    /**
     * Verifica o estado do logger e caso esteja habilitado registra o log a nivel trace.
     * 
     * @param {@link Class} classe
     * @param {@link String} mensagem
     * @param {@link Object} params
     */
    public static void logTrace(Class<?> classe, String mensagem, Object... params) {

        logTrace(getLogger(classe), mensagem, params);
    }

    /**
     * Verifica o estado do logger e caso esteja habilitado registra o log a nivel trace.
     * 
     * @param {@link Logger} logger
     * @param {@link String} mensagem
     * @param {@link Object} params
     */
    public static void logTrace(Logger logger, String mensagem, Object... params) {
        if (logger.isTraceEnabled()) {
            logger.trace(mensagem, params);
        }
    }

    /**
     * Verifica o estado do logger e caso esteja habilitado registra o log a nivel warning.
     * 
     * @param {@link Class} classe
     * @param {@link String} mensagem
     * @param {@link Object} params
     */
    public static void logWarning(Class<?> classe, String mensagem, Object... params) {

        logWarning(getLogger(classe), mensagem, params);
    }

    /**
     * Verifica o estado do logger e caso esteja habilitado registra o log a nivel warning.
     * 
     * @param {@link Logger} logger
     * @param {@link String} mensagem
     * @param {@link Object} params
     */
    public static void logWarning(Logger logger, String mensagem, Object... params) {
        if (logger.isWarnEnabled()) {
            logger.warn(mensagem, params);
        }
    }

    /**
     * Retorna o arquivo de log.
     * 
     * @return {@link File}
     */
    public static File getLogFile() {
        // TODO
        // return br.com.jerimum.config.LoggerConfigurator.getLogFile();
        return null;
    }

}
