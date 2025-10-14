package com.project.jujucabomboniermanegementsystem.managementsystem.models;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "tb_clients")

public class ClientModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id // << Anotação de identificação de id
    @GeneratedValue(strategy = GenerationType.AUTO) // << Gerador de ID
    private UUID id_client; // <<  id do Cliente
    private String cargo; // << Cargo do cliente
    private String telefone; // << Telefone do cliente
    @ManyToOne // << marca a relação com outra tabela
    @JoinColumn(name = "cpf", referencedColumnName = "cpf") // << diz que esse campo é relacionado a outra tabela
    private PeopleModel cpf; // << campo de cpf do cliente


    // Metodos get e setters da model cliente


    public UUID getId_client() {
        return id_client;
    }

    public void setId_client(UUID id_client) {
        this.id_client = id_client;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public PeopleModel getCpf() {
        return cpf;
    }

    public void setCpf(PeopleModel cpf) {
        this.cpf = cpf;
    }
}