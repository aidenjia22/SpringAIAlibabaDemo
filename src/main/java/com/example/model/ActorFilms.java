package com.example.model;

public class ActorFilms {
    private String filmName;
    private String description;

    public ActorFilms() {}

    public ActorFilms(String filmName, String description) {
        this.filmName = filmName;
        this.description = description;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

