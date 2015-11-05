package br.com.jerimum.fw.exception;

/**
 * Exception class for jms messaging operations.
 * 
 * @author Dali Freire - dalifreire@gmail.com
 * @since 10/2015
 */
public class MessageException extends Exception {

    private static final long serialVersionUID = 3473094129626736298L;

    public MessageException() {

    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

}
