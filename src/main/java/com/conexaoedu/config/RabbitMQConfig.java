package com.conexaoedu.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String FILA_MONITORAMENTO = "fila.monitoramento";

    @Bean
    public Queue filaMonitoramento() {
        return new Queue(FILA_MONITORAMENTO, true);
    }
}