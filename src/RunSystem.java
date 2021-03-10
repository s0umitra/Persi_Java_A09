import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

import libraries.Date;
import libraries.DbManager;
import libraries.MovieManager;
import libraries.Movie;


public class RunSystem {

    static List<Movie> movies;

    public static void printer(List<Movie> movies) {
        System.out.println("*****************************************************************************************");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        System.out.println("*****************************************************************************************");
    }

    public List<Movie> getMoviesReleasedInYear(int year) {
        List<Movie> reqMovies = new ArrayList<>();
        libraries.Date d = new Date();
        for (Movie m : movies) {
            if (Integer.parseInt(d.getStringFromDate(m.get_releaseDate()).split("-")[0]) == year) {
                reqMovies.add(m);
            }
        }
        return reqMovies;
    }

    public List<Movie> getMoviesByActor(String... actorNames) {
        List<Movie> reqMovies = new ArrayList<>();
        for (Movie m : movies) {
            for (String c : actorNames) {
                if (m.get_casting().contains(c)) {
                    if (!reqMovies.contains(m)) {
                        reqMovies.add(m);
                    }
                }
            }
        }
        return reqMovies;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException, IOException {

        RunSystem rs = new RunSystem();
        rs.Menu();
    }

    public void Menu() throws SQLException, ClassNotFoundException, ParseException, IOException {
        int choice;
        Scanner sc;
        MovieManager mm = new MovieManager();
        DbManager db = new DbManager();
        Movie m;

        System.out.println("**********************************************");
        System.out.println("**  Auto Populating movies from Movies.txt  **");

        String cwd = Paths.get("").toAbsolutePath().toString();
        File file = new File(cwd + "\\db\\Movies.txt");
        movies = mm.populateMovies(file);

        do {
            System.out.print("\n1)  Read Movies from a file\n");
            System.out.print("2)  Export Movies to Database\n");
            System.out.print("3)  Add a new Movie\n");
            System.out.print("4)  Serialize Movies to a file\n");
            System.out.print("5)  Deserialize Movies from a file\n");
            System.out.print("6)  Filter Movies by Release Year\n");
            System.out.print("7)  Filter Movies by Actor[s]\n");
            System.out.print("8)  Update a Movie's Ratings\n");
            System.out.print("9)  Update a Movie's Business Done\n");
            System.out.print("10) Filter Movies by Business Done\n");
            System.out.print("11) Show Movies\n");
            System.out.print("12) Exit\n");

            sc = new Scanner(System.in);
            System.out.print("Choice: ");
            choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Enter file path with extension (enter d for defaults): ");
                String fName = sc.next().toLowerCase();
                if (fName.equals("d")) fName = cwd + "\\db\\Movies.txt";
                movies = mm.readFromFile(fName);
                printer(movies);
            }
            else if (choice == 2) {
                // Export Movies to database
                db.initTable();
                db.addAllToDb(movies);
            }
            else if (choice == 3) {
                int movieId;
                String movieName;
                String movieType;
                String language;
                String releaseDate;
                StringBuilder casting = new StringBuilder();
                double rating, totalBusinessDone;

                System.out.print("Movie ID: ");
                movieId = sc.nextInt();
                System.out.print("Movie Name: ");
                movieName = sc.next();
                System.out.print("Category (Genre): ");
                movieType = sc.next();
                System.out.print("Language: ");
                language = sc.next();
                System.out.print("Release Date (YYYY-MM-DD): ");
                releaseDate = sc.next();
                System.out.print("Number of Casts: ");
                int nCasting = sc.nextInt();
                System.out.print("Cast[s] (First Name[s], Spaced): ");
                for (int i = 0; i < nCasting; i++) {
                    casting.append(sc.next()).append(" ");
                }
                System.out.print("Ratings: ");
                rating = sc.nextDouble();
                System.out.print("Total Business: ");
                totalBusinessDone = sc.nextDouble();

                mm.defineMovieFromStrings(movieId, movieName, movieType, language, releaseDate,
                                        casting.toString(), rating, totalBusinessDone);

                m = mm.createMovie();
                mm.addMovie(m, movies);
                System.out.println("Movie Added!");
                printer(movies);

                System.out.println("Add movie to database? [0/1]: ");
                if (sc.nextInt() == 1) {
                    db.addToDb(m);
                }
            }
            else if (choice == 4) {
                System.out.print("Enter file name with extension (enter d for defaults): ");
                String fName = sc.next().toLowerCase();
                if (fName.equals("d")) fName = "SerializedData.s";
                mm.serializeMovies(movies, fName);
            }
            else if (choice == 5) {
                System.out.print("Enter file name with extension (enter d for defaults): ");
                String fName = sc.next().toLowerCase();
                if (fName.equals("d")) fName = "SerializedData.s";
                movies = mm.deserializeMovie(fName);
            }
            else if (choice == 6) {
                System.out.print("Enter Release Year: ");
                int year = sc.nextInt();
                System.out.print("Look in list or database? [0/1]: ");
                int i = sc.nextInt();

                if (i == 0) {
                    printer(getMoviesReleasedInYear(year));
                } else if (i == 1) {
                    printer(db.getMoviesReleasedInYear(year));
                }
            }
            else if (choice == 7) {
                StringBuilder casting = new StringBuilder();
                System.out.print("Number of Casts: ");
                int nCasting = sc.nextInt();
                System.out.print("Cast[s] (First Name[s], Spaced): ");
                for (int i = 0; i < nCasting; i++) {
                    casting.append(sc.next()).append(" ");
                }
                String[] cast = casting.toString().strip().split(" ");
                System.out.print("Look in list or database? [0/1]: ");
                int i = sc.nextInt();

                if (i == 0) {
                    printer(getMoviesByActor(cast));
                } else if (i == 1) {
                    printer(db.getMoviesByActor(cast));
                }
            }
            else if (choice == 8) {
                printer(movies);
                System.out.print("Enter Movie ID: ");
                int i = sc.nextInt();
                System.out.print("Enter Ratings (<10): ");
                double r = sc.nextDouble();
                Movie mov = mm.getMovieById(i, movies);
                mm.updateRatings(mov, r, movies);
                System.out.print("Ratings updated!\n");
                System.out.println(mov);

                System.out.println("Copy updates to database? [0/1]: ");
                i = sc.nextInt();
                if (i == 1) {
                    db.updateRatings(mov, r);
                }
            }
            else if (choice == 9) {
                printer(movies);
                System.out.print("Enter Movie ID: ");
                int i = sc.nextInt();
                System.out.print("Enter Business Done: ");
                double b = sc.nextDouble();
                Movie mov = mm.getMovieById(i, movies);
                mm.updateBusiness(mov, b, movies);
                System.out.print("Business Done updated!\n");
                System.out.println(mov);

                System.out.println("Copy updates to database? [0/1]: ");
                i = sc.nextInt();
                if (i == 1) {
                    db.updateBusiness(mov, b);
                }
            }
            else if (choice == 10) {
                System.out.print("Enter Business Amount: ");
                double amount = sc.nextDouble();
                Set<Movie> movieSet = mm.businessDone(amount, movies);
                System.out.print("Movies with higher Business Amount:-\n");
                printer(new ArrayList<>(movieSet));
            }
            else if (choice == 11) {
                printer(movies);
            }
            else if (choice == 12) {
                sc.close();
                db.close();
                System.out.println("Closing...");
                System.exit(3);
            }
        } while (true);
    }
}
