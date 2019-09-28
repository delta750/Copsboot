package com.example.copsboot.restservices;

import com.example.copsboot.business.objects.dto.UserDTO;
import com.example.copsboot.business.objects.entities.User;
import com.example.copsboot.business.objects.services.DataServices.UserDataService;
import com.example.copsboot.exceptions.UserNotFoundException;
import com.example.copsboot.infrastructure.security.UserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

// Annotate the class with RestController so Spring will pick this class up in component scanning and
//register the routes for it
@RestController
@RequestMapping("/api/users")
public class UserRestService {

    private final UserDataService userDataService;

    public UserRestService(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @GetMapping("/currentUser")
    // annotate it with @AuthenticationPrincipal to get information on the user that is doing the request
    public UserDTO currentUser(@AuthenticationPrincipal UserDetails applicationUserDetails) {

        // Ask the UserService for the user that matches the given ID. Since the service returns an Optional,
        //this throws a custom exception UserNotFoundException when there is no user with the given ID
        //(which would be quite rare since it is the ID of the currently logged-on user).
        User user = userDataService.getUser(applicationUserDetails.getUserId()).orElseThrow(() -> new UserNotFoundException(applicationUserDetails.getUserId()));

        return UserDTO.fromUser(user);
    }

    @PostMapping("/createOfficer")
    @ResponseStatus(HttpStatus.CREATED) // Upon successful execution of this method, return a status code 201 Created to the client.
    // @RequestBody tells Spring to deserialize the JSON that gets posted to the CreateOfficerParameters
    //object. The @Valid annotation before that will make Spring validate the parameters object as well.
    public UserDTO createOfficer(@Valid @RequestBody CreateOfficerParameters officerParameters){

        final User officer = userDataService.createOfficer(officerParameters.getEmail(), officerParameters.getPassword());
        return UserDTO.fromUser(officer);
    }
}
