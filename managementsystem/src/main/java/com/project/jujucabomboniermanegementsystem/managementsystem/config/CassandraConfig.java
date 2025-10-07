package com.project.jujucabomboniermanegementsystem.managementsystem.config;
import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.nio.file.Paths;

//construtor da conexao com banco
@Configuration//indicar que uma classe contém definições de beans
public class CassandraConfig {

@Bean //instrucoes p criar obj, instanciar, configurar e inicializar um objeto que deve ser gerenciado pelo Spring.
public CqlSession cqlSession() {
   return CqlSession.builder()
      .withCloudSecureConnectBundle(Paths.get("src/main/resources/secure-connect-projetojujucacassandra.zip"))
      .withAuthCredentials("tzbdbreAfYGyMcOhoOJyjjKf", "CZrr35w2GNIKcC3jCihj9I3454fiDHULHawjCdRU4TlmY4GQb.GU9NAhDx6Db81JKxLx,IcyJ4.JHMO0xJzgZt1vKl+yfzqmM-ZmAC0Z3HRDs0do3XoqBu3Jc78k9E5E")
      .build();
}
}