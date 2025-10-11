package com.project.jujucabomboniermanegementsystem.managementsystem.models;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "tb_seller")
public class SellerModel implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id_sales;
    private String metaDeVendas;
    private String horasTrabalhadas;
    @ManyToOne
    @JoinColumn(name = "cpf", referencedColumnName = "cpf")
    private PeopleModel cpf;

    public UUID getId_sales() {
        return id_sales;
    }

    public void setId_sales(UUID id_sales) {
        this.id_sales = id_sales;
    }


    public String getHorasTrabalhadas() {
        return horasTrabalhadas;
    }

    public void setHorasTrabalhadas(String horasTrabalhadas) {
        this.horasTrabalhadas = horasTrabalhadas;
    }

    public String getMetaDeVendas() {
        return metaDeVendas;
    }

    public void setMetaDeVendas(String metaDeVendas) {
        this.metaDeVendas = metaDeVendas;
    }

    public PeopleModel getCpf() {
        return cpf;
    }

    public void setCpf(PeopleModel cpf) {
        this.cpf = cpf;
    }
}
