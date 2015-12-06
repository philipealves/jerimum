#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.data.config;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Properties configurations used in the application. 
 * 
 * @author https://github.com/dalifreire/jerimum
 * @since 12/2015
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Component
public class ApplicationConfig implements Serializable {

    private static final long serialVersionUID = 7896624401620243915L;

    @Value("${basePackages}")
    private String basePackages;
    
    @Value("${applicationName}")
    private String applicationName;

    @Autowired
    private ApplicationConfigDatabase appConfigDatabase;

}
