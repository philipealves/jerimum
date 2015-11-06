#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import br.com.jerimum.fw.logging.LoggerUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Configuration
@PropertySource({ "${spring.config.location}" })
public class AppConfig implements InitializingBean {

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

}
