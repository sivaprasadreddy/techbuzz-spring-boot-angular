package com.sivalabs.techbuzz.security;

import com.sivalabs.techbuzz.users.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        SecurityUser securityUser =
                userRepository
                        .findByEmail(username)
                        .map(SecurityUser::new)
                        .orElseThrow(
                                () ->
                                        new UsernameNotFoundException(
                                                "No user found with username " + username));

        if (!securityUser.isEnabled()) {
            throw new DisabledException("Account verification is pending");
        }
        return securityUser;
    }
}
