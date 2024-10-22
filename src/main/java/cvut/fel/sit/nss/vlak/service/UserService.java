package cvut.fel.sit.nss.vlak.service;

import cvut.fel.sit.nss.vlak.dao.UserDao;
import cvut.fel.sit.nss.vlak.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerUser(User user) {
        if (userDao.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists: " + user.getUsername());
        }
        user.encodePassword(passwordEncoder);
        userDao.persist(user);
    }
}

