package com.example.quizapp;
public class Result {

    private String id;
    private String name;

    private String score;
    public Result(
                    String id,
                    String name,
                    String score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }


    public String getid() {
        return id;
    }
    public void setid(String id) { this.id = id;  }

    public String getname() {
        return name;
    }
    public void setname(String name) { this.name = name;  }


    public String getscore() {
        return score;
    }
    public void setscore(String score) { this.score = score;  }



}