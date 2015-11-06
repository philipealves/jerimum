#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.jms.listener;

import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.jerimum.fw.logging.LoggerUtils;
import ${package}.service.HelloWorldService;

/**
 * Sample Hello World message listener.
 * 
 * @author Dali Freire: dalifreire@gmail.com
 * @since 11/2015
 */
@Component
public class HelloWorldMessageListener implements MessageListener {

	@Autowired
	private HelloWorldService helloWorldService;

	@Override
    public void onMessage(Message message) {

        try {
        
        	String messageID = StringUtils.removeStartIgnoreCase(message.getJMSMessageID(), "ID:");
        	String correlationID = StringUtils.removeStartIgnoreCase(message.getJMSCorrelationID(), "ID:");
        	LoggerUtils.logDebug(this.getClass(), "New message received. MessageID: '{}', CorrelationID: '{}'", messageID, correlationID);

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
            
            // SENDING A MESSAGE
            helloWorldService.propagateReceivedMessage(messageStr);

            // SENDING A MESSAGE AND GETTING THE RESPONSE
            String requestMessage = "Are you there?";
            String response = helloWorldService.sendAndReceive(requestMessage);
            
            LoggerUtils.logDebug(this.getClass(), "Received response: '{}'", response);

        } catch (Exception e) {
            LoggerUtils.logError(this.getClass(), "Unable to get jms message.", e);
        }
    }
	
}
