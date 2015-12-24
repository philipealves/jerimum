package br.com.jerimum.fw.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;

import br.com.jerimum.fw.exception.MessageException;


/**
 * Gateway utilizado para a comunicacao com as filas de mensagens via JMS.
 * 
 * @author Dali Freire - dalifreire@gmail.com
 * @since 11/2015
 */
public interface JMSMessage {

    /**
     * Retorna a fila que sera utilizada para escrita.
     * 
     * @return {@link Queue}
     */
    Queue getWriteQueue();

    /**
     * Retorna a fila que sera utilizada para leitura.
     * 
     * @return {@link Queue}
     */
    Queue getReadQueue();

    /**
     * Retorna o {@link ConnectionFactory}.
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
     * Posta na fila de request a mensagem passada como parametro retornando a mensagem postada.
     * 
     * @param messageCreator
     * @return {@link Message}
     * @throws MessageException
     */
    Message sendMessage(JMSMessageCreator<?> messageCreator) throws MessageException;

    /**
     * Posta na fila a mensagem passada como parametro retornando a mensagem postada.
     * 
     * @param messageCreator
     * @param queue
     * @return {@link Message}
     * @throws MessageException
     */
    Message sendMessage(JMSMessageCreator<?> messageCreator, Queue queue) throws MessageException;

    /**
     * Envia a mensagem passada como parametro recuperando a mensagem de resposta.
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
