package ru.isaev;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.isaev.DAO.CatDAO;
import ru.isaev.DAO.OwnerDAO;
import ru.isaev.Entities.Cats.Cat;
import ru.isaev.Entities.Cats.CatColors;
import ru.isaev.Entities.Owners.Owner;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class Domain {
    public static void main(String[] args)  {
        SessionFactory sessionFactory;
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        OwnerDAO ownerService = new OwnerDAO(sessionFactory);
        CatDAO catService = new CatDAO(sessionFactory);

        Calendar calendar = Calendar.getInstance();

        // cat 1
        calendar.set(2000, Calendar.SEPTEMBER, 9);
        Cat cat1 = new Cat();
        cat1.setBreed("breed1");
        cat1.setColor(CatColors.Red);
        cat1.setBirthday(new Date(calendar.getTime().getTime()));

        catService.add(cat1);


        // cat 2
        calendar.set(2010, Calendar.DECEMBER, 31);
        Cat cat2 = new Cat();
        cat2.setBreed("breed2");
        cat2.setColor(CatColors.Black);
        cat2.setBirthday(new Date(calendar.getTime().getTime()));

        catService.add(cat2);

        // make cats to be friends
        cat1.addFriend(cat2, true);
        catService.update(cat1);
        catService.update(cat2);


        // owner 1
        calendar.set(1939, Calendar.MAY, 1);
        Owner owner = new Owner();
        owner.setFirstName("Daniel");
        owner.setLastName("Isaev");
        owner.setBirthday(new Date(calendar.getTime().getTime()));
        owner.addCat(cat1, true);
        owner.addCat(cat2, true);

        ownerService.add(owner);
        catService.update(cat1);
        catService.update(cat2);



        Cat cat = catService.getById(1L);

        List<Cat> availableCats = catService.getAll();

//        catService.remove(cat1);

        availableCats = catService.getAll();



        sessionFactory.close();
    }
}
