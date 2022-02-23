package day01;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ActorRepositoryTest {

    ActorRepository actorRepository;

    @BeforeEach
    void init() {
        MariaDbDataSource dataSource = new MariaDbDataSource();
        try {
            dataSource.setUrl("jdbc:mariadb://localhost:3306/movies-actors-test?useUnicode=true");
            dataSource.setUserName("root");
            dataSource.setPassword("f0ssql");
        } catch (SQLException e) {
            throw new IllegalStateException("DB can not be reached!");
        }

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        actorRepository = new ActorRepository(dataSource);
    }

    @Test
    void testInsert() {
        actorRepository.saveActor("Jack Doe");
    }

}