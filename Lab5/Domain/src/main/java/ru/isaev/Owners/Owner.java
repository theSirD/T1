package ru.isaev.Owners;

import jakarta.persistence.*;
import ru.isaev.Cats.Cat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "OWNER")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String first_name;

    private String last_name;

    //TODO localedate. DONE
    private LocalDate birthday;

    private String password;

    private Roles role;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "cats_of_hosts",
            joinColumns = @JoinColumn(name = "host_id"),
            inverseJoinColumns = @JoinColumn(name = "cat_id")
    )
    private List<Cat> catsList = new ArrayList<>();

    public Owner() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public List<Cat> getCatsList() {
        return catsList;
    }

    public void setCatsList(List<Cat> catsList) {
        this.catsList = catsList;
    }

    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    public void setLastName(String lastName) {
        this.last_name = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
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
