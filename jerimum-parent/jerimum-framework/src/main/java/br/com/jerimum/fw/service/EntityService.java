package br.com.jerimum.fw.service;

import java.util.Set;

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

    public void deleteEntityById(Long id);

    public Set<ENTITY> getAllEntities();

}
