package com.app.cqrs.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

@Configuration
public class XStreamConfig {

    @Bean
    @Primary
    XStream xStream() {
        // Use XppDriver instead of PureJavaReflectionProvider for better Java 17+ compatibility
        XStream xStream = new XStream(new XppDriver(new NoNameCoder()));
        
        // Allow types by wildcard for your application packages
        xStream.allowTypesByWildcard(new String[] { "com.app.**" });
        
        // For development purposes, allow all types (be more restrictive in production)
        xStream.addPermission(AnyTypePermission.ANY);
        
        // Configure XStream to handle Java 17+ module restrictions
        xStream.setMode(XStream.NO_REFERENCES);
        
        return xStream;
    }

}
