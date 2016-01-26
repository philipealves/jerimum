package br.com.jerimum.fw.exception;

import java.util.ArrayList;
import java.util.List;

import br.com.jerimum.fw.constants.ReturnCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Exception class for validation operations.
 * 
 * @author Dali Freire - dalifreire@gmail.com
 */
@EqualsAndHashCode(callSuper = false)
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 3473094129626736298L;

    @Getter
    private final Integer code;
    @Getter
    private final List<String> listaErros = new ArrayList<String>();

    public ValidationException() {
        this.code = ReturnCode.SUCCESS.getCode();
    }

    public ValidationException(String message) {
        super(message);
        this.code = ReturnCode.SUCCESS.getCode();
    }

    public ValidationException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ValidationException(Integer code, String message, List<String> listaErros) {
        super(message);
        this.code = code;
        this.listaErros.addAll(listaErros);
    }

    public ValidationException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ValidationException(Throwable cause) {
        super(cause);
        this.code = ReturnCode.SUCCESS.getCode();
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
        this.code = ReturnCode.SUCCESS.getCode();
    }

    public ValidationException(String message, List<String> listaErros) {
        super(message);
        this.listaErros.addAll(listaErros);
        this.code = ReturnCode.SUCCESS.getCode();
    }

    public ValidationException(Integer code, String message, List<String> listaErros, Throwable cause) {
        super(message, cause);
        this.listaErros.addAll(listaErros);
        this.code = code;
    }

    public ValidationException(String message, Throwable cause, List<String> listaErros) {
        super(message, cause);
        this.listaErros.addAll(listaErros);
        this.code = ReturnCode.SUCCESS.getCode();
    }

}
