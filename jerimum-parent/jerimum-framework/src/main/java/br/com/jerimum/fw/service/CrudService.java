package br.com.jerimum.fw.service;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.jerimum.fw.entity.AbstractEntity;
import br.com.jerimum.fw.exception.ServiceException;
import br.com.jerimum.fw.exception.ValidationException;

/**
 * 
 * @author Dali Freire Dias - dalifreire@gmail.com
 * @param <DTO>
 * @param <ENTITY>
 */
public interface CrudService<DTO extends Serializable, ENTITY extends AbstractEntity<?>> {

    DTO getDtoById(Long id);

    DTO insertDto(DTO dto) throws ValidationException, ServiceException;

    DTO updateDto(DTO dto) throws ValidationException, ServiceException;

    void deleteDtoById(Long id) throws ValidationException, ServiceException;

    Set<DTO> getAllDtos();
    
    /**
     * Find all pageable 
     * @param {@link Pageable}
     * @return Page<ENTITY>
     */
    Page<DTO> getAllDtos(Pageable pageable);

}
