package day05;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ActorsMoviesService {

    private ActorRepository actorRepository;
    private MovieRepository movieRepository;
    private ActorsMoviesRepository actorsMoviesRepository;

    public ActorsMoviesService(ActorRepository actorRepository,
                               MovieRepository movieRepository,
                               ActorsMoviesRepository actorsMoviesRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
        this.actorsMoviesRepository = actorsMoviesRepository;
    }

    public void insertMovieWithActors(String title, LocalDate releaseDate, List<String> actorNames) {
        long movieId = movieRepository.saveMovie(title, releaseDate);
        for (String person : actorNames) {
            long actorId;
            Optional<Actor> found = actorRepository.findActorByName(person);
            if (found.isPresent()) {
                actorId = found.get().getId();
            } else {
                actorId = actorRepository.saveActor(person);
            }
            actorsMoviesRepository.insertActorAndMovie(actorId, movieId);
        }
    }
}
