#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.i18n;

import lombok.Getter;

/**
 * Enumeration containing the keys for internalization. 
 * 
 * @author https://github.com/dalifreire/jerimum
 * @since 11/2015
 */
public enum I18nKeys {
    
    HelloWorldXxx("msg.hello.world.xxx");
    
    @Getter
    String key;
    
    private I18nKeys(String key) {
        this.key = key;
    }
}
