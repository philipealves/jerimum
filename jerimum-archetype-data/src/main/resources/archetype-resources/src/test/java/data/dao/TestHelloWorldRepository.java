#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.data.dao;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import ${package}.RepositoryTestContext;
import ${package}.data.entity.HelloWorld;

/**
 * JUnit test class for HelloWorldRepository.
 * 
 * @author https://github.com/dalifreire/jerimum
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Rollback
@ContextConfiguration(classes = { RepositoryTestContext.class })
public class TestHelloWorldRepository extends RepositoryTestContext {

    @Autowired
    private HelloWorldRepository helloWorldRepository;
    
    @Autowired
    private LocalValidatorFactoryBean validatorFactoryBean;
    private static Validator validator;

    @Before
    public void setUp() {
        validator = validatorFactoryBean.getValidator();
    }

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
    
    @Test
    public void testValidateFields() {

        HelloWorld helloWorld = new HelloWorld();
        helloWorld.setName("name helloWorld");
        
        /*
         * all fields are correctly filled
         */
        Set<ConstraintViolation<HelloWorld>> constraintViolations = validator.validate(helloWorld);
        Assert.assertEquals(0, constraintViolations.size());

        /*
         * The name field is mandatory
         */
        helloWorld.setName(null);
        constraintViolations = validator.validate(helloWorld);
        Assert.assertEquals(1, constraintViolations.size());
        Assert.assertEquals("name", constraintViolations.iterator().next().getPropertyPath().toString());

        /*
         * The minimum size for the field name has been exceeded
         */
        helloWorld.setName(createStringWithLength(151));
        constraintViolations = validator.validate(helloWorld);
        Assert.assertEquals(1, constraintViolations.size());
        Assert.assertEquals("name", constraintViolations.iterator().next().getPropertyPath().toString());
        helloWorld.setName("name helloWorld");
        
    }

}
