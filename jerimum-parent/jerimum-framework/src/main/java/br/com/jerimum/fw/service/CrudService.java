package br.com.jerimum.fw.service;

import java.io.Serializable;
import java.util.Set;

import br.com.jerimum.fw.entity.AbstractEntity;
import br.com.jerimum.fw.exception.ValidationException;

/**
 * 
 * @author Dali Freire Dias - dalifreire@gmail.com
 * @since 11/2015
 *
 * @param <DTO>
 * @param <ENTITY>
 */
public interface CrudService<DTO extends Serializable, ENTITY extends AbstractEntity<?>> {

	DTO getDtoById(Long id);

	DTO insertDto(DTO dto) throws ValidationException;

	DTO updateDto(DTO dto) throws ValidationException;

	void deleteDtoById(Long id);

	Set<DTO> getAllDtos();

}
