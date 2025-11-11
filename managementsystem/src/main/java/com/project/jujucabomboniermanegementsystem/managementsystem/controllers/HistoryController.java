package com.project.jujucabomboniermanegementsystem.managementsystem.controllers;

import com.project.jujucabomboniermanegementsystem.managementsystem.models.HistoryModel;
import com.project.jujucabomboniermanegementsystem.managementsystem.repository.HistoryRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class HistoryController {

    private final HistoryRepository historyRepository;

    public HistoryController(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @GetMapping("/historico")
    public String listarHistorico(
            @RequestParam(value = "data", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            Model model) {

        List<HistoryModel> historicos;

        if (data != null) {
            historicos = historyRepository.findByDate(data);
            model.addAttribute("dataSelecionada", data);
        } else {
            historicos = historyRepository.findAll();
            model.addAttribute("dataSelecionada", null);
        }

        model.addAttribute("historicos", historicos);
        return "historico"; 
    }
}