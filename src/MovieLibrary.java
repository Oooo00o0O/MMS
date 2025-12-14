import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/*
 #This class loads movie data from a CSV file and stores it in memory
 It supports:
 ## Storing all movies in a list
 ## Fast lookup of movies by their ID using a HashMap
 */

public class MovieLibrary {
    private final ArrayList<Movie> movies;            // List that stores all Movie objects (keeps original order)
    private final HashMap<String, Movie> movieMap;            // Map that allows fast lookup of a Movie by its ID (O(1) access)


// Constructor
    public MovieLibrary() {
        movies = new ArrayList<>();
        movieMap = new HashMap<>();
    }
    
// Loads movie data from a CSV file.
    public boolean loadFromFile(String path) {
        try {
            File file = new File(path);
            Scanner input = new Scanner(file);
            if (input.hasNextLine()) {    // skip the first line which is id,title,genre,year,rating
                input.nextLine();
            }
    // Read file line by line
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] parts = line.split(",");    // Split CSV line into fields
                if (parts.length < 5) {
                    continue;
                }
    // Extract and clean individual fields
                String id = parts[0].trim().toUpperCase();
                String title = parts[1].trim();
                String genre = parts[2].trim();
                int year = safeParseInt(parts[3].trim());
                double rating = safeParseDouble(parts[4].trim());
                Movie movie = new Movie(id, title, genre, year, rating);    // Create a Movie object from parsed data
    // Add movie to both list and map
                movies.add(movie);
                movieMap.put(id, movie);
            }
            input.close();
            return true;
        } catch (FileNotFoundException e) {    // Handle missing file error
            System.out.println("Movies file not found: " + path);
            return false;
        }
    }
    // Prevent the program from crashing when encountering invalid or missing data by returning default values.
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
    
// Corresponding functions to view all movie
    public ArrayList<Movie> getAllMovies() {
        return movies;
    }
    
// Quickly check movies by id
    public Movie getMovieById(String id) {
        String key = id.toUpperCase();
        if (movieMap.containsKey(key)) {
            return movieMap.get(key);
        }
        return null;
    }
}
