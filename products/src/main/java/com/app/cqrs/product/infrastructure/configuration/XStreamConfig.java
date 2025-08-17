package com.app.cqrs.product.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;

@Configuration
public class XStreamConfig {

    @Bean
    XStream xStream() {
        XStream xStream = new XStream(new PureJavaReflectionProvider());
        xStream.allowTypesByWildcard(new String[] { "com.app.**" });
        return xStream;
    }

}
