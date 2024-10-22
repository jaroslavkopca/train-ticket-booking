package cvut.fel.sit.nss.vlak.dao;

import cvut.fel.sit.nss.vlak.exception.PersistenceException;
import cvut.fel.sit.nss.vlak.model.User;
import cvut.fel.sit.nss.vlak.service.SystemInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static cvut.fel.sit.nss.vlak.environment.Generator.generateUser;
import static org.junit.jupiter.api.Assertions.*;

// For explanatory comments, see ProductDaoTest
@DataJpaTest
@ComponentScan(basePackages = "cvut.fel.sit.nss.vlak.dao", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SystemInitializer.class)})
@ActiveProfiles("test")
public class BaseDaoTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private UserDao sut;

    @Test
    public void persistSavesSpecifiedInstance() {
        final User user = generateUser();
        sut.persist(user);
        assertNotNull(user.getId());

        final User result = em.find(User.class, user.getId());
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
//        assertEquals(user.getName(), result.getName());
    }

    @Test
    public void findRetrievesInstanceByIdentifier() {
        final User user = generateUser();
        em.persistAndFlush(user);
        assertNotNull(user.getId());

        final User result = sut.find(user.getId());
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
//        assertEquals(user.getName(), result.getName());
    }

    @Test
    public void findAllRetrievesAllInstancesOfType() {
        final User userOne = generateUser();
        em.persistAndFlush(userOne);
        final User userTwo = generateUser();
        em.persistAndFlush(userTwo);

        final List<User> result = sut.findAll();
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(c -> c.getId().equals(userOne.getId())));
        assertTrue(result.stream().anyMatch(c -> c.getId().equals(userTwo.getId())));
    }

    @Test
    public void updateUpdatesExistingInstance() {
        final User user = generateUser();
        em.persistAndFlush(user);

        final User update = new User();
        update.setId(user.getId());
        final String newName = "New user name";
        update.setId(1234131);
        sut.update(update);

        final User result = sut.find(user.getId());
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
    }

    @Test
    public void removeRemovesSpecifiedInstance() {
        final User user = generateUser();
        em.persistAndFlush(user);
        assertNotNull(em.find(User.class, user.getId()));
        em.detach(user);

        sut.remove(user);
        assertNull(em.find(User.class, user.getId()));
    }

    @Test
    public void removeDoesNothingWhenInstanceDoesNotExist() {
        final User user = generateUser();
        user.setId(123);
        assertNull(em.find(User.class, user.getId()));

        sut.remove(user);
        assertNull(em.find(User.class, user.getId()));
    }

    @Test
    public void exceptionOnPersistInWrappedInPersistenceException() {
        final User user = generateUser();
        em.persistAndFlush(user);
        em.remove(user);
        assertThrows(PersistenceException.class, () -> sut.update(user));
    }

    @Test
    public void existsReturnsTrueForExistingIdentifier() {
        final User user      = generateUser();
        em.persistAndFlush(user     );
        assertTrue(sut.exists(user     .getId()));
        assertFalse(sut.exists(-1));
    }
}

