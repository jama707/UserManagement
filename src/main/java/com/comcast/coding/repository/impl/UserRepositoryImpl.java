package com.comcast.coding.repository.impl;

import com.comcast.coding.entity.User;
import com.comcast.coding.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Jama Asatillayev on 5/27/2015.
 * No business logic here - just CRUD operations
 */
@Repository
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public User findByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("SELECT * FROM User u where u.email= :email", User.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public User get(Long t) {
        return entityManager.find(User.class, t);
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            entityManager.persist(user);
        } else {
            entityManager.merge(user);
        }
        return user;
    }


    @Override
    public List<User> paging(int page, int limit) {
        TypedQuery<User> query = entityManager.createQuery("FROM User", User.class);
        query.setFirstResult((page - 1) * limit);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public void deleteAll() {
        Query query = entityManager.createQuery("DELETE FROM User");
        query.executeUpdate();

    }
}
