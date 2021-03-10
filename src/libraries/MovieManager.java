package libraries;

import java.io.*;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;


public class MovieManager implements java.io.Serializable {
    int movieId;
    String movieName;
    String movieType;
    String language;
    String releaseDate;
    List<String> casting;
    Double rating, totalBusinessDone;

    public void extractData(String[] data) {

        this.movieId = Integer.parseInt(data[0]);
        this.movieName = data[1].strip();
        this.movieType = data[2].strip();
        this.language = data[3].strip().toLowerCase();
        this.releaseDate = data[4].strip();
        this.casting = Arrays.asList(data[5].strip().split(" "));
        this.rating = Double.parseDouble(data[6]);
        this.totalBusinessDone = Double.parseDouble(data[7]);
    }

    public void defineMovieFromStrings(int movieId, String movieName, String movieType, String language, String releaseDate, String casting, Double rating, Double totalBusinessDone) {

        this.movieId = movieId;
        this.movieName = movieName.strip();
        this.movieType = movieType.strip();
        this.language = language.strip().toLowerCase();
        this.releaseDate = releaseDate;
        this.casting = Arrays.asList(casting.strip().split(" "));
        this.rating = rating;
        this.totalBusinessDone = totalBusinessDone;
    }

    public void addMovie(Movie movie, List<Movie> movies) {
        movies.add(movie);
    }

    public Movie createMovie() throws ParseException {

        Category c = new Category();
        Language l = new Language();
        Date date = new Date();

        c.setCategoryName(movieType);
        l.setLanguage(language);

        return new Movie(movieId, movieName, c, l, date.getDateFromString(releaseDate), casting, rating, totalBusinessDone);
    }

    public List<Movie> readFromFile(String path) {
        File file = new File(path);
        System.out.println("Read movies from " + path);
        return readFile(file);
    }

    public List<Movie> readFile(File file) {
        String[] line;
        ArrayList<Movie> movies = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                line = sc.nextLine().split(",");
                extractData(line);
                addMovie(createMovie(), movies);
            }
            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    public List<Movie> populateMovies(File file) {
        return readFile(file);
    }

    public void serializeMovies(List<Movie> movies, String fileName) throws IOException {

        String cwd = Paths.get("").toAbsolutePath().toString();
        FileOutputStream fos = new FileOutputStream(cwd + "\\" + fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(movies);
        oos.flush();
        oos.close();

        System.out.println("Movies Serialized to: " + fileName);
    }

    public List<Movie> deserializeMovie(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream ois =new ObjectInputStream(fis);

        return (List<Movie>) ois.readObject();
    }

    public Movie getMovieById(int id, List<Movie> movies) {
        for (Movie m : movies) {
            if (m.get_movieId() == id) {
                return m;
            }
        }
        return null;
    }

    public void updateRatings(Movie movie, double rating, List<Movie> movies) {
        for (Movie m : movies) {
            if (m == movie) {
                m.set_rating(rating);
            }
        }
    }

    public void updateBusiness(Movie movie, double amount, List<Movie> movies) {
        for (Movie m : movies) {
            if (m == movie) {
                m.set_totalBusinessDone(amount);
            }
        }
    }

    public Set<Movie> businessDone(double amount, List<Movie> m) {

        CustomComparator cc = new CustomComparator();
        Set<Movie> movieSet = new TreeSet<>(cc);
        for (Movie i: m) {
            if (i.get_totalBusinessDone() > amount) {
                movieSet.add(i);
            }
        }
        return movieSet;
    }
}

class CustomComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie o1, Movie o2) {
        return o2.get_totalBusinessDone().compareTo(o1.get_totalBusinessDone());
    }
}
