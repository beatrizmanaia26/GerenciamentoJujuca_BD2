package com.project.jujucabomboniermanegementsystem.managementsystem.config;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.annotation.PostConstruct; // USE ESTA se preferir Jakarta

@Component
public class DatabaseTester {
    
    private final CqlSession session;
    
    @Autowired // Adicione esta anotação
    public DatabaseTester(CqlSession session) {
        this.session = session;
    }
    
    @PostConstruct
    public void testConnection() {
        try {
            System.out.println("Testando conexão com Cassandra...");
            ResultSet rs = session.execute("select release_version from system.local");
            Row row = rs.one();
            
            if (row != null) {
                System.out.println("Conexao bem sucedida! Versão: " + row.getString("release_version"));
            } else {
                System.out.println("Erro: Nenhum resultado retornado");
            }
        } catch (Exception e) {
            System.out.println("ERRO NA CONEXÃO: " + e.getMessage());
            e.printStackTrace();
        }
    }
}