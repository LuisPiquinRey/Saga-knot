package com.luispiquinrey.user.Projection;

import org.axonframework.config.ProcessingGroup;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("user-group")
public class LookupUserProjection {
    
}
