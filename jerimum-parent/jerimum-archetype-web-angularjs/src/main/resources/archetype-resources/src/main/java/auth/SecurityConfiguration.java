#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import br.com.jerimum.fw.auth.JerimumDefaultSecurityConfiguration;
import ${package}.util.NavigationConstants;

/**
 * Security configuration for the application.
 * 
 * @author Dali Freire - dalifreire@gmail.com
 * @since 10/2015
 */
@EnableWebSecurity
public class SecurityConfiguration extends JerimumDefaultSecurityConfiguration {

	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
	}
	
	@Override
	protected String getLoginPage() {
		return NavigationConstants.LOGIN_PAGE;
	}

	@Override
	protected String[] getUnsecuredResources() {
		return new String[] { "/pages/open/**", "/resources/**", "/wro/**", "/" };
	}
	
}
