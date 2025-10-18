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


    @GetMapping("/cadastrar-cliente") // << Endpoint de cadastro do cliente
    public String getCadastro(Model model) {
        if (!model.containsAttribute("cliente")) { // << verifica se ja existe atributos para o objeto
            model.addAttribute("cliente", new ClienteForm()); // << instancia a classe formulario para cadastro do cliente
        }
        return "cadastrar-cliente"; // << Renderiza a pagina
    }

    @PostMapping("/cadastrar-cliente") // <<Endpoint para receber os dados com metodo post
    public String postCadastro(@ModelAttribute("cliente") FuncionarioForm form,
                               BindingResult br,
                               RedirectAttributes ra){ 
        if (form.getCpf() == null || form.getCpf().isBlank() // << verifica se o campo foi preenchido
                || form.getNome() == null || form.getNome().isBlank() // << verifica se o campo foi preenchido
                || form.getEmail() == null || form.getEmail().isBlank() // << verifica se o campo foi preenchido
                || form.getNumero() == null || form.getNumero().isBlank() // << verifica se o campo foi preenchido
                || form.getCep() == null || form.getCep().isBlank() // << verifica se o campo foi preenchido
                || form.getComplemento() == null || form.getComplemento().isBlank() // << verifica se o campo foi preenchido
                || form.getEndereco() == null || form.getEndereco().isBlank() // << verifica se o campo foi preenchido
                || form.getTelefone() == null || form.getTelefone().isBlank()) {// << verifica se o campo foi preenchido

            ra.addFlashAttribute("cliente", form); // << salva os campos preenchidos
            ra.addFlashAttribute("org.springframework.validation.BindingResult.cliente", br);
            ra.addFlashAttribute("erro", "Preencha todos os campos obrigatórios."); // << informa mensagem que o campo não foi preenchido
            return "redirect:/cadastrar-cliente"; // << retorna para a pagina de cadastro
        }
        // CPF único
        if (peopleRepository.existsByCpf(form.getCpf())) { // << Verifica se o cpf ja existe no banco
            ra.addFlashAttribute("cliente", form);
            ra.addFlashAttribute("org.springframework.validation.BindingResult.cliente", br);
            ra.addFlashAttribute("erro", "Já existe um cadastro com esse CPF.");
            return "redirect:/cadastrar-cliente";
        }

        // E-mail único
        var emailExistente = peopleRepository.findByEmail(form.getEmail()); // << verifica se o email ja existe 
        if (emailExistente.isPresent()) {
            ra.addFlashAttribute("cliente", form);
            ra.addFlashAttribute("org.springframework.validation.BindingResult.cliente", br);
            ra.addFlashAttribute("erro", "E-mail já utilizado por outro cadastro.");
            return "redirect:/cadastrar-cliente";
        }

        PeopleModel people = new PeopleModel(); // << cria o objeto da classe people para salvar no banco
        people.setCpf(form.getCpf()); 
        people.setNome(form.getNome());
        people.setEmail(form.getEmail());
        people.setCep(form.getCep());
        people.setComplemento(form.getComplemento());
        people.setEndereco(form.getEndereco());
        people.setTelefone(form.getTelefone());
        people.setNumero(form.getNumero());
        peopleRepository.save(people); // << salva os dados no banco

        ClientModel client = new ClientModel(); // << cria o objeto de cliente para salvar no banco
        client.setCpf(pessoaSalva);
        client.setTelefone(pessoaSalva.getTelefone());
        clientRepository.save(client); // << Salve os dados no banco

        ra.addFlashAttribute("sucesso", "Cliente cadastrado com sucesso!"); // << Cria o FlashAttribute  para a mensagem de sucesso
        return "redirect:/cadastrar-cliente"; // << redireciona para a pagina de cadastro novamente
    }

}
