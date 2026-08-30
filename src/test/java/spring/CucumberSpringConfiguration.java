package spring;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

/**
 * Required marker class — tells cucumber-spring which @Configuration
 * to load. Must live inside a package included in @CucumberOptions'
 * glue path (see WebTestRunner below) so Cucumber discovers it.
 * Deliberately empty otherwise.
 */
@CucumberContextConfiguration
@ContextConfiguration(classes = SpringTestConfig.class)
public class CucumberSpringConfiguration {
}
