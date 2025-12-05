import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Loads and saves users.csv (username,password,watchlist,history).
public class UserStorage {
    public HashMap<String, User> loadUsers(String path) {
        HashMap<String, User> users = new HashMap<String, User>();
        try {
            File file = new File(path);
            if (!file.exists()) {
                System.out.println("User file not found: " + path);
                return users;
            }
            Scanner scanner = new Scanner(file);
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",", -1);  //-1
                if (parts.length < 4) {
                    continue;
                }
                String username = parts[0].trim();
                String password = parts[1].trim();
                ArrayList<String> watchlistItems = parseWatchlist(parts[2]);
                ArrayList<History> historyEntries = parseHistory(parts[3]);
                Watchlist watchlist = new Watchlist(watchlistItems);
                HistoryLog historyLog = new HistoryLog(historyEntries);
                User user = new User(username, password, watchlist, historyLog);
                users.put(username, user);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot read user file: " + e.getMessage());
        }
        return users;
    }

    // Split watchlist string "id;id;id" into a list.
    private ArrayList<String> parseWatchlist(String text) {
        ArrayList<String> list = new ArrayList<String>();
        String trimmed = text.trim();
        if (trimmed.length() == 0) {
            return list;
        }
        String[] parts = trimmed.split(";");
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].trim().length() > 0) {
                list.add(parts[i].trim().toUpperCase());
            }
        }
        return list;
    }

    // Split history string "id@date;id@date" into History records.
    private ArrayList<History> parseHistory(String text) {
        ArrayList<History> list = new ArrayList<History>();
        String trimmed = text.trim();
        if (trimmed.length() == 0) {
            return list;
        }
        String[] parts = trimmed.split(";");
        for (int i = 0; i < parts.length; i++) {
            String entry = parts[i].trim();
            if (entry.length() == 0) {
                continue;
            }
            String[] bits = entry.split("@");
            if (bits.length >= 2) {
                list.add(new History(bits[0].toUpperCase(), bits[1]));
            } else {
                list.add(new History(entry.toUpperCase(), ""));
            }
        }
        return list;
    }

    public boolean saveUsers(HashMap<String, User> users, String path) {
        try {
            PrintWriter writer = new PrintWriter(path);
            writer.println("username,password,watchlist,history");
            for (User user : users.values()) {
                String line = user.getUsername() + "," + user.getPassword() + "," + user.watchlistForStorage() + "," + user.historyForStorage();
                writer.println(line);
            }
            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save users: " + e.getMessage());
            return false;
        }
    }
}
