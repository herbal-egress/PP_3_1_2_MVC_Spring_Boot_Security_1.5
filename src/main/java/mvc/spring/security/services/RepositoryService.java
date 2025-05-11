package mvc.spring.security.services;

import mvc.spring.security.entities.Role;
import mvc.spring.security.entities.User;

import java.util.List;

public interface RepositoryService {
    List<Role> findAllRole();
    Role findByRolename(String name);
    Role saveRole(String name);
    List<User> findAllUser();
    User findByUsername(String name);
    User findUserById(int id);
    User saveUser(User user);
    void deleteUserById(int id);
}