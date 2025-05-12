package mvc.spring.security.utils;

import mvc.spring.security.services.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner {

    private final RepositoryService repositoryService;

    @Autowired
    public RoleInitializer(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repositoryService.findByRolename("ROLE_ADMIN") == null) {
            repositoryService.saveRole("ROLE_ADMIN");
        }
        if (repositoryService.findByRolename("ROLE_USER") == null) {
            repositoryService.saveRole("ROLE_USER");
        }
    }
}