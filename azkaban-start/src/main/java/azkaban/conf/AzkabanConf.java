/**
 * @Author wangyl
 * @E-mail wangyl@dsgdata.com
 **/
package azkaban.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author wyl
 * @version V1.0
 * @ClassName: AzkabanConf
 * @Function: TODO
 * @Date: 2019/9/23 0:23
 */

@Configuration
@ComponentScan({ "azkaban.service.**" })
public class AzkabanConf {
    /**
     * @Description 加载RestTemplate
     * @Date 2019/9/23 0:30
     * @Param
     * @return
     * @Author wangyl
     * @Version    V1.0
     */
    @Bean
    public RestTemplate getRestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(2000);
        requestFactory.setReadTimeout(2000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }
}
