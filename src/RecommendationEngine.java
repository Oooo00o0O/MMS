import java.util.ArrayList;

// Builds recommendation lists with genre filter and sort modes.
public class RecommendationEngine {
    public static final String MODE_RATING_DESC = "rating_desc";// Highest rating first
    public static final String MODE_RATING_ASC = "rating_asc";// Lowest rating first
    public static final String MODE_YEAR_DESC = "year_desc";// Latest movies first
    public static final String MODE_YEAR_ASC = "year_asc";// Oldest movies first
    public static final String MODE_RANDOM = "random";//Random

    // Collect distinct genres from all movies, sorted alphabetically, used in Main.
    public ArrayList<String> listGenres(MovieLibrary library) {
        ArrayList<String> genres = new ArrayList<>();
        ArrayList<Movie> movies = library.getAllMovies();
        for (Movie movie : movies) {
            String genre = movie.getGenre();
            if (!containsIgnoreCase(genres, genre)) {
                genres.add(genre);
            }
        }
        sortStrings(genres); //alphabetical
        return genres;
    }

    // Main entry: filter by genre (or ALL), exclude watched/watchlist, then sort.
    public ArrayList<Movie> recommend(User user, MovieLibrary library, String genreFilter, String sortMode, int n) {
        boolean allGenres = genreFilter == null || genreFilter.isEmpty();
        ArrayList<Movie> candidates = new ArrayList<>();
        ArrayList<Movie> all = library.getAllMovies();
        for (Movie movie : all) {
            if (!allGenres && !movie.getGenre().equalsIgnoreCase(genreFilter)) {
                continue;
            }
            if (!isExcluded(user, movie.getId())) {
                candidates.add(movie);
            }
        }

        if (candidates.isEmpty()) {
            return candidates;
        }

        sortMovies(candidates, sortMode);

        ArrayList<Movie> result = new ArrayList<>();   //top-n
        for (int i = 0; i < candidates.size() && result.size() < n; i++) {
            result.add(candidates.get(i));
        }
        return result;
    }

    // Selection sort on movies using the chosen comparison.
    private void sortMovies(ArrayList<Movie> movies, String mode) {
        for (int i = 0; i < movies.size(); i++) {
            int targetIndex = i;
            for (int j = i + 1; j < movies.size(); j++) {
                if (better(movies.get(j), movies.get(targetIndex), mode)) {
                    targetIndex = j;
                }
            }
            Movie temp = movies.get(i);
            movies.set(i, movies.get(targetIndex));
            movies.set(targetIndex, temp);
        }

        if (mode.equals(MODE_RANDOM)) {
            shuffleMovies(movies);
        }
    }

    // Decide if "current" should come before "target" under the given mode.
    private boolean better(Movie current, Movie target, String mode) {
        switch (mode) {
            case MODE_RATING_ASC -> {
                return current.getRating() < target.getRating();
            }
            case MODE_YEAR_DESC -> {
                if (current.getYear() > target.getYear()) {
                    return true;
                }
                if (current.getYear() == target.getYear()) {
                    return current.getRating() > target.getRating();
                }
                return false;
            }
            case MODE_YEAR_ASC -> {
                if (current.getYear() < target.getYear()) {
                    return true;
                }
                if (current.getYear() == target.getYear()) {
                    return current.getRating() > target.getRating();
                }
                return false;
            }
            case MODE_RANDOM -> {
                return false;
            }
        }
        return current.getRating() > target.getRating();
    }

    // Fisher-Yates shuffle for random order.
    private void shuffleMovies(ArrayList<Movie> movies) {
        for (int i = movies.size() - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            Movie temp = movies.get(i);
            movies.set(i, movies.get(j));
            movies.set(j, temp);
        }
    }

    // Selection sort for strings (case-insensitive).
    private void sortStrings(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            int best = i;
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j).compareToIgnoreCase(list.get(best)) < 0) {
                    best = j;
                }
            }
            String temp = list.get(i);
            list.set(i, list.get(best));
            list.set(best, temp);
        }
    }

    private boolean containsIgnoreCase(ArrayList<String> list, String text) {
        for (String s : list) {
            if (s.equalsIgnoreCase(text)) {
                return true;
            }
        }
        return false;
    }

    private boolean isExcluded(User user, String movieId) {
        if (user.getWatchlist().contains(movieId)) {
            return true;
        }
        return user.hasWatched(movieId);
    }
}
