package com.gate.lck.route;

import com.gate.lck.filter.AuthFilter;
import com.gate.lck.filter.LoggingFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserRouteConfig {

    @Bean
    public RouteLocator userRoute(RouteLocatorBuilder builder, LoggingFilter httpLoginFilter, AuthFilter authFilter) {
        return builder.routes()
                .route("lck-user-service", p -> p
                        .path("/lck-user-service/user/**")
                        .filters(f ->
                                f.filter(
                                        authFilter
                                                .apply(new AuthFilter.Config("lck-user-service", true, true))))
                        .uri("lb://LCK-USER-SERVICE"))
                .route("lck-user-service", p -> p
                        .path("/lck-user-service/all/**").filters(f ->
                                f.filter(
                                        httpLoginFilter
                                                .apply(new LoggingFilter.Config("lck-all-service", true, true))))

                        .uri("lb://LCK-USER-SERVICE"))
                .build();
    }


}
