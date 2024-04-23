package ru.isaev.cats.rest.Entities.Cats;

import jakarta.persistence.*;
import ru.isaev.cats.rest.Entities.Owners.Owner;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "CATS")
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO localedate
    private LocalDate birthday;
    //TODO enum. DONE
    private CatBreeds breed;

    private CatColors color;


    @ManyToOne(fetch = FetchType.LAZY)
    private Owner owner;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "cat_friends",
            joinColumns = @JoinColumn(name = "cat_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")

    )
    private Set<Cat> friendsList = new HashSet<>();

    public Cat() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void setFriendsList(Set<Cat> friendsList) {
        this.friendsList = friendsList;
    }

    public Set<Cat> getFriendsList() {
        return friendsList;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public CatBreeds getBreed() {
        return breed;
    }

    public void setBreed(CatBreeds breed) {
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