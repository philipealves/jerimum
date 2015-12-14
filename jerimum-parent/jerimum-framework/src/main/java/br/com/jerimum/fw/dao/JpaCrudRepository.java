package br.com.jerimum.fw.dao;

import java.io.Serializable;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.jerimum.fw.entity.AbstractEntity;

/**
 * Interface for generic CRUD operations.
 * 
 * @author Dali Freire Dias - dalifreire@gmail.com
 * @since 09/2015
 * 
 * @param <E> Related Entity.
 * @param <PK> Primary key type for related Entity.
 */
@NoRepositoryBean
public interface JpaCrudRepository<E extends AbstractEntity<?>, PK extends Serializable> extends PagingAndSortingRepository<E, PK> {

}
