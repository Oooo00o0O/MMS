import java.util.ArrayList;

// Stores movie IDs the user plans to watch.
public class Watchlist {
    private final ArrayList<String> items;

    public Watchlist() {
        items = new ArrayList<>();
    }

    public Watchlist(ArrayList<String> existing) {
        items = existing;
    }

    // Add if not already present (case-insensitive).
    public void add(String movieId) {
        String id = movieId.toUpperCase();
        if (!contains(id)) {
            items.add(id);
        }
    }

    // Remove matching ID if it exists.
    public boolean remove(String movieId) {
        String id = movieId.toUpperCase();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).equalsIgnoreCase(id)) {
                items.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean contains(String movieId) {
        String id = movieId.toUpperCase();
        for (String item : items) {
            if (item.equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    // Convert to "id;id;id" format for CSV storage.
    public String toStorageString() {
        if (items.isEmpty()) {
            return "";
        }
        String result = items.get(0);
        for (int i = 1; i < items.size(); i++) {
            result = result + ";" + items.get(i);
        }
        return result;
    }
}
