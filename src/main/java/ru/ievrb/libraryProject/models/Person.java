package ru.ievrb.libraryProject.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name="person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @NotEmpty(message = "Field cannot be empty.")
    @Column(name="firstname")
    private String firstName;
    @NotEmpty(message = "Field cannot be empty.")
    @Column(name="lastname")
    private String lastName;
    @NotEmpty(message = "Field cannot be empty.")
    @Column(name="middlename")
    private String middleName;

    @Min(value=1900, message = "Enter the correct age of the visitor.")
    @Max(value=2009, message = "Enter the correct age of the visitor.")
    @Column(name="birthyear")
    private int birthYear;

    @OneToMany(mappedBy = "holder")
    private List<Book> books;

    public Person() {
    }

    public Person(int id, String firstName, String lastName, String middleName, int birthYear) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthYear = birthYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}
