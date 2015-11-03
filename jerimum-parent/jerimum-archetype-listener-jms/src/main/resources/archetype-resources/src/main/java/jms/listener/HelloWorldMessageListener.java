#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.jms.listener;

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
public class HelloWorldMessageListener implements MessageListener {

	@Override
    public void onMessage(Message message) {

        try {
        
        	String correlationID = StringUtils.removeStartIgnoreCase(message.getJMSCorrelationID(), "ID:");
        	LoggerUtils.logDebug(this.getClass(), "New message received. CorrelationID: '{}'", correlationID);

        	String messageStr = null;
            if (message instanceof BytesMessage) {

                BytesMessage bytesMessage = (BytesMessage) message;
                byte[] body = new byte[(int) bytesMessage.getBodyLength()];
                bytesMessage.readBytes(body);
                messageStr = new String(body);

            } else {
                TextMessage textMessage = (TextMessage) message;
                messageStr = textMessage.getText();
            }
            
            // TODO
            LoggerUtils.logDebug(this.getClass(), "Message = '{}'", messageStr);

        } catch (Exception e) {
            LoggerUtils.logError(this.getClass(), "Unable to get jms message.", e);
        }
    }

}
