package rut.miit.food.express.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import rut.miit.food.express.entity.Role;
import rut.miit.food.express.entity.enums.UserRole;
import rut.miit.food.express.repository.UserRoleRepository;

import java.util.Optional;

@Repository
public class UserRoleRepositoryImpl extends BaseRepository<Role, Integer> implements UserRoleRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public UserRoleRepositoryImpl() {
        super(Role.class);
    }

    @Override
    public Optional<Role> findRoleByName(UserRole role) {
        try {
            return Optional.of(entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :role", Role.class)
                    .setParameter("role", role)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
