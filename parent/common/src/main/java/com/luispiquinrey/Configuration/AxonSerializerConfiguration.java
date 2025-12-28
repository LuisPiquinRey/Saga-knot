package com.luispiquinrey.Configuration;

import com.thoughtworks.xstream.XStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AxonSerializerConfiguration {
    @Bean
    @Primary
    XStream xStream() {
        XStream xStream = new XStream();
        xStream.allowTypesByWildcard(new String[]{
                "com.luispiquinrey.**"
        });
        return xStream;
    }
}