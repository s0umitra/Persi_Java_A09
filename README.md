# Persi_Java_A09
## Assignment 09
(Done for the "Core Java" Course provided by the Persistent Systems)

### Project Structure:

![](https://github.com/s0umitra/Persi_Java_A09/,readme/pStructure.png)
Folder "db" contains Movies.txt used to populate the list
Folder "src" contains the source codes
src.RunSystem -> main program
src.Libraries -> contains the functions used by the main program

### Problem Statement:
> 1. Create Class Movies with following data members
> Class Movie (all members should be private)
> Int movieId, 
> String movieName, 
> Category movieType,
> Language language, 
> Date releaseDate, 
> List<String> casting,
> Double rating,
> Double totalBusinessDone

> 2. Read data from movieDetails File 
> List<Movie> populateMovies(File file)

> 3. Store all movie details into database
> Boolean allAllMoviesInDb(List<Movie> movies)

> 4. Add new movie in the list
void addMovie(Movie movie, List<Movie> movie)

> 5. Serialize movie data in the provided file in the given project directory
Void serializeMovies(List<Movie> movies, String fileName)

> 6. Deserialize movies from given files
List<Movie> deserializeMovie(String filename)

> 7. Find the Movies released in entered year
List<Movie> getMoviesReleasedInYear(int year)

> 8. Find the list of movies by actor
List<Movis> getMoviesByActor(String… actorNames)

> 9. Update Movie Rating
void updateRatings(Movie movie, double rating, List<Movie> movies)

> 10. Update Business Done by Movie
void updateBusiness(Movie movie, double amount, List<Movie> movies)

> 11. Find the set of movies as per the review comments done business more than entered amount descending order of amount
Set<Movie> businessDone(double amount)

### Requirements:
- Java Runtime Environment
- MySQL JDBC connector

### License

MIT