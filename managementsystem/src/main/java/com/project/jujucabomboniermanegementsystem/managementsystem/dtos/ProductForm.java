package com.project.jujucabomboniermanegementsystem.managementsystem.dtos;
import java.time.LocalDate;
import java.io.Serializable;

//transferir dados do front pro back

public class ProductForm implements Serializable{
    //serializable: transformar objetos Java em bytes que podem ser salvos, transmitidos ou armazenados.
    // quando submeter forms, spring tem que:
    //-criar uma instância do objeto Form
    //-popular comdados do form
    //-manter o obj entre rquisicoes
    //-transmitir entre servidor e cliente
    //-popular com os dados do formulário

    //apenas campos do formulario (posso nao enviar td pro bd)
    private String lote;
    private Double preco;
    private String nome;
    private LocalDate dataValidade;
    private Double peso;
    private String unidade;
    private String marca;
    private String tipoProduto;
    private String imagem;
    private Integer quantidade;

    // Getters and Setters
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
