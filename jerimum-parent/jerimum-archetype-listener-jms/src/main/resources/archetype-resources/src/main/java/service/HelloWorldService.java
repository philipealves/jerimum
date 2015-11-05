#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import br.com.jerimum.fw.exception.ServiceException;

/**
 * Service interface for hello world operations.
 * 
 * @author Dali Freire: dalifreire@gmail.com
 * @since 11/2015
 */
public interface HelloWorldService {

	/**
	 * Post the text message into request queue.
	 * 
	 * @param msg Message to be sent
	 * @throws ServiceException
	 */
	public void propagateReceivedMessage(String msg) throws ServiceException;

	/**
	 * Send a message and get the response (wait until timeout).
	 * 
	 * @param msg Message to be sent
	 * @throws ServiceException
	 */
	public String sendAndReceive(String msg) throws ServiceException;
	
}

