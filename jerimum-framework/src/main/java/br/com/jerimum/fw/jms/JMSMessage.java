package br.com.jerimum.fw.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;

import br.com.jerimum.fw.exception.MessageException;


/**
 * Gateway used for JMS messaging.
 * 
 * @author Dali Freire - dalifreire@gmail.com
 */
public interface JMSMessage {

    /**
     * Returns the default queue that will be used to write messages.
     * 
     * @return {@link Queue}
     */
    Queue getWriteQueue();

    /**
     * Returns the deafult queue that will be used to read messages.
     * 
     * @return {@link Queue}
     */
    Queue getReadQueue();

    /**
     * Returns the {@link ConnectionFactory}.
     * 
     * @return {@link ConnectionFactory}
     */
    ConnectionFactory getConnectionFactory();

    /**
     * Returns the timeout to wait a response.
     * 
     * @return long
     */
    long getTimeout();

    /**
     * Puts a new message into the write queue.
     * 
     * @param messageCreator
     * @return {@link Message}
     * @throws MessageException
     */
    Message sendMessage(JMSMessageCreator<?> messageCreator) throws MessageException;

    /**
     * Puts a new message into the queue.
     * 
     * @param messageCreator
     * @param queue
     * @return {@link Message}
     * @throws MessageException
     */
    Message sendMessage(JMSMessageCreator<?> messageCreator, Queue queue) throws MessageException;

    /**
     * Puts a new message into the write queue and waits a response from the read queue.
     * 
     * @param message >> Mensagem que sera postada na fila de request.
     * @return {@link String} >> Mensagem de resposta.
     * @throws MessageException
     */
    String sendAndReceive(String message) throws MessageException;

    /**
     * Envia a mensagem passada como parametro recuperando a mensagem de resposta.
     * 
     * @param messageCreator >> Mensagem que sera postada na fila de request.
     * @return {@link String} >> Mensagem de resposta.
     * @throws MessageException
     */
    String sendAndReceive(JMSMessageCreator<TextMessage> messageCreator) throws MessageException;

    /**
     * Posta a mensagem passada como parametro (de forma textual) na fila.
     * 
     * @param msg
     * @return {@link TextMessage}
     * @throws TextMessage
     */
    TextMessage sendTextMessage(String msg) throws MessageException;

    /**
     * Posta a mensagem passada como parametro (de forma textual) na fila informando o CorrelationID
     * informado.
     * 
     * @param msg
     * @param correlationID
     * @return {@link TextMessage}
     * @throws MessageException
     */
    TextMessage sendTextMessage(String msg, String correlationID) throws MessageException;

    /**
     * Posta a mensagem passada como parametro (de forma textual) na fila passada como parametro.
     * 
     * @param msg
     * @param queue
     * @return {@link Message}
     * @throws TextMessage
     */
    TextMessage sendTextMessage(String msg, Queue queue) throws MessageException;

    /**
     * Recupera as mensagens da fila de reponse de acordo com o messageSelector.
     * 
     * @param messageSelector
     * @return {@link Message}
     * @throws MessageException
     */
    Message receiveMessage(String messageSelector) throws MessageException;

    /**
     * Recupera as mensagens da fila passada como parametro de acordo com o messageSelector.
     * 
     * @param messageSelector
     * @return {@link Message}
     * @throws MessageException
     */
    Message receiveMessage(String messageSelector, Queue queue) throws MessageException;

}
