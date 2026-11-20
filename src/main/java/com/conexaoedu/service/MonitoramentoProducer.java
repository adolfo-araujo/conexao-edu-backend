package com.conexaoedu.service;

import com.conexaoedu.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MonitoramentoProducer {

    private final RabbitTemplate rabbitTemplate;

    public MonitoramentoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarEvento(String acao) {
        String mensagem = LocalDateTime.now() + " - " + acao;
        rabbitTemplate.convertAndSend(RabbitMQConfig.FILA_MONITORAMENTO, mensagem);
    }
}