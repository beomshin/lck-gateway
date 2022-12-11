package com.gate.lck.route;

import com.gate.lck.auth.JwtTokenProvider;
import com.gate.lck.filter.JwtTokenFilter;
import com.gate.lck.filter.LoggingFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserRouteConfig {

    @Bean
    public RouteLocator userRoute(RouteLocatorBuilder builder, LoggingFilter httpLoginFilter, JwtTokenFilter jwtTokenFilter) {
        return builder.routes()
                .route("lck-user-service", p -> p
                        .path("/lck-user-service/user/**")
                        .filters(f ->
                                f.filter(
                                        jwtTokenFilter
                                                .apply(new JwtTokenFilter.Config("lck-user-service", true, true))))
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
