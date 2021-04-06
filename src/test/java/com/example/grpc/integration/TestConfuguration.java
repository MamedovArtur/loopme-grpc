package com.example.grpc.integration;

import com.example.grpc.grpc.CapitalizeServiceImpl;
import com.example.grpc.grpc.global.ExceptionHandler;
import net.devh.boot.grpc.client.autoconfigure.GrpcClientAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcAdviceAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ImportAutoConfiguration({
        GrpcServerAutoConfiguration.class,
        GrpcServerFactoryAutoConfiguration.class,
        GrpcAdviceAutoConfiguration.class,
        GrpcClientAutoConfiguration.class})
public class TestConfuguration {
    @Bean
    CapitalizeServiceImpl capitalizeService(){
        return new CapitalizeServiceImpl();
    }
    @Bean
    ExceptionHandler exceptionHandler(){
        return new ExceptionHandler();
    }
}
