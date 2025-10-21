package com.project.jujucabomboniermanegementsystem.managementsystem.controllers;

import com.project.jujucabomboniermanegementsystem.managementsystem.dtos.ClienteForm;
import com.project.jujucabomboniermanegementsystem.managementsystem.dtos.FuncionarioForm;
import com.project.jujucabomboniermanegementsystem.managementsystem.models.ClientModel;
import com.project.jujucabomboniermanegementsystem.managementsystem.models.PeopleModel;
import com.project.jujucabomboniermanegementsystem.managementsystem.repository.ClientRepository;
import com.project.jujucabomboniermanegementsystem.managementsystem.repository.PeopleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CadastroClienteController {

    // instancia das classes model do projeto
    private final PeopleRepository peopleRepository; // << Necessario para consultar a People no banco de dados
    private final ClientRepository clientRepository;
    // contrutor das models
    public CadastroClienteController(PeopleRepository peopleRepository, ClientRepository clientRepository) { // << Construtor necessario para injeção das dependencias
        this.peopleRepository = peopleRepository;
        this.clientRepository = clientRepository;
    }


    @GetMapping("/cadastrar-cliente")
    public String getCadastro(Model model) {
        if (!model.containsAttribute("cliente")) {
            model.addAttribute("cliente", new ClienteForm());
        }
        return "cadastrar-cliente";
    }

    @PostMapping("/cadastrar-cliente")
    public String postCadastro(@ModelAttribute("cliente") FuncionarioForm form,
                               BindingResult br,
                               RedirectAttributes ra){
        if (form.getCpf() == null || form.getCpf().isBlank()
                || form.getNome() == null || form.getNome().isBlank()
                || form.getEmail() == null || form.getEmail().isBlank()) {

            ra.addFlashAttribute("cliente", form);
            ra.addFlashAttribute("org.springframework.validation.BindingResult.cliente", br);
            ra.addFlashAttribute("erro", "Preencha todos os campos obrigatórios.");
            return "redirect:/cadastrar-cliente";
        }
        // CPF único
        if (peopleRepository.existsByCpf(form.getCpf())) {
            ra.addFlashAttribute("cliente", form);
            ra.addFlashAttribute("org.springframework.validation.BindingResult.cliente", br);
            ra.addFlashAttribute("erro", "Já existe um cadastro com esse CPF.");
            return "redirect:/cadastrar-cliente";
        }

        // E-mail único
        var emailExistente = peopleRepository.findByEmail(form.getEmail());
        if (emailExistente.isPresent()) {
            ra.addFlashAttribute("cliente", form);
            ra.addFlashAttribute("org.springframework.validation.BindingResult.cliente", br);
            ra.addFlashAttribute("erro", "E-mail já utilizado por outro cadastro.");
            return "redirect:/cadastrar-cliente";
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

        ClientModel client = new ClientModel();
        client.setCpf(pessoaSalva);
        client.setTelefone(pessoaSalva.getTelefone());
        clientRepository.save(client);

        ra.addFlashAttribute("sucesso", "Cliente cadastrado com sucesso!"); // << Cria o FlashAttribute  para a mensagem de sucesso
        return "redirect:/cadastrar-cliente"; // << redireciona para a pagina de cadastro novamente
    }

}
