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
            
            // Carrega o Secure Connect Bundle do classpath
            ClassPathResource resource = new ClassPathResource("secure-connect-projetojujucacassandra.zip");
            
            if (!resource.exists()) {
                System.err.println("❌ Arquivo Cassandra não encontrado!");
                return null;
            }
            
            // Cria arquivo temporário para o JAR
            Path tempFile = Files.createTempFile("cassandra-connect", ".zip");
            try (InputStream inputStream = resource.getInputStream()) {
                Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }
            
            // Cria a sessão do Cassandra
            CqlSession session = CqlSession.builder()
                .withCloudSecureConnectBundle(tempFile)
                .withAuthCredentials(
                    "tzbdbreAfYGyMcOhoOJyjjKf", 
                    "CZrr35w2GNIKcC3jCihj9I3454fiDHULHawjCdRU4TlmY4GQb.GU9NAhDx6Db81JKxLx,IcyJ4.JHMO0xJzgZt1vKl+yfzqmM-ZmAC0Z3HRDs0do3XoqBu3Jc78k9E5E"
                )
                .build();
            
            System.out.println("✅ Cassandra conectado e pronto para uso!");
            return session;
                
        } catch (Exception e) {
            System.err.println("❌ Erro ao conectar com Cassandra: " + e.getMessage());
            return null;
        }
    }
}