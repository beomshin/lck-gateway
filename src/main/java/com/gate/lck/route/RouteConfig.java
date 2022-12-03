package com.gate.lck.route;

import com.gate.lck.filter.LoggingFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator userRoute(RouteLocatorBuilder builder, LoggingFilter httpLoginFilter) {
        return builder.routes()
                .route("lck-user-service", p -> p
                        .path("/lck-user-service/**")
                        .filters(f ->
                                f.filter(
                                        httpLoginFilter
                                                .apply(new LoggingFilter.Config("lck-user-serviceLoggingFilter", true, true))))
                        .uri("lb://LCK-USER-SERVICE"))
                .build();
    }


    @Bean
    public RouteLocator trialRoute(RouteLocatorBuilder builder, LoggingFilter httpLoginFilter) {
        return builder.routes()
                .route("lck-trial-service", p -> p.path("/lck-trial-service/**")
                        .filters(f ->
                                f.filter(
                                        httpLoginFilter
                                                .apply(new LoggingFilter.Config("lck-trial-service LoggingFilter", true, true))))
                        .uri("lb://LCK-TRIAL-SERVICE"))
                .build();
    }

    @Bean
    public RouteLocator authRoute(RouteLocatorBuilder builder, LoggingFilter httpLoginFilter) {
        return builder.routes()
                .route("lck-auth-service", p -> p.path("/lck-auth-service/**")
                        .filters(f ->
                                f.filter(
                                        httpLoginFilter
                                                .apply(new LoggingFilter.Config("lck-trial-service LoggingFilter", true, true))))
                        .uri("lb://LCK-AUTH-SERVICE"))
                .build();
    }
}
