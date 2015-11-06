package br.com.jerimum.fw.service;

import java.util.Set;

import br.com.jerimum.fw.entity.AbstractEntity;

/**
 * 
 * @author Dali Freire Dias - dalifreire@gmail.com
 * @since 11/2015
 *
 * @param <ENTITY>
 */
public interface EntityService<ENTITY extends AbstractEntity<?>> {

	/**
	 * 
	 * @param id
	 * @return
	 */
	public ENTITY getEntityById(Long id);
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	public ENTITY saveEntity(ENTITY entity);

	/**
	 * 
	 * @param id
	 */
	public void deleteEntityById(Long id);

	/**
	 * 
	 * @return
	 */
	public Set<ENTITY> getAllEntities();
	
}
