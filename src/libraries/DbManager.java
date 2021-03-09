package libraries;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DbManager {
    static String url = "jdbc:mysql://localhost:3306/jdbc_example";
    static String user = "root";
    static String pass = "se123ed123";
    static Connection con;
    Statement stmt;

    public DbManager() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url, user, pass);
        stmt = con.createStatement();
    }

    public void createTable() throws SQLException {
        String state = "CREATE TABLE movies(movie_id INT," +
                "movie_name varchar(30)," +
                "movie_type varchar(20)," +
                "language varchar(10)," +
                "release_date date," +
                "casts varchar(100)," +
                "rating DOUBLE(3, 2)," +
                "total_business DOUBLE(10, 2))";
        stmt.executeUpdate(state);
    }

    public void addAllToDb(List<Movie> movies) throws SQLException {
        for (Movie m : movies) {
            stmt.executeUpdate(createStatement(m));
        }
        System.out.println("Database initialized with the list!");
    }

    public void addToDb(Movie m) throws SQLException {
        stmt.executeUpdate(createStatement(m));
    }

    public String createStatement(Movie m) {
        Date d = new Date();

        return "INSERT INTO movies values(" +
                m.get_movieId() + ", '" +
                m.get_movieName() + "', '" +
                m.get_movieType().getCategoryName() + "', '" +
                m.get_language().getLanguage() + "', '" +
                d.getStringFromDate(m.get_releaseDate()) + "', '" +
                m.get_casting().toString() + "', " +
                m.get_rating() + ", " +
                m.get_totalBusinessDone() + ");";
    }

    public void initTable() throws SQLException {
        DatabaseMetaData dbm = con.getMetaData();
        ResultSet table = dbm.getTables(null, null, "movies", null);
        if (table.next()) stmt.executeUpdate("DROP TABLE movies");
        createTable();
        table.close();
    }

    public List<Movie> getMoviesReleasedInYear(int year) throws SQLException, ParseException {
        return returnMovies("SELECT * FROM movies WHERE release_date BETWEEN '" + year + "-01-01'" + " AND '"+ (year+1) + "-01-01';");
    }

    public List<Movie> getMoviesByActor(String... actorNames) throws SQLException, ParseException {
        List<Movie> reqMovies = new ArrayList<>();

        for (String c: actorNames) {
            String state = "SELECT * FROM movies WHERE casts LIKE '%" + c + "%';";

            for (Movie m: returnMovies(state)) {
                if (!reqMovies.contains(m)) {
                    reqMovies.add(m);
                }
            }
        }
        return reqMovies;
    }

    public List<Movie> returnMovies(String state) throws SQLException, ParseException {
        List<Movie> reqMovies = new ArrayList<>();
        MovieManager mm = new MovieManager();
        ResultSet rs = stmt.executeQuery(state);

        while (rs.next()) {
            mm.defineMovieFromStrings(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8));

            reqMovies.add(mm.createMovie());
        }
        rs.close();
        return reqMovies;
    }

    public void updateRatings(Movie movie, double rating) throws SQLException {
        stmt.executeUpdate("UPDATE movies SET rating = " + rating + " WHERE movie_id = " + movie.get_movieId());
    }

    public void updateBusiness(Movie movie, double business) throws SQLException {
        stmt.executeUpdate("UPDATE movies SET total_business = " + business + " WHERE movie_id = " + movie.get_movieId());
    }

    public void close() throws SQLException {
        stmt.close();
        con.close();
    }
}
