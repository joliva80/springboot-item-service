package com.springboot.app.item;

//import java.time.Duration;

//import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
//import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
//import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
//import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class AppConfig {
    
    @Bean("RestClient")
    public RestTemplate registerRestTemplate(){
        return new RestTemplate();
    }

    /*
    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> configDefaultCustomizer(){
        return factory -> factory.configureDefault(id -> {  // for each circuit break identified by "id"
            return new Resilience4JConfigBuilder(id)        // configure the circuite break for the given "id" circuit break
                        .circuitBreakerConfig(CircuitBreakerConfig.custom() // Only for the given config: circuitbreak, then set the config parameters
                                .slidingWindowSize(10)
                                .failureRateThreshold(50)
                                .waitDurationInOpenState(Duration.ofMillis(10000))
                                .build())
                        //.timeLimiterConfig(TimeLimiterConfig.ofDefaults()) // ofDefaults is by defaults, or .custom() to customize.
                        .timeLimiterConfig(TimeLimiterConfig.custom()
                                .timeoutDuration(Duration.ofSeconds(6L))
                                .build())  // timeout to 2 seconds response max time
                        .build();
        });
    }
    */

}
