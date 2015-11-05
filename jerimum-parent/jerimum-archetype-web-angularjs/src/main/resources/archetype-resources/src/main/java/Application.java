#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.scheduling.annotation.EnableAsync;

import ${package}.config.AppConfig;

/**
 * Startup spring application class.
 * 
 * @author Dali Freire - dalifreire@gmail.com
 * @since 10/2015
 */
@EnableAsync
@Configuration
@ComponentScan
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Application extends WebMvcConfigurerAdapter implements Serializable {

	private static final long serialVersionUID = 7271156509131604941L;

	@Autowired
	private AppConfig appConfig;

}
