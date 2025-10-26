package com.project.jujucabomboniermanegementsystem.managementsystem.controllers;

import com.project.jujucabomboniermanegementsystem.managementsystem.dtos.FuncionarioForm;
import com.project.jujucabomboniermanegementsystem.managementsystem.models.ClientModel;
import com.project.jujucabomboniermanegementsystem.managementsystem.models.PeopleModel;
import com.project.jujucabomboniermanegementsystem.managementsystem.models.SellerModel;
import com.project.jujucabomboniermanegementsystem.managementsystem.repository.ClientRepository;
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
    private final ClientRepository clientRepository;
    // contrutor das models
    public CadastroController(PeopleRepository peopleRepository, SellerRepository sellerRepository, ClientRepository clientRepository) { // << Construtor necessario para injeção das dependencias
        this.peopleRepository = peopleRepository;
        this.sellerRepository = sellerRepository;
        this.clientRepository = clientRepository;
    }


    @GetMapping("/cadastrar-funcionario")
    public String getCadastro(Model model) {
        if (!model.containsAttribute("funcionario")) {
            model.addAttribute("funcionario", new FuncionarioForm());
        }
        return "cadastrar-funcionario";
    }

    @PostMapping("/cadastrar-funcionario")
    public String postCadastro(@ModelAttribute("funcionario") FuncionarioForm form,
                               BindingResult br,
                               RedirectAttributes ra){
        if (form.getCpf() == null || form.getCpf().isBlank()
                || form.getNome() == null || form.getNome().isBlank()
                || form.getEmail() == null || form.getEmail().isBlank()
                || form.getPassword() == null || form.getPassword().isBlank()) {

            ra.addFlashAttribute("funcionario", form);
            ra.addFlashAttribute("org.springframework.validation.BindingResult.funcionario", br);
            ra.addFlashAttribute("erro", "Preencha todos os campos obrigatórios.");
            return "redirect:/cadastrar-funcionario";
        }
        // CPF único
        if (peopleRepository.existsByCpf(form.getCpf())) {
            ra.addFlashAttribute("funcionario", form);
            ra.addFlashAttribute("org.springframework.validation.BindingResult.funcionario", br);
            ra.addFlashAttribute("erro", "Já existe um cadastro com esse CPF.");
            return "redirect:/cadastrar-funcionario";
        }

        // E-mail único
        var emailExistente = peopleRepository.findByEmail(form.getEmail());
        if (emailExistente.isPresent()) {
            ra.addFlashAttribute("funcionario", form);
            ra.addFlashAttribute("org.springframework.validation.BindingResult.funcionario", br);
            ra.addFlashAttribute("erro", "E-mail já utilizado por outro cadastro.");
            return "redirect:/cadastrar-funcionario";
        }

        PeopleModel people = new PeopleModel();
        people.setCpf(form.getCpf());
        people.setNome(form.getNome());
        people.setEmail(form.getEmail());
        people.setCep(form.getCep());
        people.setComplemento(form.getComplemento());
        people.setEndereco(form.getEndereco());
        people.setTelefone(form.getTelefone());
        people.setNumero(form.getNumero());
        PeopleModel pessoaSalva = peopleRepository.save(people);

        SellerModel seller = new SellerModel();
        seller.setCpf(pessoaSalva);
        seller.setEmail(pessoaSalva.getEmail());
        seller.setPassword(form.getPassword());
        sellerRepository.save(seller);

        ClientModel client = new ClientModel();
        client.setCpf(pessoaSalva);
        client.setTelefone(pessoaSalva.getTelefone());
        clientRepository.save(client);

        ra.addFlashAttribute("sucesso", "Funcionário cadastrado com sucesso!"); // << Cria o FlashAttribute  para a mensagem de sucesso
        return "redirect:/login-funcionario"; // << redireciona para a pagina de cadastro novamente
    }

}
