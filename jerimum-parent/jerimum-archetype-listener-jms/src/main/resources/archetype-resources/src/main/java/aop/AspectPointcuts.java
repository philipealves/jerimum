#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.aop;

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

    @Pointcut("execution(* ${package}.service.impl.*ServiceImpl.*(..))")
    public void serviceImpl() {
    }


    @Pointcut("execution(* ${package}.jms.listener.*MessageListener.*(..))")
    public void messageListener() {
    }

}
