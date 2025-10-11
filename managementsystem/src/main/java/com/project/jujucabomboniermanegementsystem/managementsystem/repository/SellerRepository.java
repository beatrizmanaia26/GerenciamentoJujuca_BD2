package com.project.jujucabomboniermanegementsystem.managementsystem.repository;

import com.project.jujucabomboniermanegementsystem.managementsystem.models.SellerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface SellerRepository extends JpaRepository<SellerModel, UUID> {
    List<SellerModel> findByCpf_Cpf(String cpf);
}