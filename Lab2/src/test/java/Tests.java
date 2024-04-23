import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.isaev.DAO.CatDAO;
import ru.isaev.Entities.Cats.Cat;
import ru.isaev.Entities.Cats.CatColors;

import java.sql.Date;
import java.util.Calendar;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class Tests {
    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    private CatDAO catDAO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        catDAO = new CatDAO(sessionFactory);
    }


    @Test
    public void testAddCat() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.SEPTEMBER, 9);
        Cat testCat = new Cat();
        testCat.setBreed("breed1");
        testCat.setColor(CatColors.Red);
        testCat.setBirthday(new Date(calendar.getTime().getTime()));

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);

        catDAO.add(testCat);

        verify(session).persist(testCat);
        verify(transaction).commit();
        verify(session).close();

        sessionFactory.close();
    }

    @Test
    public void testGetCat() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.SEPTEMBER, 9);
        Cat testCat = new Cat();
        testCat.setBreed("breed1");
        testCat.setColor(CatColors.Red);
        testCat.setBirthday(new Date(calendar.getTime().getTime()));

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);

        catDAO.getById(1L);

        verify(session).find(Cat.class, 1L);
        verify(transaction).commit();
        verify(session).close();

        sessionFactory.close();
    }

    @Test
    public void testUpdateCat() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.SEPTEMBER, 9);
        Cat testCat = new Cat();
        testCat.setBreed("breed1");
        testCat.setColor(CatColors.Red);
        testCat.setBirthday(new Date(calendar.getTime().getTime()));

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);

        catDAO.update(testCat);

        verify(session).update(testCat);
        verify(transaction).commit();
        verify(session).close();

        sessionFactory.close();
    }

    @Test
    public void testDeleteCat() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.SEPTEMBER, 9);
        Cat testCat = new Cat();
        testCat.setBreed("breed1");
        testCat.setColor(CatColors.Red);
        testCat.setBirthday(new Date(calendar.getTime().getTime()));

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);

        catDAO.remove(testCat);

        verify(session).remove(testCat);
        verify(transaction).commit();
        verify(session).close();

        sessionFactory.close();
    }

}
