#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.data.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.jerimum.fw.dao.JpaCrudRepository;
import ${package}.data.entity.HelloWorld;

/**
 * Repository for operations on {@link HelloWorld} entity.
 * @see https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
 * 
 * @author https://github.com/dalifreire/jerimum
 * @since 11/2015
 */
@Repository
public interface HelloWorldRepository extends JpaCrudRepository<HelloWorld, Long> {

    List<HelloWorld> findByNameIgnoreCase(String name);
    
}
