package org.sglnu.userservice.security;

import lombok.RequiredArgsConstructor;
import org.sglnu.userservice.domain.User;
import org.sglnu.userservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsImplService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User user = userRepository.searchUserByPhoneNumber(phoneNumber) //Pause here 11.04
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + phoneNumber));

        return UserDetailsImpl.build(user);
    }
}
