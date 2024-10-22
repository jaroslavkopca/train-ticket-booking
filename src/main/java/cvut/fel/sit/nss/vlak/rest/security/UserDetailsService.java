package cvut.fel.sit.nss.vlak.rest.security;

import cvut.fel.sit.nss.vlak.dao.UserDao;
import cvut.fel.sit.nss.vlak.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserDao userDao;

    @Autowired
    public UserDetailsService(UserDao clientDao) {
        this.userDao = clientDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username " + username + " not found.");
        }
        return new cvut.fel.sit.nss.vlak.security.model.UserDetails(user);
    }
}
