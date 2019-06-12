package com.project.platform.game;

public class Player {
    private int id;
    private String username;
    private int score;
    private String password;

    Player(String username, String password) {
        this.id = -1;
        this.username = username;
        this.score = 0;
        this.password = password;
    }

    Player(int id, String username, String password, int score) {
        this.id = id;
        this.username = username;
        this.score = score;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void add() {
        DatabaseManager.insert(this);
        this.id = DatabaseManager.get(DatabaseManager.getIndexOf(this)).id;
    }
}
