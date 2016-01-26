#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.jerimum.fw.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the HELLO_WORLD database table.
 * 
 * @author https://github.com/dalifreire/jerimum
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, of = { "idHelloWorld" })
@Entity
@Table(name = "HELLO_WORLD")
public class HelloWorld extends AbstractEntity<Long> {

    private static final long serialVersionUID = 3239567327638971693L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HELLO_WORLD", unique = true, nullable = false)
    private Long idHelloWorld;

    @NotNull(message = "msg.error.constraint.notnull.xxx")
    @Size(min = 1, max = 150, message = "msg.error.constraint.maxsize.xxx")
    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Override
    public Long getPK() {
        return idHelloWorld;
    }

    @Override
    public String getLabel() {
        return name;
    }

}
