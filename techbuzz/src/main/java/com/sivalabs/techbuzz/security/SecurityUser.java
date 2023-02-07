package com.sivalabs.techbuzz.security;

import com.sivalabs.techbuzz.users.domain.User;
import java.util.Set;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class SecurityUser extends org.springframework.security.core.userdetails.User {
    private final User user;

    public SecurityUser(User user) {
        super(
                user.getEmail(),
                user.getPassword(),
                Set.of(new SimpleGrantedAuthority(user.getRole().name())));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean isEnabled() {
        return this.user.isVerified();
    }
}
