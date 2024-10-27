package rut.miit.food.express.repository.generic;

import org.springframework.data.repository.NoRepositoryBean;
import rut.miit.food.express.entity.BaseEntity;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ReadRepository<T extends BaseEntity, ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
}
