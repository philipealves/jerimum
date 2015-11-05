#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.springframework.core.annotation.Order;

import br.com.jerimum.fw.config.JerimumWebApplicationInitializer;

/**
 * Startup spring application class.
 * 
 * @author Dali Freire - dalifreire@gmail.com
 * @since 10/2015
 */
@Order(1)
public class ApplicationInitializer extends JerimumWebApplicationInitializer {

	@Override
	public Class<?> getConfigurationClass() {
		return Application.class;
	}

}
