package mvc.spring.security.services;

import mvc.spring.security.entities.Role;
import mvc.spring.security.entities.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private RepositoryService repositoryService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setRepositoryService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    @Transactional (readOnly = true)
    public User findByUsername(String name) {
        return repositoryService.findByUsername(name);
    }

    @Override
    @Transactional (readOnly = true)
    public User findUserById(int id) {
        return repositoryService.findUserById(id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = findByUsername(name);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Пользователь '%s' не найден", name));
        }
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    @Override
    @Transactional
    public void register(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRoles(user.getRoles());
        repositoryService.saveUser(user);
    }

    @Override
    @Transactional
    public void update(int id, User userWithNewInfo) {
        User updatedUser = repositoryService.findUserById(id);
        updatedUser.setName(userWithNewInfo.getName());
        updatedUser.setAge(userWithNewInfo.getAge());
        updatedUser.setEmail(userWithNewInfo.getEmail());
        updatedUser.setRoles(userWithNewInfo.getRoles());
        if (!userWithNewInfo.getPassword().isEmpty()) { // что бы пароль не хранился в хэше
            updatedUser.setPassword(passwordEncoder.encode(userWithNewInfo.getPassword()));
        }
        repositoryService.saveUser(updatedUser);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        repositoryService.deleteUserById(id);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}