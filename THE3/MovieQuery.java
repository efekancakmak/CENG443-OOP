import java.io.FileWriter;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

class MovieQuery {

    public static void queryAll(String inFileName, String outFileName) throws Exception{
        // Read CSV file and save all information into Movie list.
        List<Movie> movies = Files.lines(Paths.get(inFileName + ".csv"))
                                .skip(1) // first line contains field names
                                .map(inp -> new Movie(inp)) // construct Movie from a CSV line 
                                .collect(Collectors.toList()); // convert stream to List.
        
        // query1: Titles of all the movies.
        String out1 = movies.stream()
            .map(m -> m.title) // get rid of all information instead of title
            .map(String::toUpperCase)
            .sorted()
            .distinct() // get rid of duplicates
            .collect(Collectors.joining("\n")); // printing issue
        FileWriter toFile = new FileWriter(outFileName + "-1.out");
        toFile.write(out1);
        toFile.close();
        
        // query2: Directors who directed a movie with a rating of at least 8.5.
        String out2 = movies.stream()
                        .filter(m -> m.rating >= 8.5) // select successful Directors...
                        .map(m -> m.director) // get rid of unnecesarry fields
                        .map(String::toUpperCase)
                        .distinct() // get rid of duplicates
                        .sorted()
                        .collect(Collectors.joining("\n")); // printing issue
        toFile = new FileWriter(outFileName + "-2.out");
        toFile.write(out2);
        toFile.close();

        // query3: Director who made an Adventure movie with the least known gross revenue.
        Movie out3 = movies.stream()
                        .filter(m -> !m.gross.equals(-1.0) && m.genres.contains("Adventure"))
                        .min(Comparator.comparing(Movie::getGross))
                        .get();
        toFile = new FileWriter(outFileName + "-3.out");
        toFile.write(out3.director.toUpperCase());
        toFile.close();
        
        // query4: Directors who have directed both a successful 
        // movie with a rating of at least 8.5 and also 
        // a moderate movie with a rating of at most 8.
        List<String> ms1 = movies.stream()
                                .filter(m -> m.rating >= 8.5)
                                .map(Movie::getDirector) // save successful director names
                                .collect(Collectors.toList());
        String out4 = movies.stream()
            .filter(m -> m.rating <= 8.0) // get more unsuccessful directors
            .filter(m -> ms1.contains(m.director)) // check if he was also successful?
            .map(Movie::getDirector)
            .map(String::toUpperCase)
            .sorted()
            .distinct() // get rid of duplicates
            .collect(Collectors.joining("\n"));
        toFile = new FileWriter(outFileName + "-4.out");
        toFile.write(out4);
        toFile.close();

        // query5: Sum of the duration of the movies directed by the director of the earliest movie in the list.
        String thedirector = movies.stream()
                                .min(Comparator.comparing(Movie::getYear)) // find the earlist movie
                                .map(m -> m.director).get(); // save director name to use
        Long out5 = movies.stream()
            .filter(m -> m.director.equals(thedirector)) // Only THE director's movies.
            .mapToLong(Movie::getRuntime) // Only run time is needed.
            .sum(); // sum them all
        toFile = new FileWriter(outFileName + "-5.out");
        toFile.write(out5.toString());
        toFile.close();
        return;
    }
}