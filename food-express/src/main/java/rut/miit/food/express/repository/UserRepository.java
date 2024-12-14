package rut.miit.food.express.repository;

import rut.miit.food.express.entity.User;
import rut.miit.food.express.repository.generic.CreateRepository;
import rut.miit.food.express.repository.generic.ReadRepository;
import rut.miit.food.express.repository.generic.UpdateRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends
        CreateRepository<User, Integer>, ReadRepository<User, Integer>, UpdateRepository<User, Integer> {

    Optional<User> findByUsername(String username);
    List<User> findByUsernameContaining(String partUsername);
    Set<String> findAllUsernames();
    Set<String> findAllPhoneNumbers();

}
