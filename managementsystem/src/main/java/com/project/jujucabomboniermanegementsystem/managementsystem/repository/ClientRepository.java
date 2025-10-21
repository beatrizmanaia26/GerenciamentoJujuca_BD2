package com.project.jujucabomboniermanegementsystem.managementsystem.repository;

import com.project.jujucabomboniermanegementsystem.managementsystem.models.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<ClientModel, UUID> {

    List<ClientModel> findByCpf_Cpf(String cpf);
}