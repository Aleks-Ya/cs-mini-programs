import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        Class.forName("org.postgresql.Driver");

        executeSql("set",
                "/home/alexx/server/SetRetail10_Server/RetailX/DB/set/create_tables_set.sql",
                "/home/alexx/server/SetRetail10_Server/RetailX/DB/set/init_tables_set.sql",
                "/home/alexx/server/SetRetail10_Server/RetailX/DB/set/init_tables_set_centrum.sql"
        );

        executeSql("set_operday",
                "/home/alexx/server/SetRetail10_Server/RetailX/DB/set_operday/create_tables_set_operday.sql",
                "/home/alexx/server/SetRetail10_Server/RetailX/DB/set_operday/init_tables_set_operday.sql"
        );

        executeSql("set_loyal",
                "/home/alexx/server/SetRetail10_Server/RetailX/DB/set_loyal/create_tables_set_loyal.sql",
                "/home/alexx/server/SetRetail10_Server/RetailX/DB/set_loyal/init_tables_set_loyal.sql"
        );
    }

    private static void executeSql(String database, String... sqlFiles) throws SQLException, IOException {
        try {
            System.out.println("Process DB: " + database);
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + database, "postgres", "postgres");
            Statement statement = conn.createStatement();

            for (String file : sqlFiles) {
                System.out.println("Process file: " + file);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder query = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    query.append(line);
                    query.append("\n");
                }
                reader.close();

                String queryStr = query.toString().replace("\uFEFF", "");
                statement.executeUpdate(queryStr);
            }

            conn.close();
            System.out.println();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.out);
        }
    }
}