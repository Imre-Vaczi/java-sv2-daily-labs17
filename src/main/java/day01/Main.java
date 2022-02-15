package day01;

import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        MariaDbDataSource dataSource = new MariaDbDataSource();
        try {
            dataSource.setUrl("jdbc:mariadb://localhost:3306/movies-actors?useUnicode=true");
            dataSource.setUserName("root");
            dataSource.setPassword("f0ssql");
        } catch (SQLException e) {
            throw new IllegalStateException("DB can not be reached!");
        }

        /*try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement();) {

            statement.executeUpdate("insert into actors(actor_name)values ('John Doe')");
        } catch (SQLException exception) {
            throw new IllegalStateException("DB can not be reached!");
        }*/

        ActorRepository actorRepository = new ActorRepository(dataSource);
        //actorRepository.saveActor("Jack Doe");

        System.out.println(actorRepository.findActor("Jo"));
    }
}
