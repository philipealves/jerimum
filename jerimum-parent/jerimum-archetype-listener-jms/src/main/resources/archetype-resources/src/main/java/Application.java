#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package};

import java.io.Serializable;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import br.com.jerimum.fw.logging.LoggerUtils;import ${package}.config.ApplicationConfig;import ${package}.jms.listener.HelloWorldMessageListener;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Startup spring application class.
 * 
 * @author https://github.com/dalifreire/jerimum
 * @since 10/2015
 */
@Data
@EqualsAndHashCode(callSuper = false)
@EnableAsync
@Configuration
@ComponentScan
@PropertySource({ "${spring.config.location}" })
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Application implements InitializingBean, Serializable {

    private static final long serialVersionUID = 7271156509131604941L;

    @Autowired
    private Environment environment;

    @Autowired
    private ApplicationConfig appConfig;

    @Override
    public void afterPropertiesSet() throws Exception {
        String[] profiles = environment.getActiveProfiles();
        if (profiles.length == 0) {
            LoggerUtils.logInfo(this.getClass(), "Active Spring Profiles: None");
        } else {
            LoggerUtils.logInfo(this.getClass(), "Active Spring Profiles: {}", (Object[]) profiles);
        }
    }

    @Bean
    @Autowired
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(Environment environment) {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setEnvironment(environment);
        return configurer;
    }

    @Bean(name = "jmsConnectionFactory")
    public ConnectionFactory connectionFactory() throws NamingException {

        Context ctx = new InitialContext();
        ConnectionFactory jmsConnectionFactory = (ConnectionFactory) ctx
            .lookup(appConfig.getAppConfigJms().getConnectionFactoryName());
        LoggerUtils.logDebug(this.getClass(), "Looking up jms connection factory reference: '{}' -> '{}'",
            appConfig.getAppConfigJms().getConnectionFactoryName(), jmsConnectionFactory);

        return jmsConnectionFactory;
    }

    @Bean(name = "requestQueue")
    public Queue requestQueue() throws NamingException, JMSException {

        Context ctx = new InitialContext();
        Queue requestQueue = (Queue) ctx.lookup(appConfig.getAppConfigJms().getRequestQueueName());
        LoggerUtils.logDebug(this.getClass(), "Looking up jms request queue: '{}' -> '{}'",
            appConfig.getAppConfigJms().getRequestQueueName(), requestQueue.getQueueName());

        return requestQueue;
    }

    @Bean(name = "responseQueue")
    public Queue responseQueue() throws NamingException, JMSException {

        Context ctx = new InitialContext();
        Queue responseQueue = (Queue) ctx.lookup(appConfig.getAppConfigJms().getResponseQueueName());
        LoggerUtils.logDebug(this.getClass(), "Looking up jms response queue: '{}' -> '{}'",
            appConfig.getAppConfigJms().getResponseQueueName(), responseQueue.getQueueName());

        return responseQueue;
    }

    @Bean(name = "listenerQueue")
    public Queue listenerQueue() throws NamingException, JMSException {

        Context ctx = new InitialContext();
        Queue listenerQueue = (Queue) ctx.lookup(appConfig.getAppConfigJms().getListenerQueueName());
        LoggerUtils.logDebug(this.getClass(), "Looking up jms listener queue: '{}' -> '{}'",
            appConfig.getAppConfigJms().getListenerQueueName(), listenerQueue.getQueueName());

        return listenerQueue;
    }

    @Bean(name = "jmsTimeout")
    public Long jmsTimeout() {
        return Long.valueOf(appConfig.getAppConfigJms().getTimeout());
    }

    @Bean
    @Autowired
    public DefaultMessageListenerContainer helloWorldMessageListenerContainer(ConnectionFactory connectionFactory,
        @Qualifier("listenerQueue") Queue destinationQueue, HelloWorldMessageListener messageListener)
            throws JMSException {

        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setDestinationName(destinationQueue.getQueueName());
        listenerContainer.setMessageListener(messageListener);
        listenerContainer.setConcurrentConsumers(appConfig.getAppConfigJms().getConcurrentConsumers().intValue());
        listenerContainer.setMaxConcurrentConsumers(appConfig.getAppConfigJms().getMaxConcurrentConsumers().intValue());
        return listenerContainer;
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(5);
        pool.setMaxPoolSize(34);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("i18n\\messages");
        return messageSource;
    }

}
