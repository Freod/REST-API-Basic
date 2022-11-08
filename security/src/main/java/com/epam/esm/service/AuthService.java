package com.epam.esm.service;

import com.epam.esm.dao.UserRepository;
import com.epam.esm.model.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUser securityUser = userRepository.findByUsername(username)
                .map(SecurityUser::new)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with username = (" + username + ") isn't exists."));
        return securityUser;
    }
}
