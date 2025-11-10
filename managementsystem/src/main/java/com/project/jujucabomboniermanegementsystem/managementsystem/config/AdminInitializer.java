package com.project.jujucabomboniermanegementsystem.managementsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.project.jujucabomboniermanegementsystem.managementsystem.models.PeopleModel;
import com.project.jujucabomboniermanegementsystem.managementsystem.models.SellerModel;
import com.project.jujucabomboniermanegementsystem.managementsystem.repository.PeopleRepository;
import com.project.jujucabomboniermanegementsystem.managementsystem.repository.SellerRepository;

import java.util.List;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private SellerRepository sellerRepository;
    
    @Autowired
    private PeopleRepository peopleRepository;

    @Override
    public void run(String... args) throws Exception {
        try {
            createAdminIfNotExists();
        } catch (Exception e) {
            System.err.println("Erro ao criar admin: " + e.getMessage());
        }
    }

    private void createAdminIfNotExists() {
        String adminEmail = "Admin@admin.com";
        
        // Verifica se já existe algum seller com este email
        List<SellerModel> allSellers = sellerRepository.findAll();
        boolean adminExists = allSellers.stream()
                .anyMatch(seller -> adminEmail.equals(seller.getEmail()));
        
        if (!adminExists) {
            // Busca ou cria a pessoa
            PeopleModel adminPerson = createOrGetAdminPerson();
            
            // Cria o seller admin
            SellerModel adminSeller = new SellerModel();
            adminSeller.setEmail(adminEmail);
            adminSeller.setPassword("Admin");
            adminSeller.setCpf(adminPerson);
            
            sellerRepository.save(adminSeller);
            System.out.println("Admin criado com sucesso: " + adminEmail);
        } else {
            System.out.println("Admin já existe: " + adminEmail);
        }
    }

    private PeopleModel createOrGetAdminPerson() {
        String adminCpf = "00000000000";
        
        // Tenta buscar a pessoa, se não existir cria uma nova
        return peopleRepository.findById(adminCpf)
            .orElseGet(() -> {
                PeopleModel newPerson = new PeopleModel();
                newPerson.setCpf(adminCpf);
                return peopleRepository.save(newPerson);
            });
    }
}