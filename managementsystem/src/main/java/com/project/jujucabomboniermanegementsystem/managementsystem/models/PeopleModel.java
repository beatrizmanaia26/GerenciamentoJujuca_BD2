package com.project.jujucabomboniermanegementsystem.managementsystem.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "tb_people")
public class PeopleModel implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id // << Marca como ID a coluna
    @Column(nullable = false, unique = true, length = 11) // << define como deve ser o campo
    private String cpf; // << cpf da pessoa
    private String nome; // << Nome da pessoa
    private String email; //<< email da pessoa
    private String password; // << senha da pessoa
    private String cep; // <<  cep da pessoa
    private String complemento; // << complemento da pessoa
    private String endereco; // << endereço da pessoa
    private String telefone; // <<  telefone da pessoa
    private int numero; // << numero da casa

    // Metodos get e setters da model de pessoa

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}