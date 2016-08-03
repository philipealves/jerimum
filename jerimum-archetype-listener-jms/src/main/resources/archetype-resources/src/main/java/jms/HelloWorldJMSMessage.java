#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.jms;

import br.com.jerimum.fw.jms.JMSMessage;

/**
 * Sample Hello World message gateway interface to demonstrate jms messaging operations.
 * 
 * @author https://github.com/dalifreire/jerimum
 * @since 11/2015
 */
public interface HelloWorldJMSMessage extends JMSMessage {

}
