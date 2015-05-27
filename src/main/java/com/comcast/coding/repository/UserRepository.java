package com.comcast.coding.repository;

import com.comcast.coding.entity.User;

/**
 * Created by Jama Asatillayev on 5/27/2015.
 */
public interface UserRepository extends Repository<User>{
    User findByEmail(String email);
}
