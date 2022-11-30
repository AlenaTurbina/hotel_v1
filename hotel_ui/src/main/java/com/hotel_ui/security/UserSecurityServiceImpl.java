package com.hotel_ui.security;


import com.hotel_dto.dto.UserDto;
import com.hotel_ui.message.Messages;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@AllArgsConstructor
public class UserSecurityServiceImpl implements UserSecurityService {

    RestTemplate restTemplate;
    private static final String URL_HOST = Messages.getMessage("ui.url.host");
    private static final String URL_AUTH = URL_HOST + Messages.getMessage("ui.url.authentication");

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto user = restTemplate.postForObject(URL_AUTH, email, UserDto.class);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return UserSecurity.fromUser(user);
    }

}
