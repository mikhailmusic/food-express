package rut.miit.food.express.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import rut.miit.food.express.entity.User;
import rut.miit.food.express.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserRepositoryImpl extends BaseRepository<User, Integer> implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            return Optional.of(entityManager.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
                    .setParameter("login", username)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findByUsernameContaining(String partUsername) {
        return entityManager.createQuery("SELECT u FROM User u WHERE LOWER(u.login) LIKE LOWER(:name)", User.class)
                .setParameter("name", "%" + partUsername + "%")
                .getResultList();
    }

    @Override
    public Set<String> findAllUsernames() {
        return new HashSet<>(entityManager.createQuery("SELECT u.login FROM User u", String.class).getResultList());
    }

    @Override
    public Set<String> findAllPhoneNumbers() {
        return new HashSet<>(entityManager.createQuery("SELECT u.phoneNumber FROM User u", String.class).getResultList());
    }

}
