#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.aop;

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
 * @since 10/2015
 */
@Aspect
@Component
@Order(100)
public class AspectLog extends JerimumAspectLog {

    @Before("${package}.aop.AspectPointcuts.serviceImpl() || ${package}.aop.AspectPointcuts.messageListener()")
    public void logEntry(JoinPoint jp) throws Exception {
        super.logEntry(jp);
    }

    @AfterReturning(pointcut = "(${package}.aop.AspectPointcuts.serviceImpl() || ${package}.aop.AspectPointcuts.messageListener()) && (execution(void *..*(..)))")
    public void logExit(JoinPoint jp) throws Exception {
        super.logExit(jp);
    }

    @AfterReturning(pointcut = "(${package}.aop.AspectPointcuts.serviceImpl() || ${package}.aop.AspectPointcuts.messageListener()) && !(execution(void *..*(..)))", returning = "returningValue", argNames = "jp,returningValue")
    public void logExit(JoinPoint jp, Object returningValue) throws Exception {
        super.logExit(jp, returningValue);
    }

    @AfterThrowing(pointcut = "${package}.aop.AspectPointcuts.serviceImpl()", throwing = "ex", argNames = "jp,ex")
    public void logException(JoinPoint jp, Throwable ex) throws Throwable {
        super.logException(jp, ex);
    }

}

