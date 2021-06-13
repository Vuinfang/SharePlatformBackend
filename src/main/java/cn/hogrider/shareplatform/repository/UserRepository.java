package cn.hogrider.shareplatform.repository;

import cn.hogrider.shareplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUsername(String username);

    User findByEmail(String email);
}
