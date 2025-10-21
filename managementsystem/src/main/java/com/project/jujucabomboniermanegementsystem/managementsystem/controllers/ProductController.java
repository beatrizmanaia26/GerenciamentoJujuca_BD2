package com.project.jujucabomboniermanegementsystem.managementsystem.controllers;
import com.project.jujucabomboniermanegementsystem.managementsystem.dtos.ProductForm;
import com.project.jujucabomboniermanegementsystem.managementsystem.models.ProductModel;
import com.project.jujucabomboniermanegementsystem.managementsystem.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

@Controller
public class ProductController {
    private final ProductRepository productRepository; //injeta o repositório no controller - é a conexão com o banco de dados

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

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

        // Validações básicas
        if (form.getNome() == null || form.getNome().isBlank() ||
                form.getPreco() == null || form.getPreco() <= 0 ||
                form.getQuantidade() == null || form.getQuantidade() < 0) {

            ra.addFlashAttribute("produto", form);
            ra.addFlashAttribute("erro", "Preencha todos os campos obrigatórios.");
            return "redirect:/cadastrar-produto";
        }

        // Converter Form para Model
        ProductModel produto = new ProductModel();
        produto.setNome(form.getNome());//copia nome
        produto.setLote(form.getLote());
        produto.setPreco(form.getPreco());
        produto.setDataValidade(form.getDataValidade());
        produto.setPeso(form.getPeso());
        produto.setUnidade(form.getUnidade());
        produto.setMarca(form.getMarca());
        produto.setTipoProduto(form.getTipoProduto());
        produto.setImagem(form.getImagem());
        produto.setQuantidade(form.getQuantidade());

        // Salvar no MongoDB
        productRepository.save(produto);

        ra.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");
        return "redirect:/cadastrar-produto";
    }
}