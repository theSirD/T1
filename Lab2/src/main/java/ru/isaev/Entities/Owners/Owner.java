package ru.isaev.Entities.Owners;

import jakarta.persistence.*;
import ru.isaev.Entities.Cats.Cat;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "OWNER")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private Date birthday;

    @OneToMany
    @JoinTable(
            name = "cats_of_hosts",
            joinColumns = @JoinColumn(name = "host_id"),
            inverseJoinColumns = @JoinColumn(name = "cat_id")
    )
    private Set<Cat> catsList = new HashSet<>();

    public Owner() {
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void addCat(Cat cat, Boolean set) {
        if (cat != null) {
            catsList.add(cat);
        }
        if (set) {
            cat.setOwner(this, false);
        }
    }
}
