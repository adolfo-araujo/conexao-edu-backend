package com.conexaoedu.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/monitor")
@CrossOrigin(origins = "*")
public class MonitoramentoController {

    private static final List<String> eventos = new ArrayList<>();

    public static void adicionarEvento(String evento) {
        eventos.add(0, evento);

        if (eventos.size() > 20) {
            eventos.remove(eventos.size() - 1);
        }
    }

    @GetMapping("/eventos")
    public List<String> listarEventos() {
        return new ArrayList<>(eventos);
    }
}