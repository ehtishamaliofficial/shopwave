package com.shami.common.context;

import com.shami.common.exception.MissingHeaderException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import java.util.UUID;

@Component
@RequestScope
public class RequestContext {

    private final HttpServletRequest request;

    public RequestContext(HttpServletRequest request) {
        this.request = request;
    }

    public UUID getCurrentUserId() {
        String id = request.getHeader("X-User-Id");
        if (id == null) throw new MissingHeaderException("X-User-Id header missing");
        return UUID.fromString(id);
    }

    public String getCurrentUserEmail() {
        String email = request.getHeader("X-User-Email");
        if (email == null) throw new MissingHeaderException("X-User-Email header missing");
        return email;
    }

    public String getCurrentUserName() {
        return request.getHeader("X-User-Name");
    }

    public boolean isAdmin() {
        String role = request.getHeader("X-User-Role");
        return "ROLE_ADMIN".equalsIgnoreCase(role);
    }
}
