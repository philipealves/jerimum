package br.com.jerimum.fw.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import br.com.jerimum.fw.dao.JpaCrudRepository;
import br.com.jerimum.fw.entity.AbstractEntity;
import br.com.jerimum.fw.exception.ValidationException;
import br.com.jerimum.fw.service.CrudService;

/**
 * 
 * @author Dali Freire Dias - dalifreire@gmail.com
 * @param <DTO>
 * @param <ENTITY>
 */
public abstract class AbstractCrudService<DTO extends Serializable, ENTITY extends AbstractEntity<?>>
    extends AbstractEntityService<ENTITY> implements CrudService<DTO, ENTITY> {

    @Override
    protected abstract JpaCrudRepository<ENTITY, Long> getRepository();

    @Override
    @Transactional(readOnly = true)
    public DTO getDtoById(Long id) {
        ENTITY entity = getEntityById(id);
        if (entity == null) {
            Class<ENTITY> clazz = getEntityClass();
            throwEmptyResultDataAccessException(clazz.getSimpleName(), id);
        }
        return buildDtoFromEntity(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public DTO insertDto(DTO dto) throws ValidationException {
        ENTITY entity = buildEntityFromDto(dto);
        saveEntity(entity);
        return buildDtoFromEntity(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public DTO updateDto(DTO dto) throws ValidationException {
        ENTITY entity = buildEntityFromDto(dto);
        entity = saveEntity(entity);
        return buildDtoFromEntity(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteDtoById(Long id) {
        deleteEntityById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<DTO> getAllDtos() {
        return buildDtoFromEntity(getAllEntities());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<DTO> getAllDtos(Pageable pageable) {
        return buildDtoFromEntity(getAllEntities(pageable), pageable);
    }


    /**
     * takes a DTO object and converts it to an entity.
     * 
     * @param dto the DTO.
     * @return a constructed Boundary.
     */
    protected abstract ENTITY buildEntityFromDto(DTO dto);

    /**
     * takes an entity and converts it to a DTO object.
     * 
     * @param entity the entity.
     * @return a constructed DTO.
     */
    protected abstract DTO buildDtoFromEntity(ENTITY entity);

    /**
     * Converts an entities list into dto list.
     * 
     * @param entities The entity list to be converted.
     * @return {@link List} - The converted entity list.
     */
    protected List<DTO> buildDtoFromEntity(List<ENTITY> entities) {
        List<DTO> dtoList = new ArrayList<DTO>();
        for (ENTITY entity : entities) {
            dtoList.add(buildDtoFromEntity(entity));
        }
        return dtoList;
    }
    
    /**
     * Converts an entities list into dto list.
     * 
     * @param entities The entity list to be converted.
     * @param pageable The pageable used by the query.
     * @return {@link Page} - The converted entity list.
     */
    protected Page<DTO> buildDtoFromEntity(Page<ENTITY> entities, Pageable pageable) {
        List<DTO> dtoList = new ArrayList<DTO>();
        for (ENTITY entity : entities) {
            dtoList.add(buildDtoFromEntity(entity));
        }
        return new PageImpl<DTO>(dtoList, pageable, entities.getTotalElements());
    }

    /**
     * Converts an entities set into dto set.
     * 
     * @param entities The entity list to be converted.
     * @return {@link List} - The converted entity list.
     */
    protected Set<DTO> buildDtoFromEntity(Set<ENTITY> entities) {
        Set<DTO> dtoSet = new HashSet<DTO>();
        for (ENTITY entity : entities) {
            dtoSet.add(buildDtoFromEntity(entity));
        }
        return dtoSet;
    }

    @SuppressWarnings("unchecked")
    private Class<ENTITY> getEntityClass() {
        return (Class<ENTITY>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
}
