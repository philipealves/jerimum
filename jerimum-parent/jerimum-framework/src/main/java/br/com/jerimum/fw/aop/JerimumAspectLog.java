package br.com.jerimum.fw.aop;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;

import br.com.jerimum.fw.exception.JerimumException;
import br.com.jerimum.fw.exception.ValidationException;
import br.com.jerimum.fw.logging.LoggerUtils;

/**
 * 
 * @author Dali Freire - dalifreire@gmail.com
 * @since 10/2015
 */
public class JerimumAspectLog {

    private static int sequence = 1;

    protected void logEntry(JoinPoint jp) throws Exception {

        Logger logger = JerimumAspectUtils.getLogger(jp);
        if (logger.isDebugEnabled()) {

            StringBuilder sb = new StringBuilder();
            sb.append("Entry --> ");
            sb.append(JerimumAspectUtils.methodName(jp));
            sb.append('(');
            JerimumAspectUtils.appendArguments(jp.getArgs(), sb);
            sb.append(')');
            LoggerUtils.logDebug(logger, sb.toString());
        }
    }

    protected void logExit(JoinPoint jp) throws Exception {

        Logger logger = JerimumAspectUtils.getLogger(jp);
        LoggerUtils.logDebug(logger, "Exit <-- " + JerimumAspectUtils.methodName(jp) + " - void");
    }

    protected void logExit(JoinPoint jp, Object returningValue) throws Exception {

        Logger logger = JerimumAspectUtils.getLogger(jp);
        LoggerUtils.logDebug(logger,
            "Exit <-- " + JerimumAspectUtils.methodName(jp) + " - " + JerimumAspectUtils.displayObject(returningValue));
    }

    protected void logException(JoinPoint jp, Throwable ex) throws Throwable {

        String occurrenceId = nextOccurrenceId();
        String methodName = JerimumAspectUtils.methodName(jp);

        Logger logger = JerimumAspectUtils.getLogger(jp);
        LoggerUtils.logError(logger, "Exit <-- {} - EXCEPTION [{}] {}", methodName, occurrenceId, ex.getMessage());

        if (logger.isErrorEnabled()) {
            if (ex instanceof JerimumException) {

                JerimumException fex = (JerimumException) ex;
                dumpException(fex, true, logger);

            } else if (ex instanceof ValidationException) {

                JerimumException fex = new JerimumException(ex.getMessage(), ex, new Date(), occurrenceId, methodName,
                    jp.getThis(), jp.getArgs());
                dumpException(fex, false, logger);

            } else {

                JerimumException fex = new JerimumException(ex.getMessage(), ex, new Date(), occurrenceId, methodName,
                    jp.getThis(), jp.getArgs());
                dumpException(fex, true, logger);
            }
        }

        throw ex;
    }

    protected void dumpException(JerimumException fex, boolean appendRootCause, Logger logger) {

        StringBuilder args = new StringBuilder();
        JerimumAspectUtils.appendArguments(fex.getArgs(), args);

        StringBuilder dump = new StringBuilder();
        dump.append("# Identificador do erro: {} \n");
        dump.append("# Timestamp: {} \n");
        dump.append("# Mensagem de erro: {} \n");
        dump.append("# Service: {} \n");
        dump.append("# Metodo: {} \n");
        dump.append("# Argumentos: ({}) \n");

        String rootCauseString = null;
        if (appendRootCause) {
            Throwable rootCause = fex;
            while (rootCause.getCause() != null) {
                rootCause = rootCause.getCause();
            }
            rootCauseString = getRootCause(rootCause);
            dump.append("# Exception Root Cause: {} \n");
        }
        dump.append("-");
        LoggerUtils.logError(logger, dump.toString(), fex.getOccurrenceId(), fex.getTimeStamp(), fex.getMessage(),
            fex.getCurrent(), fex.getMethodName(), args, rootCauseString);
    }

    /**
     * Returns the exception stack in {@link String} format.
     * 
     * @param cause
     * @return {@link String}
     */
    protected String getRootCause(Throwable cause) {

        if (cause instanceof ValidationException) {

            return cause.getMessage();

        } else {

            Throwable rootCause = cause;
            while (rootCause.getCause() != null) {
                rootCause = rootCause.getCause();
            }

            StringWriter rootCauseStack = new StringWriter();
            PrintWriter writer = new PrintWriter(rootCauseStack);
            rootCause.printStackTrace(writer);
            return rootCauseStack.toString();
        }
    }

    /**
     * Returns the next occurrence id.
     * 
     * @return {@link String}
     */
    protected static synchronized String nextOccurrenceId() {
        return String.valueOf(++sequence);
    }

}

