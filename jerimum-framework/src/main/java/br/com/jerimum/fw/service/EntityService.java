package br.com.jerimum.fw.service;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.jerimum.fw.entity.AbstractEntity;
import br.com.jerimum.fw.exception.ValidationException;

/**
 * 
 * @author Dali Freire Dias - dalifreire@gmail.com
 * @param <ENTITY>
 */
public interface EntityService<ENTITY extends AbstractEntity<?>> {

    public ENTITY getEntityById(Long id);

    public ENTITY saveEntity(ENTITY entity) throws ValidationException;

    public ENTITY deleteEntityById(Long id);

    public Set<ENTITY> getAllEntities();
    
    /**
     * Find all pageable 
     * @param {@link Pageable}
     * @return Page<ENTITY>
     */
    public Page<ENTITY> getAllEntities(Pageable pageable);

}
