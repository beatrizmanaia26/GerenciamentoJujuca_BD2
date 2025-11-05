package com.project.jujucabomboniermanegementsystem.managementsystem.controllers;

import com.project.jujucabomboniermanegementsystem.managementsystem.models.SellerModel;
import com.project.jujucabomboniermanegementsystem.managementsystem.models.PeopleModel;
import com.project.jujucabomboniermanegementsystem.managementsystem.repository.SellerRepository;
import com.project.jujucabomboniermanegementsystem.managementsystem.repository.PeopleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
public class GerenciarFuncionarioController {

    private final SellerRepository sellerRepository;
    private final PeopleRepository peopleRepository;

    public GerenciarFuncionarioController(SellerRepository sellerRepository, PeopleRepository peopleRepository) {
        this.sellerRepository = sellerRepository;
        this.peopleRepository = peopleRepository;
    }

    // LISTAR FUNCIONÁRIOS
    @GetMapping("/gerenciar-funcionarios")
    public String listarFuncionarios(Model model) {
        List<SellerModel> funcionarios = sellerRepository.findAll();
        model.addAttribute("funcionarios", funcionarios);
        return "gerenciar-funcionarios";
    }

    // EDITAR FUNCIONÁRIO
    @GetMapping("/gerenciar-funcionarios/editar/{id}")
    public String editarFuncionario(@PathVariable("id") UUID id, Model model, RedirectAttributes ra) {
        var sellerOpt = sellerRepository.findById(id);
        if (sellerOpt.isEmpty()) {
            ra.addFlashAttribute("erro", "Funcionário não encontrado.");
            return "redirect:/gerenciar-funcionarios";
        }

        SellerModel funcionario = sellerOpt.get();
        PeopleModel pessoa = funcionario.getCpf();

        model.addAttribute("funcionario", funcionario);
        model.addAttribute("pessoa", pessoa);
        return "editar-funcionario";
    }

    // SALVAR ALTERAÇÕES
    @PostMapping("/gerenciar-funcionarios/salvar")
    public String salvarFuncionario(@ModelAttribute("pessoa") PeopleModel pessoa,
                                    @RequestParam("id") UUID funcionarioId,
                                    RedirectAttributes ra) {
        try {
            // Atualiza dados da pessoa vinculada
            peopleRepository.save(pessoa);

            // Atualiza vínculo no Seller
            var funcionario = sellerRepository.findById(funcionarioId).orElse(null);
            if (funcionario != null) {
                funcionario.setCpf(pessoa);
                sellerRepository.save(funcionario);
            }

            ra.addFlashAttribute("sucesso", "Funcionário atualizado com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro ao salvar funcionário: " + e.getMessage());
        }

        return "redirect:/gerenciar-funcionarios";
    }

    // EXCLUIR FUNCIONÁRIO
    @GetMapping("/gerenciar-funcionarios/excluir/{id}")
    public String excluirFuncionario(@PathVariable("id") UUID id, RedirectAttributes ra) {
        try {
            var funcionario = sellerRepository.findById(id).orElse(null);
            if (funcionario != null) {
                sellerRepository.deleteById(id);
                ra.addFlashAttribute("sucesso", "Funcionário removido com sucesso!");
            } else {
                ra.addFlashAttribute("erro", "Funcionário não encontrado.");
            }
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro ao excluir funcionário: " + e.getMessage());
        }

        return "redirect:/gerenciar-funcionarios";
    }
}
