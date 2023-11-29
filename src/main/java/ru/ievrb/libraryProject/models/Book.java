package ru.ievrb.libraryProject.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="person_stored_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date personStoredDate;
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

    @Transient
    private boolean overdue = false;

    @Transient
    private int personId;

    public Book() {
    }

    public Book(int id, int personId, Date personStoredDate, Person holder, String name, int year, boolean overdue) {
        this.id = id;
        this.personId = personId;
        this.personStoredDate = personStoredDate;
        this.holder = holder;
        this.name = name;
        this.year = year;
        this.overdue = overdue;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getPersonStoredDate() {
        return personStoredDate;
    }

    public void setPersonStoredDate(Date personStoredDate) {
        this.personStoredDate = personStoredDate;
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

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }
}
