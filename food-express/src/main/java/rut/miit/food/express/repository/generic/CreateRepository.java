package rut.miit.food.express.repository.generic;

import org.springframework.data.repository.NoRepositoryBean;
import rut.miit.food.express.entity.BaseEntity;

@NoRepositoryBean
public interface CreateRepository<T extends BaseEntity, ID> {
    T save(T entity);
}
