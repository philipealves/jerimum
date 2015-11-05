#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jerimum.fw.exception.MessageException;
import br.com.jerimum.fw.exception.ServiceException;
import ${package}.jms.HelloWorldMessage;
import ${package}.service.HelloWorldService;

/**
 * Service class for hello world operations.
 * 
 * @author Dali Freire: dalifreire@gmail.com
 * @since 11/2015
 */
@Service
public class HelloWorldServiceImpl implements HelloWorldService {

	@Autowired
	private HelloWorldMessage helloWorldMessage;

	@Override
	public void propagateReceivedMessage(String msg) throws ServiceException {

		try {
			
			helloWorldMessage.sendTextMessage(msg);
			
		} catch (MessageException e) {
			throw new ServiceException("Unable to send message!", e);
		}
	}

	@Override
	public String sendAndReceive(String msg) throws ServiceException {

		try {
			
			return helloWorldMessage.sendAndReceive(msg);
			
		} catch (MessageException e) {
			throw new ServiceException("Unable to send/receive message!", e);
		}
	}

}