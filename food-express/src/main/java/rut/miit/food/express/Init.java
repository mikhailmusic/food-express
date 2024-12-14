package rut.miit.food.express;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rut.miit.food.express.entity.Role;
import rut.miit.food.express.entity.enums.UserRole;
import rut.miit.food.express.repository.UserRoleRepository;


@Component
public class Init implements CommandLineRunner {
    private final UserRoleRepository userRoleRepository;

    public Init(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initRoles();
    }
    private void initRoles() {
        if (userRoleRepository.findAll().isEmpty()) {
            Role normalUserRole = new Role(UserRole.USER);
            Role moderatorRole = new Role(UserRole.MODERATOR);
            Role adminRole = new Role(UserRole.ADMIN);
            userRoleRepository.save(normalUserRole);
            userRoleRepository.save(moderatorRole);
            userRoleRepository.save(adminRole);
        }
    }
}
