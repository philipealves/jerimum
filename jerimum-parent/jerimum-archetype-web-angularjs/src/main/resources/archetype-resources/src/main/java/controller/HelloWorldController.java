#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello World sample controller.
 * 
 * @author Dali Freire: dalifreire@gmail.com
 * @since 10/2015
 */
@RestController
public class HelloWorldController {
	
	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	@RequestMapping("/resource")
	public Map<String, Object> home() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", String.format("Hello World '%s'!", getLoggedUsername()));
		return model;
	}

	/**
	 * Returns the logged username.
	 * 
	 * @return {@link String}
	 */
	private String getLoggedUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (auth != null ? auth.getName() : null);
	}
	
}
