package rut.miit.food.express.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import rut.miit.food.express.entity.BaseEntity;
import rut.miit.food.express.repository.generic.CreateRepository;
import rut.miit.food.express.repository.generic.ReadRepository;
import rut.miit.food.express.repository.generic.UpdateRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T extends BaseEntity, ID> implements
        CreateRepository<T, ID>, ReadRepository<T, ID>, UpdateRepository<T, ID> {

    @PersistenceContext
    private EntityManager entityManager;
    private final Class<T> entityClass;

    public BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    @Transactional
    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public T update(T entity) {
        entityManager.merge(entity);
        return entity;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery("from " + entityClass.getName(), entityClass)
                .getResultList();
    }

}
