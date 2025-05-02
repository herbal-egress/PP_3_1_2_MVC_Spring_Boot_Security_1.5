package mvc.spring.security.services;

import mvc.spring.security.entities.Role;
import mvc.spring.security.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
// Проверка наличия ролей в базе данных/ Просто для автоматизации добавил.
// Перед началом работы с приложением запускается этот класс, проверяет что роли ADMIN и USER уже существуют в базе данных.
// Если их нет, то при старте приложения происходит запись перечисленных здесь ролей в таблицу roles в БД :

@Component
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Создаем роли, если их нет в базе данных
        if (roleRepository.findByName("ROLE_ADMIN") == null) {
            roleRepository.save(new Role("ROLE_ADMIN"));
        }
        if (roleRepository.findByName("ROLE_USER") == null) {
            roleRepository.save(new Role("ROLE_USER"));
        }
    }
}