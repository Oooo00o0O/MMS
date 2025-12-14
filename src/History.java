// Represents a single watched movie entry with its date.
public class History {
    private final String movieId;// Unique ID of the watched movie
    private final String watchedDate;// Date the movie was watched (AAAA-BB-CC format)
    //Constructor to create a new watch history entry
    public History(String movieId, String watchedDate) {
        this.movieId = movieId;
        this.watchedDate = watchedDate;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getWatchedDate() {
        return watchedDate;
    }

    // Convert to "id@date" for CSV storage.
    public String toStorageString() {
        return movieId + "@" + watchedDate;
    }
}
