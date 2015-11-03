#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.listener;

import java.util.List;
import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import br.com.jerimum.fw.logging.LoggerUtils;

/**
 * 
 * @author Dali Freire: dalifreire@gmail.com
 * @since 11/2015
 */
@Component
public class HelloWorldJMSMessageListener implements MessageListener {

    public void onMessage(Message message) {

        try {
        
        	String correlationID = StringUtils.removeStartIgnoreCase(message.getJMSCorrelationID(), "ID:");
        	LoggerUtils.logDebug(this.getClass(), "New message received. CorrelationID: '{}'", correlationID);

        	String message = null;
            if (message instanceof BytesMessage) {

                BytesMessage bytesMessage = (BytesMessage) message;
                byte[] body = new byte[(int) bytesMessage.getBodyLength()];
                bytesMessage.readBytes(body);
                message = new String(body);

            } else {
                TextMessage textMessage = (TextMessage) message;
                message = textMessage.getText();
            }
            
            // TODO
            LoggerUtils.logDebug(this.getClass(), "Message = '{}'", message);

        } catch (Exception e) {
            LoggerUtils.logError(this.getClass(), e);
        }
    }

}
