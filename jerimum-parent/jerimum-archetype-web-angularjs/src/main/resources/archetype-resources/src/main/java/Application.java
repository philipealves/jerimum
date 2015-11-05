#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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
