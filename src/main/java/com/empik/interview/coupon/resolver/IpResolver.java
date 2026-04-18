package com.empik.interview.coupon.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Dominik Zieliński
 * Date: 18/04/2026
 * Time: 08:05
 */
@Component
public class IpResolver {

    @Value("${app.debug.ip.country.resolver.enable}")
    private boolean ipCountryResolverDebugEnabled;

    public String resolve(HttpServletRequest request) {
        if (ipCountryResolverDebugEnabled) {
            String testIp = request.getHeader("X-Test-IP");
            if (testIp != null && !testIp.isBlank()) {
                return testIp;
            }
        }

        return request.getRemoteAddr();
    }
}
