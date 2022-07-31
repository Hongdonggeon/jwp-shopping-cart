package woowacourse;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import woowacourse.auth.config.WebConfig;

public class WebConfigTest {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(WebConfig.class);


    @Test
    void test() {
        // given

        // when
        final String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(applicationContext.getBean(beanDefinitionName));
        }

        // then

    }
}
