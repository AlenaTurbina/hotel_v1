package com.hotel_ui.security;


import com.hotel_dto.dto.UserDTO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class UserSecurity implements UserDetails {

    private final String username;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean isActive;

    public static final UUID ID_DEFAULT_USER_STATUS_ACTIVE = UUID.fromString("ddcc5bf7-f7ca-460d-9d23-a950415ef1f4");      //default value for new user: status = "ACTIVE"

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }


    //Transformation UserDTO into UserDetails
    public static UserDetails fromUser(UserDTO userDTO) {
        return new org.springframework.security.core.userdetails.User(
                userDTO.getEmail(),                                                         //username==email
                userDTO.getPassword(),                                                      //password
                userDTO.getUserStatusInteger().equals(ID_DEFAULT_USER_STATUS_ACTIVE),       //enabled
                userDTO.getUserStatusInteger().equals(ID_DEFAULT_USER_STATUS_ACTIVE),       //accountNonExpired
                userDTO.getUserStatusInteger().equals(ID_DEFAULT_USER_STATUS_ACTIVE),       //credentialsNonExpired
                userDTO.getUserStatusInteger().equals(ID_DEFAULT_USER_STATUS_ACTIVE),       //accountNonLocked
                mapRolesToAuthorities(userDTO.getRolesString()));                           //Collection authorities
    }


    //Method for getting Collection authorities
    private static Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<String> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    }

}
