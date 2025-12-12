// Represents one user with credentials and personal lists.
public class User {
    private final String username;
    private String password;
    private final Watchlist watchlist;
    private final HistoryLog history;

    public User(String username, String password, Watchlist watchlist, HistoryLog history) {
        this.username = username;
        this.password = password;
        this.watchlist = watchlist;
        this.history = history;
    }

    public String getUsername() {
        return username;
    }

    public boolean verifyPassword(String attempt) {
        return password.equals(attempt);
    }

    public void changePassword(String newPassword) {
        password = newPassword;
    }

    public Watchlist getWatchlist() {
        return watchlist;
    }

    public HistoryLog getHistory() {
        return history;
    }

    public void addToWatchlist(String movieId) {
        watchlist.add(movieId);
    }

    public boolean removeFromWatchlist(String movieId) {
        return watchlist.remove(movieId);
    }

    public boolean hasWatched(String movieId) {
        return history.containsMovie(movieId);
    }

    // Add to history and remove from watchlist if present.
    public void markWatched(String movieId, String date) {
        String id = movieId.toUpperCase();
        history.addEntry(id, date);
        if (watchlist.contains(id)) {
            watchlist.remove(id);
        }
    }

    public String watchlistForStorage() {
        return watchlist.toStorageString();
    }

    public String historyForStorage() {
        return history.toStorageString();
    }

    public String getPassword() {
        return password;
    }
}
