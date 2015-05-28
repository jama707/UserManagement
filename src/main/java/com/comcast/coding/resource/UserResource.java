package com.comcast.coding.resource;

import com.comcast.coding.entity.User;
import com.comcast.coding.exception.InvalidRequestException;
import com.comcast.coding.exception.NotFoundException;
import com.comcast.coding.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Jama Asatillayev on 5/27/2015.
 * No Business logic here - delegate it to service
 */
//todo HATEOAS

@RestController
public class UserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);


    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody @Valid User user) {
        userService.save(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }


    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> get(@PathVariable Long id) {
        User user = userService.get(id);
        if (user == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable Long id, @RequestBody @Valid User user) {
        user.setId(id);
        User save = userService.save(user);
        LOGGER.error("UM-APP" + save);
        if (save != null) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/users/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        boolean isDeleted = userService.delete(id);
        if (isDeleted) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


}
