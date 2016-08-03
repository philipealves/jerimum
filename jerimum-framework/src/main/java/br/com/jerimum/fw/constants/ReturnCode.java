package br.com.jerimum.fw.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enumerator that contains the return codes used in the application services.
 * 
 * @author Dali Freire - dalifreire@gmail.com
 */
@AllArgsConstructor
public enum ReturnCode {

    SUCCESS(0), FAIL(-1), ACCESS_DENIED(-2), INVALID_PARAMETERS(-3);

    @Getter
    private Integer code;

}
