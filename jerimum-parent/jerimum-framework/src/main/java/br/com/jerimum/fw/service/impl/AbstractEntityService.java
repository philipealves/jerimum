package br.com.jerimum.fw.service.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.EmptyResultDataAccessException;

import com.google.common.collect.Sets;

import br.com.jerimum.fw.dao.JpaCrudRepository;
import br.com.jerimum.fw.entity.AbstractEntity;
import br.com.jerimum.fw.i18n.I18nUtils;
import br.com.jerimum.fw.service.EntityService;

/**
 * 
 * @author Dali Freire Dias - dalifreire@gmail.com
 * @since 11/2015
 *
 * @param <ENTITY>
 */
public abstract class AbstractEntityService<ENTITY extends AbstractEntity<?>> implements EntityService<ENTITY> {

    @Autowired
    private MessageSource messageSource;

    protected abstract JpaCrudRepository<ENTITY, Long> getRepository();

    @Override
    public ENTITY getEntityById(Long id) {
        return getRepository().findOne(id);
    }

    @Override
    public ENTITY saveEntity(ENTITY entity) {
        return getRepository().save(entity);
    }

    @Override
    public void deleteEntityById(Long id) {
        ENTITY entity = getRepository().findOne(id);
        if (entity == null) {
            Class<ENTITY> clazz = getEntityClass();
            throwEmptyResultDataAccessException(clazz.getName(), id);
        }
        getRepository().delete(entity);
    }

    @Override
    public Set<ENTITY> getAllEntities() {
        return Sets.newHashSet(getRepository().findAll());
    }

    @SuppressWarnings("unchecked")
    private Class<ENTITY> getEntityClass() {
        return (Class<ENTITY>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    protected void throwEmptyResultDataAccessException(String entity, Long id) {
        String msg = I18nUtils.getMsg(this.messageSource, "msg.entity.not.found", entity, id);
        throw new EmptyResultDataAccessException(msg, 1);
    }
}
