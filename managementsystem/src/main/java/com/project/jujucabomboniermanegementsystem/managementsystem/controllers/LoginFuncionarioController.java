package com.project.jujucabomboniermanegementsystem.managementsystem.controllers;

import com.project.jujucabomboniermanegementsystem.managementsystem.models.ClientModel;
import com.project.jujucabomboniermanegementsystem.managementsystem.models.SellerModel;
import com.project.jujucabomboniermanegementsystem.managementsystem.repository.ClientRepository;
import com.project.jujucabomboniermanegementsystem.managementsystem.repository.PeopleRepository;
import com.project.jujucabomboniermanegementsystem.managementsystem.repository.SellerRepository;
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

    //private final PeopleRepository peopleRepository; // << Necessario para consultar a People no banco de dados
    private final SellerRepository sellerRepository;

    public LoginFuncionarioController(SellerRepository sellerRepository) { // << Construtor necessario para injeção das dependencias
        this.sellerRepository = sellerRepository;
    }

    @GetMapping("/login-funcionario") // << Mapeia a Custom Path de renderização da pagina (ENDPOINT)
    public String loginFuncionario(Model model) {
        model.addAttribute("seller", new SellerModel());
        return "login-funcionario"; // << Devolve a view (pagina) armazenada com esse nome na path /templates
    }

    @PostMapping("/login-funcionario") // << ENDPOINT post para processar os dados submetidos
    public String doLoginFuncionario(@ModelAttribute("seller") SellerModel form, // <<  Recebe os campos do formulario ligados a classe PeopleMode
                                     RedirectAttributes ra, // << Envia as mensagens apos o redirect
                                     HttpSession session) { // << Salva a sessão do usuario

        var opt = sellerRepository.findByEmail(form.getEmail()); // << Busca por email o usuario informado no formulario

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

        // Login OK
        session.setAttribute("userEmail", user.getEmail()); // << marca o email para gerenciar a sessão
        ra.addFlashAttribute("sucesso", "Login realizado com sucesso");
        return "redirect:/area-funcionario"; // << redireciona para a area do funcionario
    }

    // Página pós-login com os botões
    @GetMapping("/area-funcionario") // << end point da area do funcionario
    public String areaFuncionario(HttpSession session, RedirectAttributes ra) {
        Object email = session.getAttribute("userEmail"); // << verifica se o email não esta nulo (sessão ativa)
        if (email == null) {
            ra.addFlashAttribute("erro", "É necessário estar logado para acessar esta página"); // << caso esteja nula não renderiza a pagina
            return "redirect:/login-funcionario"; // << retorna para o login
        }
        return "area-funcionario"; // << caso exista a sessão, renderiza para a pagina de funcionario
    }

    // (Opcional) Logout simples
    @GetMapping("/logout") // << endpoint de logout
    public String logout(HttpSession session, RedirectAttributes ra) {
        session.invalidate(); // << finaliza a sessão
        ra.addFlashAttribute("sucesso", "Você saiu da sessão");
        return "redirect:/login-funcionario"; // << devolve para a pagina inicial de login
    }

}
