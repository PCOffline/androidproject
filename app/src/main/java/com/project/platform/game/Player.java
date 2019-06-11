package com.project.platform.game;

public class Player {
    private int id;
    private String name;
    private int score;
    private String image;

    Player(String name, String image) {
        this.id = -1;
        this.name = name;
        this.score = 0;
        this.image = image;
    }

    Player(int id, String name, int score, String image) {
        this.id = id;
        this.name = name;
        this.score = score;
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

    public void add() {
        DatabaseManager.insert(this);
        this.id = DatabaseManager.get(DatabaseManager.getIndexOf(this)).id;
    }
}
