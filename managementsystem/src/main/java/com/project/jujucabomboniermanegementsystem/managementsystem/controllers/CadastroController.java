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

    // instancia das classes model do projeto
    private final PeopleRepository peopleRepository; // << Necessario para consultar a People no banco de dados
    private final SellerRepository sellerRepository; // << Necessario para consultar o Seller no banco de dados

    // contrutor das models
    public CadastroController(PeopleRepository peopleRepository, SellerRepository sellerRepository) { // << Construtor necessario para injeção das dependencias
        this.peopleRepository = peopleRepository;
        this.sellerRepository = sellerRepository;
    }


    @GetMapping("/cadastrar-funcionario") // << EndPoint da pagina de cadastro de funcionario
    public String getCadastro(Model model) {
        if (!model.containsAttribute("people")) { // << Verifica se ja existe um objeto com People criado (caso de erro, volte pre preenchido os campos)
            model.addAttribute("people", new PeopleModel()); // << Cria o objeto vazio de People
        }
        return "cadastrar-funcionario";// << Renderiza a pagina
    }

    @PostMapping("/cadastrar-funcionario") // << EndPoint post da pagina de cadastro
    public String postCadastro(@ModelAttribute("people") PeopleModel people, BindingResult br, RedirectAttributes ra) {

        if (br.hasErrors()) {
            ra.addFlashAttribute("people", people); // << Mantém os valores digitados
            ra.addFlashAttribute("org.springframework.validation.BindingResult.people", br); // << Chave exigida pelo spring

            ra.addFlashAttribute("erro", "Preencha todos os campos corretamente."); // << Retorna com FlashAttribute o erro para o pop-up
            return "redirect:/cadastrar-funcionario"; // << Redireciona para a pagina de cadastro novamente
        }

        if (peopleRepository.existsByCpf(people.getCpf())) {
            ra.addFlashAttribute("people", people); // << Mantém os valores digitados
            ra.addFlashAttribute("org.springframework.validation.BindingResult.people", br); // << Chave exigida pelo spring

            ra.addFlashAttribute("erro", "Já existe um cadastro com esse CPF. Operação cancelada."); // << Retorna com FlashAttribute o erro para o pop-up
            return "redirect:/cadastrar-funcionario"; // << Redireciona para a pagina de cadastro novamente
        }

        var emailExistente = peopleRepository.findByEmail(people.getEmail());
        if (emailExistente.isPresent()) {
            ra.addFlashAttribute("people", people); // << Mantém os valores digitados
            ra.addFlashAttribute("org.springframework.validation.BindingResult.people", br); // << Chave exigida pelo spring

            ra.addFlashAttribute("erro", "E-mail já utilizado por outro cadastro. Operação cancelada.");// << Retorna com FlashAttribute o erro para o pop-up
            return "redirect:/cadastrar-funcionario"; // << Redireciona para a pagina de cadastro novamente
        }

        PeopleModel pessoaSalva = peopleRepository.save(people); // << Insere ou atualiza os dados da pessoa no banco de dados (caso de inseção)

        SellerModel seller = new SellerModel(); // << Cria o objeto de seller para criar a relação com pess oa

        seller.setCpf(pessoaSalva); // << Endereça o cpf da pessoa a tabela de seller
        seller.setMetaDeVendas("0"); // << seta a coluna de meta como 0
        seller.setHorasTrabalhadas("0"); // <<  seta a coluna de horas como 0
        sellerRepository.save(seller); // <<  Insere ou atualiza os dados no banco de dados (caso de inserção)

        ra.addFlashAttribute("sucesso", "Funcionário e vendedor cadastrados com sucesso!"); // << Cria o FlashAttribute  para a mensagem de sucesso
        return "redirect:/cadastrar-funcionario"; // << redireciona para a pagina de cadastro novamente
    }

}
