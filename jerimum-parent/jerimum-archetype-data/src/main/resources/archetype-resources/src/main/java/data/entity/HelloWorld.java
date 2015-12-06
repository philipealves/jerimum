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

import br.com.jerimum.fw.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the HELLO_WORLD database table.
 * 
 * @author https://github.com/dalifreire/jerimum
 * @since 11/2015
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = { "name" })
@Entity
@Table(name = "HELLO_WORLD")
public class HelloWorld extends AbstractEntity<Long> {

	private static final long serialVersionUID = 3239567327638971693L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_HELLO_WORLD", unique = true, nullable = false)
	private Long idHelloWorld;

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
