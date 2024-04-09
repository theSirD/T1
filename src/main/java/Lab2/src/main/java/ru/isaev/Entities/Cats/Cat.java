package ru.isaev.Entities.Cats;

import jakarta.persistence.*;
import ru.isaev.Entities.Owners.Owner;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

//@Getter
//@Setter
@Entity
@Table(name = "CATS")
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date birthday;

    private String breed;

    private CatColors color;


    @ManyToOne(fetch = FetchType.LAZY)
    private Owner owner;
    @ManyToMany
    @JoinTable(
            name = "cat_friends",
            joinColumns = @JoinColumn(name = "cat_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<Cat> friendsList = new HashSet<>();

    public Cat() {
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public CatColors getColor() {
        return color;
    }

    public void setColor(CatColors color) {
        this.color = color;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner, Boolean add) {
        this.owner = owner;
        if (owner != null && add) {
            owner.addCat(this, false);
        }
    }

    public void addFriend(Cat cat, Boolean set) {
        if (cat != null) {
            friendsList.add(cat);
            if (set) {
                cat.addFriend(this, false);
            }
        }
    }
}