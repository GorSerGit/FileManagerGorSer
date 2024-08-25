package com.example.mymicroservice.model;

public class MyRequest {
    public String file;
    public String title;
    public String creation_date;
    public String description;
    public int id;

    public String getMessage() {
        return title;
    }

    public void setMessage(String message) {
        this.file = file;
	this.title = title;
	this.description = description;
	this.creation_date = creation_date;
	this.id = id;

    }
}