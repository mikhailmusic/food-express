package rut.miit.food.express.repository;

import rut.miit.food.express.entity.Role;
import rut.miit.food.express.entity.enums.UserRole;
import rut.miit.food.express.repository.generic.CreateRepository;
import rut.miit.food.express.repository.generic.ReadRepository;

import java.util.Optional;

public interface UserRoleRepository extends
        CreateRepository<Role, Integer>, ReadRepository<Role, Integer> {

    Optional<Role> findRoleByName(UserRole role);
}
