package br.com.jerimum.fw.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Abstract class for all entities. All entities must be extends this class.
 * 
 * @author Dali Freire Dias - dalifreire@gmail.com
 * @since 11/2015
 * 
 * @param <T> Primary key type.
 */
public abstract class AbstractEntity<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = -18840108735675636L;

    /**
     * @return PK - Returns the primary key value.
     */
    public abstract T getPK();

    /**
     * @return {@link String} - Returns the text ({@link String}) label for this entity.
     */
    public abstract String getLabel();

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
