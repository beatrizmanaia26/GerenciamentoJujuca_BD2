package com.project.jujucabomboniermanegementsystem.managementsystem.models;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "tb_seller")
public class SellerModel implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id // << define o campo como id
    @GeneratedValue(strategy = GenerationType.AUTO) // << gerador de id
    private UUID id_sales; // << id do seller
    private String password; // << senha da pessoa
    private String email;
    //private String metaDeVendas; //<< meta de vendas do seller
    //private String horasTrabalhadas; // <<  horas trabalhadas do seller
    @ManyToOne // << identifica a chave estrangeira
    @JoinColumn(name = "cpf", referencedColumnName = "cpf") // << define como deve ser feita a relação
    private PeopleModel cpf; // << cpf do seller

    // Metodos get e setters da classe de Seller

    public UUID getId_sales() {
        return id_sales;
    }

    public void setId_sales(UUID id_sales) {
        this.id_sales = id_sales;
    }

    //public String getHorasTrabalhadas() {
    //    return horasTrabalhadas;
    //}

    //public void setHorasTrabalhadas(String horasTrabalhadas) {
    //    this.horasTrabalhadas = horasTrabalhadas;
    //}

    //public String getMetaDeVendas() {
    //    return metaDeVendas;
    //}

    //public void setMetaDeVendas(String metaDeVendas) {
    //    this.metaDeVendas = metaDeVendas;
    //}

    public PeopleModel getCpf() {
        return cpf;
    }

    public void setCpf(PeopleModel cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
