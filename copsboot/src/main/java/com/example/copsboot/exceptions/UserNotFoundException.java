package com.example.copsboot.exceptions;

import com.example.copsboot.business.objects.entities.UserId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// By annotating the exception with @ResponseStatus, Spring will use that status code in the response
//when this exception gets thrown in a controller
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends  RuntimeException {

    public UserNotFoundException(UserId userId){
        super(String.format("Could not find user with id %s", userId.asString()));
    }
}
