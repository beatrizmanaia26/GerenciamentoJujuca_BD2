package com.project.jujucabomboniermanegementsystem.managementsystem.models;

import jakarta.persistence.Id;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

//mapear tabelas para persistir no bd, tem anotacoes de bd)
@Document(collection = "productDB")  //collection no mongo
public class ProductModel {

    @Id
    private String id; //gerado pelo mongl automaticamente
    private String nome;
    private String lote;
    private Double preco;
    private LocalDate dataValidade;
    private Double peso;
    private String unidade;
    private String marca;
    private String tipoProduto;
    private String imagem;
    private Integer quantidade;



    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }

    public LocalDate getDataValidade() { return dataValidade; }
    public void setDataValidade(LocalDate dataValidade) { this.dataValidade = dataValidade; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }

    public String getUnidade() { return unidade; }
    public void setUnidade(String unidade) { this.unidade = unidade; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getTipoProduto() { return tipoProduto; }
    public void setTipoProduto(String tipoProduto) { this.tipoProduto = tipoProduto; }

    public String getImagem() { return imagem; }
    public void setImagem(String imagem) { this.imagem = imagem; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
}
