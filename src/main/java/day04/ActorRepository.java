package day04;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActorRepository {

    private DataSource dataSource;

    public ActorRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Long saveActor(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("insert into actors(actor_name) values(?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong("id");
                }
                throw new IllegalStateException("Can not insert.");
            }

        } catch (SQLException sqlException) {
            throw new IllegalStateException("Cannot update: " + name, sqlException);
        }
    }

    public List<String> findActor(String prefix) {
        List<String> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select actor_name from actors where actor_name like ?")) {

            preparedStatement.setString(1, prefix + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String actorName = resultSet.getString("actor_name");
                    result.add(actorName);
                }
            }

        } catch (SQLException sqlException) {
            throw new IllegalStateException("Can not reach DB", sqlException);
        }
        return result;
    }

    public Optional<Actor> findActorByName(String name) {
        try (
                Connection conn = dataSource.getConnection();
                //language=sql
                PreparedStatement ps = conn.prepareStatement("select * from actors where actor_name = ?;");
        ) {
            ps.setString(1, name);
            return processSelectStatement(ps);
        }
        catch (SQLException sqlException) {
            throw new IllegalStateException("Can not query.", sqlException);
        }
    }

    private Optional<Actor> processSelectStatement(PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return Optional.of(new Actor(rs.getLong("id"), rs.getString("actor_name")));
            } else {
                return Optional.empty();
            }
        }
    }
}
