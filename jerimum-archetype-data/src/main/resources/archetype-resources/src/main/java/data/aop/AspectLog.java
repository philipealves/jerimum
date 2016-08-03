#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.data.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import br.com.jerimum.fw.aop.JerimumAspectLog;

/**
 * Aspect to intercept and log application methods.
 * 
 * @author https://github.com/dalifreire/jerimum
 */
@Aspect
@Component
@Order(100)
public class AspectLog extends JerimumAspectLog {

    @Before("${package}.data.aop.AspectPointcuts.repository()")
    @Override
    public void logEntry(JoinPoint jp) throws Exception {
        super.logEntry(jp);
    }

    @AfterReturning(pointcut = "${package}.data.aop.AspectPointcuts.repository() && (execution(void *..*(..)))")
    @Override
    public void logExit(JoinPoint jp) throws Exception {
        super.logExit(jp);
    }

    @AfterReturning(pointcut = "${package}.data.aop.AspectPointcuts.repository() && !(execution(void *..*(..)))", returning = "returningValue", argNames = "jp,returningValue")
    @Override
    public void logExit(JoinPoint jp, Object returningValue) throws Exception {
        super.logExit(jp, returningValue);
    }

    @AfterThrowing(pointcut = "${package}.data.aop.AspectPointcuts.repository()", throwing = "ex", argNames = "jp,ex")
    @Override
    public void logException(JoinPoint jp, Throwable ex) throws Throwable {
        super.logException(jp, ex);
    }

}

