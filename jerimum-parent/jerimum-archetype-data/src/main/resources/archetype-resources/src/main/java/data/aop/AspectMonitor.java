#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.data.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import br.com.jerimum.fw.aop.JerimumAspectMonitor;

/**
 * Aspect for application monitoring.
 * 
 * @author https://github.com/dalifreire/jerimum
 * @since 10/2015
 */
@Aspect
@Component
@Order(200)
public class AspectMonitor extends JerimumAspectMonitor {

    @Around("${package}.data.aop.AspectPointcuts.repository()")
    @Override
    protected Object monitor(ProceedingJoinPoint jp) throws Throwable {
        return super.monitor(jp);
    }

}

