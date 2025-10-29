package com.project.jujucabomboniermanegementsystem.managementsystem.config;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.annotation.PostConstruct;

@Component
public class DatabaseTester {
    
    private final CqlSession session;
    
    @Autowired
    public DatabaseTester(CqlSession session) {
        this.session = session;
    }
    
    @PostConstruct
    public void testConnection() {
        if (session == null) {
            System.out.println("⚠️ Cassandra não disponível");
            return;
        }
        
        try {
            System.out.println("🧪 Testando conexão Cassandra...");
            ResultSet rs = session.execute("select release_version from system.local");
            Row row = rs.one();
            
            if (row != null) {
                System.out.println("✅ Cassandra OK - Versão: " + row.getString("release_version"));
            }
        } catch (Exception e) {
            System.out.println("❌ Falha no teste: " + e.getMessage());
        }
    }
}