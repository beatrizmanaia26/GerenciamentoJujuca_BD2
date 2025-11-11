package com.project.jujucabomboniermanegementsystem.managementsystem.controllers;

import com.project.jujucabomboniermanegementsystem.managementsystem.repository.ProductRepository;
import com.project.jujucabomboniermanegementsystem.managementsystem.services.PurchaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LojaController {

    private final ProductRepository productRepository;
    private final PurchaseService purchaseService;

    public LojaController(ProductRepository productRepository, PurchaseService purchaseService) {
        this.productRepository = productRepository;
        this.purchaseService = purchaseService;
    }

    @GetMapping("/loja")
    public String listarProdutos(@RequestParam(value = "q", required = false) String q, Model model) {
        if (q != null && !q.isBlank()) {
            var resultados = productRepository
                    .findByNomeContainingIgnoreCaseOrMarcaContainingIgnoreCaseOrTipoProdutoContainingIgnoreCase(q, q, q);
            model.addAttribute("produtos", resultados);
        } else {
            model.addAttribute("produtos", productRepository.findAll());
        }
        model.addAttribute("q", q == null ? "" : q.trim());
        return "loja";
    }

    @PostMapping("/loja/comprar")
    public String comprar(@RequestParam("productId") String productId,
                          @RequestParam(name = "quantidade", required = false) Integer quantidade,
                          @RequestParam(name = "cpfCliente", required = false) String cpfCliente,
                          RedirectAttributes ra) {

        var result = purchaseService.comprar(
                productId,
                quantidade != null ? quantidade : 0,
                cpfCliente // CPF opcional
        );

        if (result.isSucesso()) {
            String msg = result.getMensagem()
                    + " Produto: " + result.getProdutoNome()
                    + " | Quantidade: " + result.getQuantidade()
                    + " | Unitário: R$ " + String.format("%.2f", result.getPrecoUnitario())
                    + " | Subtotal: R$ " + String.format("%.2f", result.getSubtotal());
            ra.addFlashAttribute("sucesso", msg);
        } else {
            ra.addFlashAttribute("erro", result.getMensagem());
        }

        return "redirect:/loja";
    }
}
