package br.com.jerimum.fw.jms;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import org.springframework.jms.core.MessageCreator;

import lombok.Getter;

/**
 * 
 * @author Dali Freire - dalifreire@gmail.com
 * @since 11/2015
 * 
 * @param <T>
 */
public abstract class JMSMessageCreator<T extends Message> implements MessageCreator {

	@Getter
    private T message;
	@Getter
    private Class<T> messageType;

    @SuppressWarnings("unchecked")
    public JMSMessageCreator() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        ParameterizedType type = (ParameterizedType) genericSuperclass;
        this.messageType = ((Class<T>) type.getActualTypeArguments()[0]);
    }

    @SuppressWarnings("unchecked")
    public Message createMessage(Session session) throws JMSException {

        if (this.messageType.equals(TextMessage.class)) {

            this.message = (T) session.createTextMessage();

        } else if (this.messageType.equals(MapMessage.class)) {

            this.message = (T) session.createMapMessage();

        } else if (this.messageType.equals(BytesMessage.class)) {

            this.message = (T) session.createBytesMessage();

        } else if (this.messageType.equals(ObjectMessage.class)) {

            this.message = (T) session.createObjectMessage();

        } else if (this.messageType.equals(StreamMessage.class)) {

            this.message = (T) session.createStreamMessage();

        } else if (this.messageType.equals(Message.class)) {

            this.message = (T) session.createMessage();

        }

        setParams(this.message);
        return this.message;
    }

    public abstract void setParams(T message) throws JMSException;

    public String toString() {
        return getClass().getName() + " - message: \n"
            + (this.message != null ? this.message.toString() : "message not exists yet!");
    }

}
