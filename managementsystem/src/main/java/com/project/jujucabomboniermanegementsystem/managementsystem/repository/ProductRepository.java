package com.project.jujucabomboniermanegementsystem.managementsystem.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.project.jujucabomboniermanegementsystem.managementsystem.models.ProductModel;
import java.util.List;

public interface ProductRepository extends MongoRepository<ProductModel, String> {

    // Busca por nome OU marca OU tipo de produto (case-insensitive)
    List<ProductModel> findByNomeContainingIgnoreCaseOrMarcaContainingIgnoreCaseOrTipoProdutoContainingIgnoreCase(
            String nome, String marca, String tipoProduto
    );
}
