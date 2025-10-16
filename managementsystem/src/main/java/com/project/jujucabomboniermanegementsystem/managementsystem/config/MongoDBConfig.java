package com.project.jujucabomboniermanegementsystem.managementsystem.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
//spring cria coenxao automaticamente quando coloco infos no application properties
public class MongoDBConfig implements CommandLineRunner{
    //Spring INJETA o MongoClient automaticamente
    private final MongoClient mongoClient;

    public MongoDBConfig(MongoClient mongoClient) {
        this.mongoClient = mongoClient;// Injeção de dependência
    }

    @Override
    public void run(String... args) throws Exception {
        try {
             // Spring já configurou tudo via application.properties, só precisamos testar a conexão
            MongoDatabase database = mongoClient.getDatabase("ProjetoJujuca");
            database.runCommand(new Document("ping", 1));
            System.out.println(" CONEXÃO COM MONGODB ESTABELECIDA COM SUCESSO!");
            System.out.println(" Cluster: ClusterBDAvancadoFEI");
            System.out.println(" Database: ProjetoJujuca");
            
        } catch (Exception e) {
            System.err.println("ERRO MONGODB: " + e.getMessage());
        }
    }
}

//spring: cria mongocliente automaticamente, configurura td q precisa no application properties, gerencia automaticamente usa @Component + CommandLineRunner

/*conexao oferecida pelo mongo db
 * public class MongoClientConnectionExample {
    public static void main(String[] args) {
        String connectionString = "mongodb+srv://<db_username>:<db_password>@clusterbdavancadofei.dueievt.mongodb.net/?retryWrites=true&w=majority&appName=ClusterBDAvancadoFEI";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Send a ping to confirm a successful connection
                MongoDatabase database = mongoClient.getDatabase("admin");
                database.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }
}
 */