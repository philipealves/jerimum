package br.com.jerimum.fw.service.impl;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

import br.com.jerimum.fw.constants.ReturnCode;
import br.com.jerimum.fw.dao.JpaCrudRepository;
import br.com.jerimum.fw.entity.AbstractEntity;
import br.com.jerimum.fw.exception.ValidationException;
import br.com.jerimum.fw.i18n.I18nUtils;
import br.com.jerimum.fw.service.EntityService;

/**
 * 
 * @author Dali Freire Dias - dalifreire@gmail.com
 * @param <ENTITY>
 */
public abstract class AbstractEntityService<ENTITY extends AbstractEntity<?>> implements EntityService<ENTITY> {

    @Autowired
    private transient MessageSource messageSource;

    protected abstract JpaCrudRepository<ENTITY, Long> getRepository();

    @Override
    public ENTITY getEntityById(Long id) {
        return getRepository().findOne(id);
    }

    @Override
    @Transactional(readOnly = false)
    public ENTITY saveEntity(ENTITY entity) throws ValidationException {
        try {
            return getRepository().save(entity);
        } catch (ConstraintViolationException e) {
            String msg = I18nUtils.getMsg(this.messageSource, "msg.invalid.parameters");
            throwValidationExceptino(ReturnCode.INVALID_PARAMETERS.getCode(), msg, e);
            return null;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteEntityById(Long id) {
        ENTITY entity = getRepository().findOne(id);
        if (entity == null) {
            Class<ENTITY> clazz = getEntityClass();
            throwEmptyResultDataAccessException(clazz.getName(), id);
        }
        getRepository().delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<ENTITY> getAllEntities() {
        return Sets.newHashSet(getRepository().findAll());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<ENTITY> getAllEntities(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    @SuppressWarnings("unchecked")
    private Class<ENTITY> getEntityClass() {
        return (Class<ENTITY>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    protected void throwEmptyResultDataAccessException(String entity, Long id) {
        String msg = I18nUtils.getMsg(this.messageSource, "msg.entity.not.found", entity, id);
        throw new EmptyResultDataAccessException(msg, 1);
    }

    /**
     * Throws a new {@link ValidationException} based in the {@link ConstraintViolationException}.
     * 
     * @param code
     * @param message
     * @param e
     * @throws ValidationException
     */
    protected void throwValidationExceptino(Integer code, String message, ConstraintViolationException e)
        throws ValidationException {

        List<String> listaErros = new ArrayList<String>();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : violations) {
            String msg = I18nUtils.getMsg(messageSource, constraintViolation.getMessage(),
                constraintViolation.getPropertyPath());
            listaErros.add(msg);
        }
        throw new ValidationException(code, message, listaErros, e);
    }

}
