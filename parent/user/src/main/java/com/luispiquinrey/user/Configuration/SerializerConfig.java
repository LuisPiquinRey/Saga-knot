package com.luispiquinrey.user.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thoughtworks.xstream.XStream;

@Configuration
public class SerializerConfig {
    @Bean
    XStream xStream(){
        XStream xStream=new XStream();
        xStream.allowTypesByWildcard(new String[]{
            "com.luispiquinrey.**"
        });
        return xStream;
    }
}
