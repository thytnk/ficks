package com.fujielectric.ficks.domain;

import com.fujielectric.ficks.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String empNumber) throws UsernameNotFoundException {
        Optional<User> result = userRepository.findEnabledUser(empNumber);
        if (result.isPresent()) {
            return new LoginUserDetails(result.get());
        }
        throw new UsernameNotFoundException("Employee not found");
    }
}
