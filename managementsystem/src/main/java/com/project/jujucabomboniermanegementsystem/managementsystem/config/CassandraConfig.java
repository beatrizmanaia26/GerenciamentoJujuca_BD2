import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import java.nio.file.Paths;

public class ConnectDatabase {

   public static void main(String[] args) {
         try (CqlSession session = CqlSession.builder()
            .withCloudSecureConnectBundle(Paths.get("src/main/resources/secure-connect-projetojujucacassandra.zip"))
            .withAuthCredentials("tzbdbreAfYGyMcOhoOJyjjKf","CZrr35w2GNIKcC3jCihj9I3454fiDHULHawjCdRU4TlmY4GQb.GU9NAhDx6Db81JKxLx,IcyJ4.JHMO0xJzgZt1vKl+yfzqmM-ZmAC0Z3HRDs0do3XoqBu3Jc78k9E5E")
            .build()) {
            // Select the release_version from the system.local table:
            ResultSet rs = session.execute("select release_version from system.local");
            Row row = rs.one();
            //Print the results of the CQL query to the console:
            if (row != null) {
               System.out.println(row.getString("release_version"));
            } else {
               System.out.println("An error occurred.");
            }
         }
         System.exit(0);
   }
}