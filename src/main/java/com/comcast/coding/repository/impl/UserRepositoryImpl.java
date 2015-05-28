package com.comcast.coding.repository.impl;

import com.comcast.coding.entity.User;
import com.comcast.coding.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Jama Asatillayev on 5/27/2015.
 * No business logic here - just CRUD operations
 */
@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public User findByEmail(String email) {
        try {
            TypedQuery<User> query = entityManager.createQuery("SELECT * FROM User u where u.email= :email", User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (Exception e) {
            LOGGER.error("UM-APP", e);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        try {
            User user = entityManager.find(User.class, id);
            if (user == null) {
                return false;
            } else {
                entityManager.remove(user);
                return true;
            }

        } catch (Exception e) {
            LOGGER.error("UM-APP", e);
        }
        return false;
    }

    @Override
    public User get(Long t) {
        try {
            return entityManager.find(User.class, t);
        } catch (Exception e) {
            LOGGER.error("UM-APP", e);
        }
        return null;
    }

    @Override
    public User save(User user) {
        try {
            if (user.getId() == null) {
                entityManager.persist(user);
                return user;
            } else {
                return entityManager.merge(user);
            }
        } catch (Exception e) {
            LOGGER.error("UM-APP", e);
        }
        return user;
    }


    @Override
    public List<User> paging(int page, int limit) {
        try {
            TypedQuery<User> query = entityManager.createQuery("FROM User", User.class);
            query.setFirstResult((page - 1) * limit);
            query.setMaxResults(limit);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("UM-APP", e);
        }
        return null;
    }

    @Override
    public void deleteAll() {


    }
}
