package com.project.jujucabomboniermanegementsystem.managementsystem.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import com.project.jujucabomboniermanegementsystem.managementsystem.dtos.ProdutoForm;
import com.project.jujucabomboniermanegementsystem.managementsystem.repository.ProdutoRepository;
import com.project.jujucabomboniermanegementsystem.managementsystem.models.ProdutoModel;

@Controller
public class ProdutoController {
    private final ProdutoRepository produtoRepository; //injeta o repositório no controller - é a conexão com o banco de dados

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @GetMapping("/cadastrar-produto")
     public String cadastrarProduto(Model model) {
        if (!model.containsAttribute("produto")) {
            model.addAttribute("produto", new ProdutoForm());
        }
        return "cadastrar-produto";
    }

    @PostMapping("/cadastrar-produto")
    public String salvarProduto(@ModelAttribute("produto") ProdutoForm form,
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
        ProdutoModel produto = new ProdutoModel();
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
        produtoRepository.save(produto);

        ra.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");
        return "redirect:/cadastrar-produto";
    }
}
