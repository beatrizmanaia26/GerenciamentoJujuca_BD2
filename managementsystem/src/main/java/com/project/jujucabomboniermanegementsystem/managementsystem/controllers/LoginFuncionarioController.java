package com.project.jujucabomboniermanegementsystem.managementsystem.controllers;

import com.project.jujucabomboniermanegementsystem.managementsystem.repository.PeopleRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import com.project.jujucabomboniermanegementsystem.managementsystem.models.PeopleModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginFuncionarioController {


    private final PeopleRepository peopleRepository; // << Necessario para consultar a People no banco de dados

    public LoginFuncionarioController(PeopleRepository peopleRepository) { // << Construtor necessario para injeção das dependencias
        this.peopleRepository = peopleRepository;
    }

    @GetMapping("/login-funcionario") // << Mapeia a Custom Path de renderização da pagina (ENDPOINT)
    public String loginFuncionario(Model model) {
        model.addAttribute("people", new PeopleModel()); // << Adiciona ao modelo um objeto vazio para o formulário se ligar com Thymeleaf
        return "login-funcionario"; // << Devolve a view (pagina) armazenada com esse nome na path /templates
    }

    @PostMapping("/login-funcionario") // << ENDPOINT post para processar os dados submetidos
    public String doLoginFuncionario(@ModelAttribute("people") PeopleModel form, // <<  Recebe os campos do formulario ligados a classe PeopleModel
                                     RedirectAttributes ra, // << Envia as mensagens apos o redirect
                                     HttpSession session) { // << Salva a sessão do usuario

        var opt = peopleRepository.findByEmail(form.getEmail()); // << Busca por email o usuario informado no formulario

        if (opt.isEmpty()) { // << Caso retorne o opt nulo (não encontrou registro), mostra o erro e redireciona para a pagina de login
            ra.addFlashAttribute("erro", "E-mail invalido"); // << Flash attributes (devole o resultado da requisição)
            return "redirect:/login-funcionario"; // << Retorna para a pagina de login
        }

        var user = opt.get(); // << Recebe o usuario encontrado
        String raw = form.getPassword(); // << Recebe a senha do formulario
        String stored = user.getPassword(); // << Recebe a senha do usuario

        if (stored == null || !stored.equals(raw)) { // << Caso a senha seja nula ou não seja igual a senha armazenada, retornas o erro de senha incorreta
            ra.addFlashAttribute("erro", "Senha Invalida"); // << Flash attributes (devole o resultado da requisição)
            return "redirect:/login-funcionario"; // << Retorna para a pagina de login
        }

        ra.addFlashAttribute("sucesso", "Sucesso ao login"); // << Flash attributes (devole o resultado da requisição)
        // Marca usuário como logado (simples)
        session.setAttribute("userEmail", user.getEmail()); // << Marca como sessão logada

        return "redirect:/cadastrar-funcionario"; // << Redireciona para a pagina desejada apos o login bem sucedido
    }

}
