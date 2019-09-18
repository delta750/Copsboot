package com.example.copsboot.business.objects.entities;

import com.example.copsboot.business.objects.enums.UserRole;
import com.example.orm.jpa.AbstractEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "copsboot_user")
public class User extends AbstractEntity<UserId> {

    private String email;
    private String name;
    private String lastname;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @NotNull
    private List<UserRole> roles;

    public User(UserId id, String email, String password, List<UserRole> roles) {
        super(id);
        this.email = email;
        this.password = password;
        this.roles = roles;
    }


    public static User createOfficer(UserId id, String officerEmail, String password) {
        return new User(id, officerEmail, password, Collections.singletonList(UserRole.OFFICER));
    }

    public static User createCaptain(UserId id, String captainEmail, String password) {
        return new User(id, captainEmail, password, Collections.singletonList(UserRole.CAPTAIN));
    }
}
