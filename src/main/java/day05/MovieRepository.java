package day05;

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

    public Long saveMovie(String title, LocalDate releaseDate) {
        try (
                Connection conn = dataSource.getConnection();
                //language=sql
                PreparedStatement ps = conn.prepareStatement(
                        "insert into movies(title, release_date) values(?,?);",
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1,title);
            ps.setDate(2, Date.valueOf(releaseDate));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong("id");
                }
                throw new IllegalStateException("Can not insert.");
            }
        }
        catch (SQLException sqlException) {
            throw new IllegalStateException("Can not insert.", sqlException);
        }
    }

    public List<Movie> findAllMovies() {
        try (
                Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement();
                //language=sql
                ResultSet rs = stmt.executeQuery("select id, title, release_date from movies;")
        ) {
           List<Movie> movies = new ArrayList<>();
           while (rs.next()) {
               populateMoviesList(rs, movies);
           }
           return movies;
        }
        catch (SQLException sqlException) {
            throw new IllegalStateException("Can not query.", sqlException);
        }
    }

    private void populateMoviesList(ResultSet rs, List<Movie> movies) throws SQLException {
        Long tmpID = rs.getLong("id");
        String tmpTitle = rs.getString("title");
        LocalDate tmpRelease_date = rs.getDate("release_date").toLocalDate();
        movies.add(new Movie(tmpID, tmpTitle, tmpRelease_date));
    }
}
