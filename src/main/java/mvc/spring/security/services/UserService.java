package mvc.spring.security.services;

import mvc.spring.security.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
    User findUserById(int id);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    void register(User user);
    void update(int id, User userWithNewInfo);
    void deleteById(int id);
}