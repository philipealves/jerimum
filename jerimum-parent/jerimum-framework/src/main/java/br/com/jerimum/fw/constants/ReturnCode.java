package br.com.jerimum.fw.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enumerador contendo os codigos de retorno e suas respectivas mensagens que
 * serao utilizads nos retornos das chamadas feitas aos servicos da aplicacao.
 * 
 * @author Dali Freire - dalifreire@gmail.com
 */
@AllArgsConstructor
public enum ReturnCode {

	SUCCESS(0), FAIL(-1), ACCESS_DENIED(-2), INVALID_PARAMETERS(-3);

	@Getter
	private Integer code;

}
