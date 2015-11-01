package br.com.jerimum.fw.exception;

import java.util.Arrays;
import java.util.Date;

import lombok.Getter;

/**
 * 
 * @author Dali Freire - dalifreire@gmail.com
 * @since 10/2015
 */
public class JerimumException extends RuntimeException {

    private static final long serialVersionUID = -3443905113557722597L;
    @Getter
    private final Date timeStamp;
    @Getter
    private final String occurrenceId;
    @Getter
    private final String methodName;
    @Getter
    private transient final Object current;
    @Getter
    private transient final Object[] args;

    public JerimumException(String message, Throwable cause, Date timeStamp, String occurrenceId, String methodName,
        Object current, Object[] newArgs) {
        super(message, cause);
        this.timeStamp = timeStamp;
        this.occurrenceId = occurrenceId;
        this.methodName = methodName;
        this.current = current;
        this.args = newArgs != null ? Arrays.copyOf(newArgs, newArgs.length) : null;
    }

}
