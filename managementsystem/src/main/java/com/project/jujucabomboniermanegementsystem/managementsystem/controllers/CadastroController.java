package com.project.jujucabomboniermanegementsystem.managementsystem.controllers;

import com.project.jujucabomboniermanegementsystem.managementsystem.models.PeopleModel;
import com.project.jujucabomboniermanegementsystem.managementsystem.models.SellerModel;
import com.project.jujucabomboniermanegementsystem.managementsystem.repository.PeopleRepository;
import com.project.jujucabomboniermanegementsystem.managementsystem.repository.SellerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CadastroController {

    private final PeopleRepository peopleRepository;
    private final SellerRepository sellerRepository;

    public CadastroController(PeopleRepository peopleRepository, SellerRepository sellerRepository) {
        this.peopleRepository = peopleRepository;
        this.sellerRepository = sellerRepository;
    }

    @GetMapping("/cadastrar-funcionario")
    public String getCadastro(Model model) {
        if (!model.containsAttribute("people")) {
            model.addAttribute("people", new PeopleModel());
        }
        return "cadastrar-funcionario";
    }

    @PostMapping("/cadastrar-funcionario")
    public String postCadastro(@ModelAttribute("people") PeopleModel people, BindingResult br, RedirectAttributes ra) {

        if (br.hasErrors()) {
            ra.addFlashAttribute("erro", "Preencha todos os campos corretamente.");
            return "redirect:/cadastrar-funcionario";
        }

        if (peopleRepository.existsByCpf(people.getCpf())) {
            ra.addFlashAttribute("erro", "Já existe um cadastro com esse CPF. Operação cancelada.");
            return "redirect:/cadastrar-funcionario";
        }

        var emailExistente = peopleRepository.findByEmail(people.getEmail());
        if (emailExistente.isPresent()) {
            ra.addFlashAttribute("erro", "E-mail já utilizado por outro cadastro. Operação cancelada.");
            return "redirect:/cadastrar-funcionario";
        }

        PeopleModel pessoaSalva = peopleRepository.save(people);

        SellerModel seller = new SellerModel();
        seller.setCpf(pessoaSalva);
        seller.setMetaDeVendas("0");
        seller.setHorasTrabalhadas("0");
        sellerRepository.save(seller);

        ra.addFlashAttribute("sucesso", "Funcionário e vendedor cadastrados com sucesso!");
        return "redirect:/cadastrar-funcionario";
    }

}
