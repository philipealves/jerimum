#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.conf;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Spring boot application config class.
 * 
 * @author Dali Freire: dalifreire@gmail.com
 * @since 10/2015
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class ApplicationConfig implements InitializingBean, Serializable {

	private static final long serialVersionUID = 7677333568062654076L;
	private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

	@Autowired
	private Environment environment;
	
    private String basePackages;
    private String applicationName;

	@Override
	public void afterPropertiesSet() throws Exception {
        String[] profiles = environment.getActiveProfiles();
        if (profiles.length == 0) {
        	logger.info("Active Spring Profiles: None");
        } else {
        	logger.info("Active Spring Profiles: {}", (Object[]) profiles);
        }	
	}
	
}
