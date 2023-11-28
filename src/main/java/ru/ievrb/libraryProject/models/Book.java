package ru.ievrb.libraryProject.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;



    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person holder;
    @NotEmpty(message = "Field cannot be empty.")
    @Column(name="name")
    private String name;
    @NotEmpty(message = "Field cannot be empty.")
    @Column(name="author")
    private String author;
    @Min(value=0, message = "Enter the correct year.")
    @Max(value=2023, message = "Enter the correct year.")
    @Column(name="year")
    private int year;

    public Book() {
    }

    public Book(int id, Person holder, String name, int year) {
        this.id = id;
        this.holder = holder;
        this.name = name;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getHolder() {
        return holder;
    }

    public void setHolder(Person holder) {
        this.holder = holder;
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
