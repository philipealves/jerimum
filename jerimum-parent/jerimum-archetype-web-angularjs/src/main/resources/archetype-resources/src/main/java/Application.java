#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.io.Serializable;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import br.com.jerimum.fw.logging.LoggerUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Startup spring application class.
 * 
 * @author Dali Freire - dalifreire@gmail.com
 * @since 10/2015
 */
@Data
@EqualsAndHashCode(callSuper = false)
@EnableAsync
@EnableWebMvc
@Configuration
@ComponentScan
@PropertySource({ "${spring.config.location}" })
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Application extends WebMvcConfigurerAdapter implements InitializingBean, Serializable {

	private static final long serialVersionUID = 7271156509131604941L;
	
	@Autowired
	private Environment environment;

	@Value("${basePackages}")
	private String basePackages;
	@Value("${applicationName}")
	private String applicationName;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		String[] profiles = environment.getActiveProfiles();
		if (profiles.length == 0) {
			LoggerUtils.logInfo(this.getClass(), "Active Spring Profiles: None");
		} else {
			LoggerUtils.logInfo(this.getClass(), "Active Spring Profiles: {}", (Object[]) profiles);
		}
	}

	@Bean
	@Autowired
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(Environment environment) {
		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		configurer.setEnvironment(environment);
		return configurer;
	}

	@Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(5);
        pool.setMaxPoolSize(34);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }
}
