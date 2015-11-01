package br.com.jerimum.fw.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Exception class for validation operations.
 * 
 * @author Dali Freire - dalifreire@gmail.com
 * @since 10/2015
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ValidationException extends Exception {

    private static final long serialVersionUID = 3473094129626736298L;
    private final Integer code;
    private final List<String> listaErros = new ArrayList<String>();

    public ValidationException() {
        this.code = 0;
    }

    public ValidationException(String message) {
        super(message);
        this.code = 0;
    }
    
    public ValidationException(Integer code, String message) {
        super(message);
        this.code = code;
    }
    
    public ValidationException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ValidationException(Throwable cause) {
        super(cause);
        this.code = 0;
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
        this.code = 0;
    }

    public ValidationException(String message, List<String> listaErros) {
        super(message);
        this.listaErros.addAll(listaErros);
        this.code = 0;
    }

    public ValidationException(String message, Throwable cause, List<String> listaErros) {
        super(message, cause);
        this.listaErros.addAll(listaErros);
        this.code = 0;
    }

}
