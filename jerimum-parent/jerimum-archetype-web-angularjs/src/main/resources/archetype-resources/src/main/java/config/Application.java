#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Startup spring boot application class.
 * 
 * @author Dali Freire - dalifreire@gmail.com
 * @since 10/2015
 */
@EnableAsync
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = { "br.com.jerimum", "${package}" })
public class Application extends WebMvcConfigurerAdapter {
	
    @Autowired
    private ApplicationConfig applicationConfig;

}
