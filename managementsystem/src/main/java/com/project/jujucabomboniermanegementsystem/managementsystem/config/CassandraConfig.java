package com.project.jujucabomboniermanegementsystem.managementsystem.config;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Configuration
public class CassandraConfig {

    @Bean
    public CqlSession cqlSession() {
        try {
            System.out.println("🔌 Configurando conexão com Cassandra...");

            // Nome do seu bundle dentro de src/main/resources/
            ClassPathResource resource = new ClassPathResource("seu zip secure connect");

            if (!resource.exists()) {
                throw new IllegalStateException("❌ Arquivo secure-connect-projetojujucacassandra.zip não encontrado em resources/");
            }

            // Cria um arquivo temporário
            Path tempFile = Files.createTempFile("cassandra-connect", ".zip");
            try (InputStream inputStream = resource.getInputStream()) {
                Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }

            // Conecta ao AstraDB
            CqlSession session = CqlSession.builder()
                    .withCloudSecureConnectBundle(tempFile)
                    .withAuthCredentials(
                            "seu cliente id",  // CLIENT ID
                            "seu cliente secret" // CLIENT SECRET
                    )
                    .withKeyspace("history_transation") // coloque o nome exato do seu keyspace
                    .build();

            System.out.println("✅ Cassandra conectado e pronto para uso!");
            return session;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("❌ Falha ao conectar com Cassandra: " + e.getMessage(), e);
        }
    }
}
