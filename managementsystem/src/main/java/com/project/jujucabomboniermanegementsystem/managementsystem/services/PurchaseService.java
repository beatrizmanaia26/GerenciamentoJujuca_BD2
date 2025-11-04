package com.project.jujucabomboniermanegementsystem.managementsystem.services;

import com.project.jujucabomboniermanegementsystem.managementsystem.models.ProductModel;
import com.project.jujucabomboniermanegementsystem.managementsystem.models.PeopleModel;
import com.project.jujucabomboniermanegementsystem.managementsystem.repository.ProductRepository;
import com.project.jujucabomboniermanegementsystem.managementsystem.repository.PeopleRepository;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PurchaseService {

    private final MongoTemplate mongoTemplate;
    private final ProductRepository productRepository;
    private final HistoryService historyService;
    private final PeopleRepository peopleRepository;

    public PurchaseService(MongoTemplate mongoTemplate,
                           ProductRepository productRepository,
                           HistoryService historyService,
                           PeopleRepository peopleRepository) {
        this.mongoTemplate = mongoTemplate;
        this.productRepository = productRepository;
        this.historyService = historyService;
        this.peopleRepository = peopleRepository;
    }

    public PurchaseResult comprar(String productId, int quantidadeDesejada, String cpfCliente) {

        if (quantidadeDesejada <= 0) {
            return PurchaseResult.erro("Quantidade inválida.");
        }

        var opt = productRepository.findById(productId);
        if (opt.isEmpty()) {
            return PurchaseResult.erro("Produto não encontrado.");
        }

        ProductModel produto = opt.get();

        // Verifica estoque
        Query q = new Query(Criteria.where("id").is(productId)
                .and("quantidade").gte(quantidadeDesejada));
        Update u = new Update().inc("quantidade", -quantidadeDesejada);

        UpdateResult res = mongoTemplate.updateFirst(q, u, ProductModel.class);

        if (res.getModifiedCount() == 1) {
            double subtotal = produto.getPreco() != null ? produto.getPreco() * quantidadeDesejada : 0.0;

            // Identifica cliente
            String nomeCliente;
            String cpf;

            if (cpfCliente != null && !cpfCliente.isBlank()) {
                PeopleModel cliente = peopleRepository.findByCpf(cpfCliente);

                if (cliente != null) {
                    nomeCliente = cliente.getNome();
                    cpf = cliente.getCpf();
                } else {
                    nomeCliente = "Cliente não cadastrado";
                    cpf = cpfCliente;
                }
            } else {
                nomeCliente = "N/A";
                cpf = "N/A";
            }

            // Salva no Cassandra
            try {
                UUID id = historyService.registrarTransacao(
                        produto.getNome(),
                        quantidadeDesejada,
                        produto.getPreco(),
                        cpf,
                        nomeCliente
                );
                System.out.println("Histórico salvo com ID: " + id);
            } catch (Exception e) {
                System.err.println("Erro ao salvar histórico no Cassandra: " + e.getMessage());
            }

            return PurchaseResult.ok("Venda registrada com sucesso!",
                    produto.getNome(),
                    quantidadeDesejada,
                    produto.getPreco(),
                    subtotal);
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
