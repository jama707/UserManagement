package com.comcast.coding.resource;

import com.comcast.coding.entity.User;

import java.util.Calendar;

/**
 * Created by Jama Asatillayev on 5/27/2015.
 */
public class UserBuilder {
    private User user = new User();

    public UserBuilder setUserName(String userName) {
        user.setUserName(userName);
        return this;
    }

    public UserBuilder setEmail(String email) {
        user.setEmail(email);
        return this;
    }

    public User build() {
        user.setCreatedDate(Calendar.getInstance().getTime());
        return user;
    }
}
