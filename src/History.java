// Represents a single watched movie entry with its date.
public class History {
    private String movieId;
    private String watchedDate;

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
