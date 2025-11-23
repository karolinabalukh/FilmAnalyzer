package org.project.Entity;

public class Film {
    private String title;
    private int year;
    private int duration;
    private String genre;
    private double rating;
    private String description;
    private Director director;

    public Film() {}

    public Film(String title, int year, int duration, String genre,
                double rating, String description, Director director) {

        this.title = title;
        this.year = year;
        this.duration = duration;
        this.genre = genre;
        this.rating = rating;
        this.description = description;
        this.director = director;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }
}
