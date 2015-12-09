#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.jerimum.fw.i18n.I18nUtils;
import ${package}.i18n.I18nKeys;
import ${package}.util.Constants;

/**
 * Hello World sample controller.
 * 
 * @author https://github.com/dalifreire/jerimum
 * @since 11/2015
 */
@RestController
@RequestMapping(value = Constants.HELLO_WORLD_CONTROLLER)
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = Constants.HELLO_WORLD_SAMPLEGET, method = RequestMethod.GET)
    public Map<String, Object> sampleget() {

        String helloWorldMessage = I18nUtils.getMsg(messageSource, I18nKeys.HelloWorldXxx.getKey(), "Fulano");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", helloWorldMessage);
        return model;
    }

}

