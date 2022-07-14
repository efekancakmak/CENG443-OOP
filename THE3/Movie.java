import java.util.ArrayList;
import java.util.Arrays;

// It is a class that contains information about Movies.
public class Movie {
    // fields
    String title;
    Short year;
    ArrayList<String> genres;
    Short runtime;
    Double rating;
    Long votes;
    String director;
    ArrayList<String> casts;   
    Double gross;

    // Getters
    public Short getYear() {
        return year;
    }
    public String getDirector() {
        return director;
    }
    public Double getGross() {
        return gross;
    }
    public Short getRuntime() {
        return runtime;
    }

    // Construction with input string.
    // it takes a line from CSV file
    // and fills proper fields.
    Movie(String inp){
        String[] p = inp.split(",");
        title = p[0];
        year = Short.valueOf(p[1]);
        genres = new ArrayList<String>(Arrays.asList(p[2].split(";")));
        runtime = Short.valueOf(p[3]);
        rating = Double.valueOf(p[4]);
        votes = Long.valueOf(p[5]);
        director = p[6];
        casts = new ArrayList<String>(Arrays.asList(p[7].split(";")));
        gross = Double.valueOf(p[8]);
    }
}
