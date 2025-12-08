import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Loads movie data from CSV and provides lookup by id.
public class MovieLibrary {
    private final ArrayList<Movie> movies;
    private final HashMap<String, Movie> movieMap;

    public MovieLibrary() {
        movies = new ArrayList<>();
        movieMap = new HashMap<>();
    }

    public boolean loadFromFile(String path) {
        try {
            File file = new File(path);
            Scanner input = new Scanner(file);
            if (input.hasNextLine()) {    //skip the first line which is id,title,genre,year,rating
                input.nextLine();
            }
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] parts = line.split(",");
                if (parts.length < 5) {
                    continue;
                }
                String id = parts[0].trim().toUpperCase();
                String title = parts[1].trim();
                String genre = parts[2].trim();
                int year = safeParseInt(parts[3].trim());
                double rating = safeParseDouble(parts[4].trim());
                Movie movie = new Movie(id, title, genre, year, rating);
                movies.add(movie);
                movieMap.put(id, movie);
            }
            input.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Movies file not found: " + path);
            return false;
        }
    }

    private int safeParseInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private double safeParseDouble(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public ArrayList<Movie> getAllMovies() {
        return movies;
    }

    public Movie getMovieById(String id) {
        String key = id.toUpperCase();
        if (movieMap.containsKey(key)) {
            return movieMap.get(key);
        }
        return null;
    }
}
