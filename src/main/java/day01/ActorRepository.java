package day01;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActorRepository {

    private DataSource dataSource;

    public ActorRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void saveActor(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("insert into actors(actor_name) values(?)")) {
            statement.setString(1, name);
            statement.executeUpdate();

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
}
