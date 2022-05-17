package com.example.mypoetry.modelclass;

public class poetry_class {
    int id;
    String poetry;
    String poet_name;
    String date;

    public poetry_class(int id, String poetry, String poet_name, String date) {
        this.id = id;
        this.poetry = poetry;
        this.poet_name = poet_name;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoetry() {
        return poetry;
    }

    public void setPoetry(String poetry) {
        this.poetry = poetry;
    }

    public String getPoet_name() {
        return poet_name;
    }

    public void setPoet_name(String poet_name) {
        this.poet_name = poet_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
