#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jerimum.fw.i18n.I18nUtils;
import ${package}.i18n.I18nKeys;

/**
 * Hello World sample controller.
 * 
 * @author https://github.com/dalifreire/jerimum
 * @since 11/2015
 */
@RestController
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping("/resource")
    public Map<String, Object> home() {

        String helloWorldMessage = I18nUtils.getMsg(messageSource, I18nKeys.HelloWorldXxx.getKey(), getLoggedUsername());

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", helloWorldMessage);
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

