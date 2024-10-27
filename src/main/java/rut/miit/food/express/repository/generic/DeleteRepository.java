package rut.miit.food.express.repository.generic;

import org.springframework.data.repository.NoRepositoryBean;
import rut.miit.food.express.entity.BaseEntity;

@NoRepositoryBean
public interface DeleteRepository<T extends BaseEntity, ID> {
    void delete(T entity);
    void deleteById(ID id);
}
