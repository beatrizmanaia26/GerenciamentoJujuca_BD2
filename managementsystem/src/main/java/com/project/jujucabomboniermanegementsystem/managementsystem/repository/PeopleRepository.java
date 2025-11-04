package com.project.jujucabomboniermanegementsystem.managementsystem.repository;


import com.project.jujucabomboniermanegementsystem.managementsystem.models.PeopleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PeopleRepository extends JpaRepository<PeopleModel, String> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    Optional<PeopleModel> findByEmail(String email);
    PeopleModel findByCpf(String cpf);
}