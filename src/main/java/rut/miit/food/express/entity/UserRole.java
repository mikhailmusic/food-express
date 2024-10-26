package rut.miit.food.express.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "user_role")
public class UserRole extends BaseEntity {
    private String role;
    private Set<User> users;

    public UserRole(String role, Set<User> users) {
        this.role = role;
        this.users = users;
    }

    protected UserRole() {
    }

    @Column(name = "role", nullable = false, unique = true)
    public String getRole() {
        return role;
    }

    @OneToMany(mappedBy = "role")
    public Set<User> getUsers() {
        return users;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
