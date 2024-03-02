package io.github.thinkframework.server.config;

import io.github.thinkframework.Jerry;
import io.github.thinkframework.server.JerryServletWebServerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;

/**
 * @see org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryConfiguration
 */
@Configuration(proxyBeanMethods = false)
public class JerryServletWebServerFactoryConfiguration {

    /**
     * 第一优先级,以替换tomcat
     * @see org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryConfiguration.EmbeddedTomcat
     */
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass({ Servlet.class, Jerry.class })
//    @ConditionalOnMissingBean(value = ServletWebServerFactory.class, search = SearchStrategy.CURRENT)
    static class EmbeddedJerry {

        @Bean
        public JerryServletWebServerFactory jerryServletWebServerFactory(JerryWebServerProperteis properteis) {
            return new JerryServletWebServerFactory(properteis);
        }

    }

}
