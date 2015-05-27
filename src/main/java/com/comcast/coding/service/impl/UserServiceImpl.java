package com.comcast.coding.service.impl;

import com.comcast.coding.entity.User;
import com.comcast.coding.repository.UserRepository;
import com.comcast.coding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Jama Asatillayev on 5/27/2015.
 * Business logic lives here
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Override
    public User get(Long t) {
        return userRepository.get(t);
    }

    @Override
    public void save(User user) {
        user.setCreatedDate(Calendar.getInstance().getTime());
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> paging(int page, int limit) {
        return userRepository.paging(page, limit);
    }
}
