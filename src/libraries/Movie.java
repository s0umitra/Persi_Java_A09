package libraries;

import java.util.List;


public class Movie {

    private int movieId;
    private String movieName;
    private Category movieType;
    private Language language;
    private java.util.Date releaseDate;
    private List<String> casting;
    private Double rating, totalBusinessDone;

    public Movie(int movieId, String movieName, Category movieType, Language language, java.util.Date releaseDate, List<String> casting, Double rating, Double totalBusinessDone) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieType = movieType;
        this.language = language;
        this.releaseDate = releaseDate;
        this.casting = casting;
        this.rating = rating;
        this.totalBusinessDone = totalBusinessDone;
    }

    public String toString() {
        Date date = new Date();
        StringBuilder tempCasting = new StringBuilder();

        for (String s : casting) {
            tempCasting.append(" ").append(s);
        }

		return movieId + ", " +
                movieName + ", " +
                movieType.getCategoryName() + ", " +
                language.getLanguage() + ", " +
                date.getStringFromDate(releaseDate) + "," +
                tempCasting + ", " +
                rating + ", " +
                totalBusinessDone;
	}

    public int get_movieId() {
        return this.movieId;
    }

    public void set_movieId(int movieId) {
        this.movieId = movieId;
    }

    public String get_movieName() {
        return this.movieName;
    }

    public void set_movieName(String movieName) {
        this.movieName = movieName;
    }

    public Category get_movieType() {
        return this.movieType;
    }

    public void set_movieType(Category movieType) {
        this.movieType = movieType;
    }

    public Language get_language() {
        return this.language;
    }

    public void set_language(Language language) {
        this.language = language;
    }

    public java.util.Date get_releaseDate() {
        return this.releaseDate;
    }

    public void set_releaseDate(java.util.Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> get_casting() {
        return this.casting;
    }

    public void set_casting(List<String> casting) {
        this.casting = casting;
    }

    public Double get_rating() {
        return this.rating;
    }

    public void set_rating(Double rating) {
        this.rating = Math.round(rating * 100.0) / 100.0;
    }
    
    public Double get_totalBusinessDone() {
        return this.totalBusinessDone;
    }

    public void set_totalBusinessDone(Double totalBusinessDone) {
        this.totalBusinessDone = Math.round(totalBusinessDone * 100.0) / 100.0;
    }

}