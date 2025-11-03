package com.project.jujucabomboniermanegementsystem.managementsystem.controllers;

import com.project.jujucabomboniermanegementsystem.managementsystem.dtos.ProductForm;
import com.project.jujucabomboniermanegementsystem.managementsystem.models.ProductModel;
import com.project.jujucabomboniermanegementsystem.managementsystem.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ==================== CADASTRAR PRODUTO ====================
    @GetMapping("/cadastrar-produto")
    public String cadastrarProduto(Model model) {
        if (!model.containsAttribute("produto")) {
            model.addAttribute("produto", new ProductForm());
        }
        return "cadastrar-produto";
    }

    @PostMapping("/cadastrar-produto")
    public String salvarProduto(@ModelAttribute("produto") ProductForm form,
                                RedirectAttributes ra) {

        if (form.getNome() == null || form.getNome().isBlank() ||
                form.getPreco() == null || form.getPreco() <= 0 ||
                form.getQuantidade() == null || form.getQuantidade() < 0) {

            ra.addFlashAttribute("produto", form);
            ra.addFlashAttribute("erro", "Preencha todos os campos obrigatórios.");
            return "redirect:/cadastrar-produto";
        }

        ProductModel produto = new ProductModel();
        produto.setNome(form.getNome());
        produto.setLote(form.getLote());
        produto.setPreco(form.getPreco());
        produto.setDataValidade(form.getDataValidade());
        produto.setPeso(form.getPeso());
        produto.setUnidade(form.getUnidade());
        produto.setMarca(form.getMarca());
        produto.setTipoProduto(form.getTipoProduto());
        produto.setImagem(form.getImagem());
        produto.setQuantidade(form.getQuantidade());

        productRepository.save(produto);

        ra.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");
        return "redirect:/cadastrar-produto";
    }

    // ==================== LISTAR PRODUTOS ====================
    @GetMapping("/gerenciar-produtos")
    public String listarProdutos(Model model) {
        model.addAttribute("produtos", productRepository.findAll());
        return "gerenciar-produtos";
    }

    // ==================== EDITAR PRODUTO ====================
    @GetMapping("/editar-produto/{id}")
    public String editarProduto(@PathVariable String id, Model model, RedirectAttributes ra) {
        Optional<ProductModel> produtoOpt = productRepository.findById(id);

        if (produtoOpt.isEmpty()) {
            ra.addFlashAttribute("erro", "Produto não encontrado.");
            return "redirect:/gerenciar-produtos";
        }

        model.addAttribute("produto", produtoOpt.get());
        return "editar-produto";
    }

    @PostMapping("/editar-produto")
    public String atualizarProduto(@ModelAttribute ProductModel produto, RedirectAttributes ra) {
        // mantém imagem antiga se o campo estiver vazio
        if (produto.getImagem() == null || produto.getImagem().isBlank()) {
            productRepository.findById(produto.getId()).ifPresent(original ->
                    produto.setImagem(original.getImagem())
            );
        }

        productRepository.save(produto);
        ra.addFlashAttribute("sucesso", "Produto atualizado com sucesso!");
        return "redirect:/gerenciar-produtos";
    }

    // ==================== EXCLUIR PRODUTO ====================
    @PostMapping("/excluir/{id}")
    public String excluirProduto(@PathVariable String id, RedirectAttributes ra) {
        try {
            if (productRepository.existsById(id)) {
                productRepository.deleteById(id);
                ra.addFlashAttribute("sucesso", "Produto excluído com sucesso!");
            } else {
                ra.addFlashAttribute("erro", "Produto não encontrado para exclusão.");
            }
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro ao excluir produto: " + e.getMessage());
        }

        return "redirect:/gerenciar-produtos";
    }
}
