package br.com.jerimum.fw.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Abstract class for all Data Transfer Objects.
 * 
 * @author Dali Freire Dias - dalifreire@gmail.com
 */
public abstract class AbstractDto implements Serializable {

    private static final long serialVersionUID = 3618027273576204047L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
