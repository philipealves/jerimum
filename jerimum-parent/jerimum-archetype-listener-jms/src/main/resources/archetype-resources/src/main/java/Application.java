#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.io.Serializable;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.scheduling.annotation.EnableAsync;

import br.com.jerimum.fw.logging.LoggerUtils;
import ${package}.config.AppConfig;
import ${package}.jms.listener.HelloWorldMessageListener;

/**
 * Startup spring application class.
 * 
 * @author Dali Freire - dalifreire@gmail.com
 * @since 10/2015
 */
@EnableAsync
@Configuration
@ComponentScan
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Application implements Serializable {

	private static final long serialVersionUID = 7271156509131604941L;

	@Autowired
	private AppConfig appConfig;

	
	
	@Bean(name = "jmsConnectionFactory")
	public ConnectionFactory connectionFactory() throws NamingException {

		Context ctx = new InitialContext();
		ConnectionFactory jmsConnectionFactory = (ConnectionFactory) ctx.lookup(appConfig.getJms().getConnectionFactoryName());
		LoggerUtils.logDebug(this.getClass(), "Looking up jms connection factory reference: '{}' -> '{}'", appConfig.getJms().getConnectionFactoryName(), jmsConnectionFactory);
		
		return jmsConnectionFactory;
	}
	
	@Bean(name = "requestQueue")
	public Queue requestQueue() throws NamingException {
		
		Context ctx = new InitialContext();
		Queue requestQueue = (Queue) ctx.lookup(appConfig.getJms().getRequestQueueName());
		LoggerUtils.logDebug(this.getClass(), "Looking up jms request queue: '{}' -> '{}'", appConfig.getJms().getRequestQueueName(), requestQueue);

		return requestQueue;
	}
	
	@Bean(name = "responseQueue")
	public Queue responseQueue() throws NamingException {
		
		Context ctx = new InitialContext();
		Queue responseQueue = (Queue) ctx.lookup(appConfig.getJms().getResponseQueueName());
		LoggerUtils.logDebug(this.getClass(), "Looking up jms response queue: '{}' -> '{}'", appConfig.getJms().getResponseQueueName(), responseQueue);
		
		return responseQueue;
	}
	
	@Bean
	@Autowired
	public DefaultMessageListenerContainer helloWorldMessageListenerContainer(ConnectionFactory connectionFactory, @Qualifier("requestQueue") Queue destinationQueue, HelloWorldMessageListener messageListener) throws JMSException {

		DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
		listenerContainer.setConnectionFactory(connectionFactory);
		listenerContainer.setDestinationName(destinationQueue.getQueueName());
		listenerContainer.setMessageListener(messageListener);
		listenerContainer.setConcurrentConsumers(appConfig.getJms().getConcurrentConsumers().intValue());
		listenerContainer.setMaxConcurrentConsumers(appConfig.getJms().getMaxConcurrentConsumers().intValue());
		return listenerContainer;
	}
}
