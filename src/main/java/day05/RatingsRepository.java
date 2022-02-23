package day05;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RatingsRepository {

    private DataSource dataSource;

    public RatingsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertRating(long movieID, List<Integer> ratings) {
        try (Connection conn = dataSource.getConnection())
         {
            conn.setAutoCommit(false);
            //language=sql
            try (PreparedStatement ps = conn.prepareStatement("insert into ratings(movie_id, rating) values(?,?)")) {
                for (Integer actual : ratings) {
                    if (actual < 1 || actual > 5) {
                        throw new IllegalArgumentException();
                    }
                    ps.setLong(1,movieID);
                    ps.setLong(2, actual);
                    ps.executeUpdate();
                }
                conn.commit();
            }
            catch (IllegalArgumentException iae) {
                conn.rollback();
            }
        }
        catch (SQLException sqlException) {
            throw new IllegalStateException("Can not insert.", sqlException);
        }
    }
}
