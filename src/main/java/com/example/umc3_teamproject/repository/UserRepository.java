package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(User user){
        em.persist(user);
        return user.getId();
    }

    public User find(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() { //jpa query . sql(테이블)과 달리 user 객체에 대한 쿼리를 나타냄
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    public List<User> findByName(String name){
        return em.createQuery("select u from User u where u.name = :name", User.class)
                .setParameter("name", name)
                .getResultList();
    }

}
