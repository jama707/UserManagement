package com.comcast.coding.representation;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by Jama Asatillayev on 5/27/2015.
 */
public class UserRepresentation extends ResourceSupport {
    private Long id;
    private String username;
    private String email;

}
