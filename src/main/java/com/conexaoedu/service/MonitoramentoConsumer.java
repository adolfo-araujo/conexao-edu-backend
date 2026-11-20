package com.conexaoedu.service;

import com.conexaoedu.config.RabbitMQConfig;
import com.conexaoedu.controller.MonitoramentoController;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MonitoramentoConsumer {

    @RabbitListener(queues = RabbitMQConfig.FILA_MONITORAMENTO)
    public void receberEvento(String mensagem) {
        MonitoramentoController.adicionarEvento(mensagem);
        System.out.println("Evento recebido: " + mensagem);
    }
}