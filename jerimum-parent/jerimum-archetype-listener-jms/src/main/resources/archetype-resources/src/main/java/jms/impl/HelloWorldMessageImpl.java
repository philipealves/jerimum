#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.jms.impl;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.com.jerimum.fw.jms.impl.AbstractJMSMessageImpl;
import ${package}.jms.HelloWorldMessage;

/**
 * Sample Hello World message gateway class to demonstrate jms messaging operations.
 * 
 * @author Dali Freire - dalifreire@gmail.com
 * @since 11/2015
 */
@Component
public class HelloWorldMessageImpl extends AbstractJMSMessageImpl implements HelloWorldMessage {

	@Autowired
	@Qualifier("jmsConnectionFactory")
	private ConnectionFactory jmsConnectionFactory;
	
	@Autowired
	@Qualifier("requestQueue")
	private Queue requestQueue;

	@Autowired
	@Qualifier("responseQueue")
	private Queue responseQueue;

	@Autowired
	@Qualifier("jmsTimeout")
	private Long timeout;
	
	@Override
	public Queue getRequestQueue() {
		return requestQueue;
	}

	@Override
	public Queue getResponseQueue() {
		return responseQueue;
	}

	@Override
	public ConnectionFactory getConnectionFactory() {
		return jmsConnectionFactory;
	}

	@Override
	public long getTimeout() {
		return timeout;
	}

}
