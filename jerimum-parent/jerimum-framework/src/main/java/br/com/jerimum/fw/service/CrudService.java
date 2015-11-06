package br.com.jerimum.fw.service;

import java.util.Set;

import br.com.jerimum.fw.dto.AbstractDto;
import br.com.jerimum.fw.entity.AbstractEntity;

/**
 * 
 * @author Dali Freire Dias - dalifreire@gmail.com
 * @since 11/2015
 *
 * @param <DTO>
 * @param <ENTITY>
 */
public interface CrudService<DTO extends AbstractDto, ENTITY extends AbstractEntity<?>> {

	/**
	 * 
	 * @param id
	 * @return
	 */
    DTO getDtoById(Long id);
    
    /**
     * 
     * @param dto
     * @return
     */
    DTO insertDto(DTO dto);
    
    /**
     * 
     * @param dto
     * @return
     */
    DTO updateDto(DTO dto);
    
    /**
     * 
     * @param id
     */
    void deleteDtoById(Long id);
    
    /**
     * 
     * @return
     */
    Set<DTO> getAllDtos();
    
}
