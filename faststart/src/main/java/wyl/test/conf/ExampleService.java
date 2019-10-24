package wyl.test.conf;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

@Component
public class ExampleService {

    private String prefix="yes ";
    private String suffix=" no";

    public ExampleService(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public ExampleService()
    {

    }

    public String wrap(String word) {
        return prefix + word + suffix;
    }
}
