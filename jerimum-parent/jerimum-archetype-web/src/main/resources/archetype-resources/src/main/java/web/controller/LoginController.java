#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.controller;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import ${package}.util.Constants;
import ${package}.util.NavigationConstants;
import br.com.jerimum.fw.logging.LoggerUtils;

/**
 * Web controller class for login management.
 * 
 * @author Dali Freire: dalifreire@gmail.com
 * @since 10/2015
 */
@Component("loginController")
@Scope("session")
public class LoginController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Returns the logged username.
	 * 
     * @return {@link String}
	 */
    public String getLoggedUserName() {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return (String) authentication.getPrincipal();
    }
    
}
