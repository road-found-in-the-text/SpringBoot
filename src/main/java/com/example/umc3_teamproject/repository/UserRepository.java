package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user){
        em.persist(user);
    }

    // 한사람만 조회
    public User findOne(Long id) {
        return em.find(User.class,id);
    }

    // 모두 조회
    public List<User> findAll(){
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    // 이름으로 조회
    public List<User> findByName(String name){
        return em.createQuery("select u from User u where u.name = :name",User.class)
                .setParameter("name",name)
                .getResultList();
    }


}
