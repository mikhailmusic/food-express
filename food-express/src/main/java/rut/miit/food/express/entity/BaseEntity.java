package rut.miit.food.express.entity;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
    private Integer id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }
    protected void setId(Integer id) {
        this.id = id;
    }
}
