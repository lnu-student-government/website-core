package org.sglnu.userservice.security;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import org.sglnu.userservice.common.Faculty;
import org.sglnu.userservice.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails{

    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Faculty faculty;
    private String groupName;
    private String phoneNumber;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id,String firstName, String lastName, String email, String password, Faculty faculty,
                           String groupName, String phoneNumber, Collection<? extends GrantedAuthority> authorities){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.faculty = faculty;
        this.groupName = groupName;
        this.phoneNumber = phoneNumber;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user){
        GrantedAuthority authority =new SimpleGrantedAuthority(user.getRole().name());

        return new UserDetailsImpl(
            user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getFaculty(),
                user.getGroupName(),
                user.getPhoneNumber(),
                Collections.singleton(authority));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getUsername(){
        return this.email;
    }
    @Override
    public String getPassword(){
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
