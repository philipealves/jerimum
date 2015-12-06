#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.data.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Application pointcut definitions.
 * 
 * @author https://github.com/dalifreire/jerimum
 * @since 10/2015
 */
@Aspect
@Component
public class AspectPointcuts {

    @Pointcut("execution(* ${package}.data.dao.*.*(..))")
    public void repository() {
        // Do nothing, only for pointcut definition
    }

}
