package br.com.jerimum.fw.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

import br.com.jerimum.fw.logging.LoggerUtils;

/**
 * Aspect for application monitoring.
 * 
 * @author Dali Freire - dalifreire@gmail.com
 */
public class JerimumAspectMonitor {

    /**
     * Stores the monitor values for the pointcut.
     * 
     * @param jp
     * @return {@link Object}
     * @throws Throwable
     */
    protected Object monitor(ProceedingJoinPoint jp) throws Throwable {

        Logger logger = JerimumAspectUtils.getLogger(jp);
        Monitor monitor = MonitorFactory.start(JerimumAspectUtils.fullMethodName(jp));
        Object retVal;
        if (monitor.isEnabled()) {
            try {
                retVal = jp.proceed();
            } finally {
                monitor.stop();
                LoggerUtils.logTrace(logger,
                    "-- " + JerimumAspectUtils.methodName(jp) + ": " + JerimumAspectMonitor.getMonitorValues(monitor));
            }
        } else {
            retVal = jp.proceed();
        }
        return retVal;
    }

    /**
     * Returns an {@link StringBuilder} with the stored monitor values.
     * 
     * @param monitor
     * @return {@link StringBuilder}
     */
    protected static StringBuilder getMonitorValues(Monitor monitor) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hits=").append(monitor.getHits()).append(", ");
        sb.append("LastValue=").append(monitor.getLastValue()).append(", ");
        sb.append("Min=").append(monitor.getMin()).append(", ");
        sb.append("Max=").append(monitor.getMax()).append(", ");
        sb.append("Avg=").append(monitor.getAvg()).append(", ");
        sb.append("Total=").append(monitor.getTotal()).append(", ");
        sb.append("StdDev=").append(monitor.getStdDev()).append(", ");
        sb.append("FirstAccess=").append(monitor.getFirstAccess()).append(", ");
        sb.append("LastAccess=").append(monitor.getLastAccess());
        return sb;
    }
}
