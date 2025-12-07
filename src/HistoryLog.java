import java.util.ArrayList;

// Stores all watched records for one user.
public class HistoryLog {
    private final ArrayList<History> entries;

    public HistoryLog() {
        entries = new ArrayList<>();
    }

    public HistoryLog(ArrayList<History> existing) {
        entries = existing;
    }

    public void addEntry(String movieId, String date) {
        String id = movieId.toUpperCase();
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getMovieId().equalsIgnoreCase(id)) {
                entries.set(i, new History(id, date));
                return;
            }
        }
        entries.add(new History(id, date));
    }

    public boolean containsMovie(String movieId) {
        String id = movieId.toUpperCase();
        for (History entry : entries) {
            if (entry.getMovieId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<History> getEntries() {
        return entries;
    }

    public String toStorageString() {
        if (entries.isEmpty()) {
            return "";
        }
        String result = entries.get(0).toStorageString();
        for (int i = 1; i < entries.size(); i++) {
            result = result + ";" + entries.get(i).toStorageString();
        }
        return result;
    }
}
