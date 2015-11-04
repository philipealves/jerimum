#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.springframework.core.annotation.Order;

import br.com.jerimum.fw.config.JerimumWebApplicationInitializer;

@Order(1)
public class ApplicationInitializer extends JerimumWebApplicationInitializer {

	@Override
	public Class<?> getConfigurationClass() {
		return Application.class;
	}

}
