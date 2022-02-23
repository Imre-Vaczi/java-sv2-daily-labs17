package day01;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    private DataSource dataSource;

    public MovieRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void saveMovie(String title, LocalDate releaseDate) {
        try (
                Connection conn = dataSource.getConnection();
                //language=sql
                PreparedStatement ps = conn.prepareStatement(
                        "insert into movies(title, release_date) values(?,?);")
        ) {
            ps.setString(1,title);
            ps.setDate(2, Date.valueOf(releaseDate));
            ps.executeUpdate();

        }
        catch (SQLException sqlException) {
            throw new IllegalStateException("Can not insert.", sqlException);
        }
    }
}
