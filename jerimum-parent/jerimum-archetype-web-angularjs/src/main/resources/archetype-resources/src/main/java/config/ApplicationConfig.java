#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.config;

import java.io.Serializable;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import br.com.jerimum.fw.logging.LoggerUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Spring application config class.
 * 
 * @author Dali Freire: dalifreire@gmail.com
 * @since 10/2015
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Configuration
public class ApplicationConfig implements InitializingBean, Serializable {

	private static final long serialVersionUID = 7677333568062654076L;

	@Autowired
	private Environment environment;
	
    private String basePackages;
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
	
}
