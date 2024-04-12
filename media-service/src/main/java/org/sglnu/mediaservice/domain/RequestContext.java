package org.sglnu.mediaservice.domain;

import org.sglnu.mediaservice.validator.annotation.IpV4;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Getter
@Component
@RequestScope
public class RequestContext {

    @IpV4
    private final String Ip;

    public RequestContext() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        this.Ip = attrs.getRequest().getRemoteAddr();
    }
}
