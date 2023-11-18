package ru.ievrb.libraryProject.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class Book {

    private int id;

    @Min(value=0, message = "Person id incorrect")
    private Integer personId;
    @NotEmpty(message = "Field cannot be empty.")
    private String name;
    @NotEmpty(message = "Field cannot be empty.")
    private String author;
    @NotEmpty(message = "Field cannot be empty.")
    private int year;
    @Min(value=0, message = "Enter the correct year.")
    @Max(value=2023, message = "Enter the correct year.")
    public Book() {
    }

    public Book(int id, Integer personId, String name, int year) {
        this.id = id;
        this.personId = personId;
        this.name = name;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
