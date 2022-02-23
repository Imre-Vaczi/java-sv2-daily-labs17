package day05;

import java.util.Arrays;
import java.util.Optional;

public class MoviesRatingsService {

    private MovieRepository movieRepository;
    private RatingsRepository ratingsRepository;

    public MoviesRatingsService(MovieRepository movieRepository, RatingsRepository ratingsRepository) {
        this.movieRepository = movieRepository;
        this.ratingsRepository = ratingsRepository;
    }

    public void addRatings(String title, Integer ... ratings) {
        Optional<Movie> actual = movieRepository.findMovieByTitle(title);
        if (actual.isPresent()) {
            ratingsRepository.insertRating(actual.get().getId(), Arrays.asList(ratings));
        } else {
            throw new IllegalStateException("can not find movie.");
        }
    }
}
