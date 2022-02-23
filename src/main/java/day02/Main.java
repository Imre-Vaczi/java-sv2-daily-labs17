package day02;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

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

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        //flyway.clean();
        flyway.migrate();

        ActorRepository actorRepository = new ActorRepository(dataSource);
        //actorRepository.saveActor("Jack Doe");
        //System.out.println(actorRepository.findActor("J"));

        MovieRepository movieRepository = new MovieRepository(dataSource);
        //movieRepository.saveMovie("Titanic", LocalDate.of(1997,01,01));
        List<Movie> movies = movieRepository.findAllMovies();
        System.out.println(movies.toString());
    }
}
