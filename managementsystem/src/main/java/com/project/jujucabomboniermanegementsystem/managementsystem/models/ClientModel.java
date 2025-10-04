package com.project.jujucabomboniermanegementsystem.managementsystem.models;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "tb_clients")

public class ClientModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id_client;
    private String cargo;
    private String telefone;
    @ManyToOne
    @JoinColumn(name = "cpf", referencedColumnName = "cpf")
    private PeopleModel cpf;

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