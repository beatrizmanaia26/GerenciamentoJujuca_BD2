package com.project.jujucabomboniermanegementsystem.managementsystem.models;

import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Column;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Table("history")
public class HistoryModel {

    @PrimaryKey
    private UUID id_transacao;

    @Column("produto")
    private String produto;

    @Column("quantidade")
    private int quantidade;

    @Column("preco")
    private double preco;

    @Column("cpf_cliente")
    private String cpf_cliente;

    @Column("nome_cliente")
    private String nome_cliente;

    @Column("date")
    private LocalDate date;

    @Column("hour_min_sec")
    private String hourMinSec;

    public UUID getId_transacao() { 
        return id_transacao; 
    }
    public void setId_transacao(UUID id_transacao) { 
        this.id_transacao = id_transacao; 
    }
    public String getProduto() { 
        return produto; 
    }
    public void setProduto(String produto) { 
        this.produto = produto; 
    }
    public int getQuantidade() { 
        return quantidade; 
    }
    public void setQuantidade(int quantidade) { 
        this.quantidade = quantidade; 
    }
    public double getPreco() { 
        return preco; 
    }
    public void setPreco(double preco) { 
        this.preco = preco; 
    }
    public String getCpf_cliente() { 
        return cpf_cliente; 
    }
    public void setCpf_cliente(String cpf_cliente) { 
        this.cpf_cliente = cpf_cliente; 
    }
    public String getNome_cliente() { 
        return nome_cliente; 
    }
    public void setNome_cliente(String nome_cliente) { 
        this.nome_cliente = nome_cliente; 
    }
    public LocalDate getDate() { 
        return date; 
    }
    public void setDate(LocalDate date) { 
        this.date = date; 
    }
    public String getHourMinSec() { 
        return hourMinSec; 
    }
    public void setHourMinSec(String hourMinSec) { 
        this.hourMinSec = hourMinSec; 
    }
}
