package com.comcast.coding.resource;

import com.comcast.coding.entity.User;
import com.comcast.coding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Jama Asatillayev on 5/27/2015.
 * No Business logic here - delegate it to service
 */
//todo HATEOAS

@RestController
public class UserResource {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody  User user) {
        userService.save(user);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }


    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> get(@PathVariable Long id) {
        return new ResponseEntity<User>(userService.get(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody  User user) {
        user.setId(id);
        userService.save(user);
        return new ResponseEntity<Void>( HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<Void>( HttpStatus.OK);
    }


}
