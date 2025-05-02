package mvc.spring.security.repositories;

import mvc.spring.security.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // использую этот метод для реализации автозаполнения таблицы ролей в классе RoleInitializer:
    Role findByName(String name);
}
