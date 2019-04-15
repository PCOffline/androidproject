package com.miretsky.ron.myapp;

public class Player {
    private int id;
    private String name;
    private int score;
    private int place;
    private String image;

    public Player(String name, String image) {
        //this.id =
        this.name = name;
        this.score = 0;
        //this.place =
        this.image = image;
    }

    public Player(int id, String name, int score, int place, String image) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.place = place;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
