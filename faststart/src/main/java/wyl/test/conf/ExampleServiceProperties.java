package wyl.test.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("example.service")
@Data
public class ExampleServiceProperties {
    private String prefix;
    private String suffix;
}