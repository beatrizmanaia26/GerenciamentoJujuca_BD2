package com.project.jujucabomboniermanegementsystem.managementsystem.services;

import com.project.jujucabomboniermanegementsystem.managementsystem.models.ProductModel;
import com.project.jujucabomboniermanegementsystem.managementsystem.repository.ProductRepository;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    private final MongoTemplate mongoTemplate;
    private final ProductRepository productRepository;

    public PurchaseService(MongoTemplate mongoTemplate, ProductRepository productRepository) {
        this.mongoTemplate = mongoTemplate;
        this.productRepository = productRepository;
    }

    public PurchaseResult comprar(String productId, int quantidadeDesejada) {
        if (quantidadeDesejada <= 0) {
            return PurchaseResult.erro("Quantidade inválida.");
        }

        // 1) Busca o produto para pegar nome e preço (exibição)
        var opt = productRepository.findById(productId);
        if (opt.isEmpty()) {
            return PurchaseResult.erro("Produto não encontrado.");
        }
        ProductModel p = opt.get();

        // 2) Tenta decrementar estoque de forma ATÔMICA somente se houver quantidade suficiente
        Query q = new Query(Criteria.where("id").is(productId).and("quantidade").gte(quantidadeDesejada));
        Update u = new Update().inc("quantidade", -quantidadeDesejada);

        UpdateResult res = mongoTemplate.updateFirst(q, u, ProductModel.class);

        if (res.getModifiedCount() == 1) {
            double subtotal = p.getPreco() != null ? p.getPreco() * quantidadeDesejada : 0.0;
            return PurchaseResult.ok("Compra realizada com sucesso!", p.getNome(), quantidadeDesejada, p.getPreco(), subtotal);
        } else {
            return PurchaseResult.erro("Estoque insuficiente para a quantidade solicitada.");
        }
    }

    public static class PurchaseResult {
        private boolean sucesso;
        private String mensagem;
        private String produtoNome;
        private Integer quantidade;
        private Double precoUnitario;
        private Double subtotal;

        public static PurchaseResult ok(String msg, String produtoNome, int qtd, Double preco, Double subtotal) {
            PurchaseResult r = new PurchaseResult();
            r.sucesso = true;
            r.mensagem = msg;
            r.produtoNome = produtoNome;
            r.quantidade = qtd;
            r.precoUnitario = preco;
            r.subtotal = subtotal;
            return r;
        }

        public static PurchaseResult erro(String msg) {
            PurchaseResult r = new PurchaseResult();
            r.sucesso = false;
            r.mensagem = msg;
            return r;
        }

        public boolean isSucesso() { return sucesso; }
        public String getMensagem() { return mensagem; }
        public String getProdutoNome() { return produtoNome; }
        public Integer getQuantidade() { return quantidade; }
        public Double getPrecoUnitario() { return precoUnitario; }
        public Double getSubtotal() { return subtotal; }
    }
}
