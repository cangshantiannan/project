package wyl.test.conf;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(ExampleService.class)
@EnableConfigurationProperties(ExampleServiceProperties.class)
@ConditionalOnProperty(prefix = "example.service", value = "enabled", havingValue = "true")
@ComponentScan({ "wyl.test.conf.**" })
public class ExampleAutoConfigure {

}