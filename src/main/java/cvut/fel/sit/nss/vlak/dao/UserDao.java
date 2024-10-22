package cvut.fel.sit.nss.vlak.dao;

import cvut.fel.sit.nss.vlak.model.User;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends BaseDao<User> {

    protected UserDao() {
        super(User.class);
    }

    public User findByUsername(String username) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

