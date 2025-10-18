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
    private final ClientRepository clientRepository; // << Necessario para consultar o Client no banco de dados
    // contrutor das models
    public CadastroController(PeopleRepository peopleRepository, SellerRepository sellerRepository, ClientRepository clientRepository) { // << Construtor necessario para injeção das dependencias
        this.peopleRepository = peopleRepository;
        this.sellerRepository = sellerRepository;
        this.clientRepository = clientRepository;
    }


    @GetMapping("/cadastrar-funcionario") // << Endpoint de cadastro do funcionario
    public String getCadastro(Model model) {
        if (!model.containsAttribute("funcionario")) {// << verifica se ja existe atributos para o objeto
            model.addAttribute("funcionario", new FuncionarioForm());// << instancia a classe formulario para cadastro do funcionario
        }
        return "cadastrar-funcionario";// << Renderiza a pagina
    }

    @PostMapping("/cadastrar-funcionario")// <<Endpoint para receber os dados com metodo post
    public String postCadastro(@ModelAttribute("funcionario") FuncionarioForm form,
                               BindingResult br,
                               RedirectAttributes ra){
        if (form.getCpf() == null || form.getCpf().isBlank() // << verifica se o campo foi preenchido
                || form.getNome() == null || form.getNome().isBlank() // << verifica se o campo foi preenchido
                || form.getEmail() == null || form.getEmail().isBlank() // << verifica se o campo foi preenchido
                || form.getNumero() == null || form.getNumero().isBlank() // << verifica se o campo foi preenchido
                || form.getCep() == null || form.getCep().isBlank() // << verifica se o campo foi preenchido
                || form.getComplemento() == null || form.getComplemento().isBlank() // << verifica se o campo foi preenchido
                || form.getEndereco() == null || form.getEndereco().isBlank() // << verifica se o campo foi preenchido
                || form.getTelefone() == null || form.getTelefone().isBlank() // << verifica se o campo foi preenchido
                || form.getPassword() == null || form.getPassword().isBlank()) {// << verifica se o campo foi preenchido 

            ra.addFlashAttribute("funcionario", form); // << salva os campos preenchidos
            ra.addFlashAttribute("org.springframework.validation.BindingResult.funcionario", br);
            ra.addFlashAttribute("erro", "Preencha todos os campos obrigatórios."); // << informa mensagem que o campo não foi preenchido
            return "redirect:/cadastrar-funcionario"; // << retorna para a pagina de cadastro
        }
        // CPF único
        if (peopleRepository.existsByCpf(form.getCpf())) {  // << Verifica se o cpf ja existe
            ra.addFlashAttribute("funcionario", form); // << salva os campos preenchidos
            ra.addFlashAttribute("org.springframework.validation.BindingResult.funcionario", br);
            ra.addFlashAttribute("erro", "Já existe um cadastro com esse CPF."); // << informa mensagem que o campo não foi preenchido
            return "redirect:/cadastrar-funcionario"; // << retorna para a pagina de cadastro
        }

        // E-mail único
        var emailExistente = peopleRepository.findByEmail(form.getEmail()); 
        if (emailExistente.isPresent()) { // << verifica se o email ja existe
            ra.addFlashAttribute("funcionario", form); // << salva os dados ja preenchidos
            ra.addFlashAttribute("org.springframework.validation.BindingResult.funcionario", br);
            ra.addFlashAttribute("erro", "E-mail já utilizado por outro cadastro."); // << informa mensagem que o campo não foi preenchido
            return "redirect:/cadastrar-funcionario"; // << retorna para a pagina de cadastro
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

        SellerModel seller = new SellerModel(); // << cria o objeto de Seller para salvar no banco
        seller.setCpf(pessoaSalva);
        seller.setEmail(pessoaSalva.getEmail());
        seller.setPassword(form.getPassword());
        sellerRepository.save(seller); // << Salve os dados no banco

        ClientModel client = new ClientModel(); // << cria o objeto de cliente para salvar no banco
        client.setCpf(pessoaSalva);
        client.setTelefone(pessoaSalva.getTelefone());
        clientRepository.save(client); // << Salve os dados no banco

        ra.addFlashAttribute("sucesso", "Funcionário cadastrado com sucesso!"); // << Cria o FlashAttribute  para a mensagem de sucesso
        return "redirect:/cadastrar-funcionario"; // << redireciona para a pagina de cadastro novamente
    }

}
