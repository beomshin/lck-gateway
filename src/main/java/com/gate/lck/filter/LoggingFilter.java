package com.gate.lck.filter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter() {
        super(Config.class);
    }
    private final Gson gson = new GsonBuilder().create();

    @Override
    public GatewayFilter apply(Config config) {
        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Logging filter baseMessage : {}", config.getBaseMessage());


            if(config.isPreLogger())
                log.info("Logging PRE filter : request id -> {}", request.getId());

            // Custom Post Filter
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if(config.isPostLogger())
                    log.info("Logging POST filter : response code -> {}", response.getStatusCode());
            }));
        }, Ordered.LOWEST_PRECEDENCE);

        return filter;
    }

    @Setter @Getter
    @AllArgsConstructor
    public static class Config{
        // Put the configuration properties...
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }

}
