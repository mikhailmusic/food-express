package rut.miit.food.express.repository;

import rut.miit.food.express.entity.User;
import rut.miit.food.express.repository.generic.CreateRepository;
import rut.miit.food.express.repository.generic.ReadRepository;
import rut.miit.food.express.repository.generic.UpdateRepository;

import java.util.Optional;

public interface UserRepository extends
        CreateRepository<User, Integer>, ReadRepository<User, Integer>, UpdateRepository<User, Integer> {

    Optional<User> findByUsername(String username);

}
