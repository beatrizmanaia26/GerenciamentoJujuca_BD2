package com.project.jujucabomboniermanegementsystem.managementsystem.controllers;

import com.project.jujucabomboniermanegementsystem.managementsystem.models.ClientModel;
import com.project.jujucabomboniermanegementsystem.managementsystem.models.PeopleModel;
import com.project.jujucabomboniermanegementsystem.managementsystem.repository.ClientRepository;
import com.project.jujucabomboniermanegementsystem.managementsystem.repository.PeopleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
public class GerenciarClienteController {

    private final ClientRepository clientRepository;
    private final PeopleRepository peopleRepository;

    public GerenciarClienteController(ClientRepository clientRepository, PeopleRepository peopleRepository) {
        this.clientRepository = clientRepository;
        this.peopleRepository = peopleRepository;
    }

    // LISTAR CLIENTES
    @GetMapping("/gerenciar-clientes")
    public String listarClientes(Model model) {
        List<ClientModel> clientes = clientRepository.findAll();
        model.addAttribute("clientes", clientes);
        return "gerenciar-clientes";
    }

    
    @GetMapping("/gerenciar-clientes/editar/{id}")
    public String editarCliente(@PathVariable("id") UUID id, Model model, RedirectAttributes ra) {
        var clientOpt = clientRepository.findById(id);
        if (clientOpt.isEmpty()) {
            ra.addFlashAttribute("erro", "Cliente não encontrado.");
            return "redirect:/gerenciar-clientes";
        }

        ClientModel client = clientOpt.get();
        PeopleModel pessoa = client.getCpf();
        model.addAttribute("cliente", client);
        model.addAttribute("pessoa", pessoa);
        return "editar-cliente";
    }

    // SALVAR ALTERAÇÕES
    @PostMapping("/gerenciar-clientes/salvar")
    public String salvarCliente(@ModelAttribute("pessoa") PeopleModel pessoa,
                                @RequestParam("id") UUID clientId,
                                RedirectAttributes ra) {

        try {
            // Atualiza dados da pessoa
            peopleRepository.save(pessoa);

            // Mantém vínculo no ClientModel
            var client = clientRepository.findById(clientId).orElse(null);
            if (client != null) {
                client.setCpf(pessoa);
                clientRepository.save(client);
            }

            ra.addFlashAttribute("sucesso", "Cliente atualizado com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro ao salvar cliente: " + e.getMessage());
        }

        return "redirect:/gerenciar-clientes";
    }

    // EXCLUIR CLIENTE
    @GetMapping("/gerenciar-clientes/excluir/{id}")
    public String excluirCliente(@PathVariable("id") UUID id, RedirectAttributes ra) {
        try {
            var client = clientRepository.findById(id).orElse(null);
            if (client != null) {
                // Exclui da tabela client e da tabela people
                String cpf = client.getCpf().getCpf();
                clientRepository.deleteById(id);
                peopleRepository.deleteById(cpf);
                ra.addFlashAttribute("sucesso", "Cliente excluído com sucesso!");
            } else {
                ra.addFlashAttribute("erro", "Cliente não encontrado.");
            }
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro ao excluir cliente: " + e.getMessage());
        }

        return "redirect:/gerenciar-clientes";
    }
}
