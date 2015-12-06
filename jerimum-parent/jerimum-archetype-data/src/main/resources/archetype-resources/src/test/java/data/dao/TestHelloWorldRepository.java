#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.data.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ${package}.data.RepositoryTestContext;
import ${package}.data.entity.HelloWorld;

/**
 * JUnit test class for HelloWorldRepository.
 * 
 * @author https://github.com/dalifreire/jerimum
 * @since 12/2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Rollback
@ContextConfiguration(classes = { RepositoryTestContext.class })
public class TestHelloWorldRepository extends RepositoryTestContext {

    @Autowired
    private HelloWorldRepository helloWorldRepository;

    /**
     * Test the CRUD functionality, relevant for all entities.
     */
    @Test
    public void testCrud() {

        HelloWorld helloWorld = new HelloWorld();
        helloWorld.setName("name");

        /* create the entity and check that auto generated fields are null */
        assertNotNull(helloWorld);
        assertNull(helloWorld.getPK());

        /* insert the entity and check that auto generated fields are'nt null */
        helloWorld = helloWorldRepository.save(helloWorld);
        assertNotNull(helloWorld);
        assertNotNull(helloWorld.getPK());

        /*
         * check that it's in the record is the same as expected (equals needs to be implemented in
         * entity class)
         */
        HelloWorld testHelloWorld = helloWorldRepository.findOne(helloWorld.getPK());
        assertEquals(testHelloWorld, helloWorld);

        /* change the entity and see the it's updated */
        helloWorld.setName("updated name");
        helloWorld = helloWorldRepository.save(helloWorld);
        testHelloWorld = helloWorldRepository.findOne(helloWorld.getPK());
        assertEquals(testHelloWorld, helloWorld);

        /* delete the entity and check that's its deleted */
        helloWorldRepository.delete(helloWorld);
        helloWorld = helloWorldRepository.findOne(helloWorld.getPK());
        assertNull(helloWorld);

    }

}
