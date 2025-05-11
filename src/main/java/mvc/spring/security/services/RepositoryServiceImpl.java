package mvc.spring.security.services;

import mvc.spring.security.entities.Role;
import mvc.spring.security.entities.User;
import mvc.spring.security.repositories.RoleRepository;
import mvc.spring.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepositoryServiceImpl implements RepositoryService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public RepositoryServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Role findByRolename(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public List<Role> findAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public Role saveRole(String name) {
        return roleRepository.save(new Role(name));
    }

    @Override
    public User findByUsername(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(int id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteUserById(id);
    }
}
