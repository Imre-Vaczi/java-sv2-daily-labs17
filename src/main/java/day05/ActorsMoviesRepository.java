package day05;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActorsMoviesRepository {

    private DataSource dataSource;

    public ActorsMoviesRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertActorAndMovie(long actor_id, long movie_id) {
        try (
                Connection conn = dataSource.getConnection();
                //language=sql
                PreparedStatement ps = conn.prepareStatement("insert into actors_movies(actor_id, movie_id) values (?,?);")
        ) {
            ps.setLong(1, actor_id);
            ps.setLong(2, movie_id);
            ps.executeUpdate();
        }
        catch (SQLException sqlException) {
            throw new IllegalStateException("Can not insert.", sqlException);
        }
    }


}
