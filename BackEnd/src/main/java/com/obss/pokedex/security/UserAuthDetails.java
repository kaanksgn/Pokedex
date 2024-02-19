package com.obss.pokedex.security;

import com.obss.pokedex.model.User;
import com.obss.pokedex.util.CollectionUtils;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Getter
public class UserAuthDetails implements UserDetails {
    private User user;

    public UserAuthDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (Objects.nonNull(user) && CollectionUtils.isNotEmpty(user.getRoles())) {
            return user.getRoles().stream().map(t -> new SimpleGrantedAuthority(t.getName())).toList();
        }
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {

        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.user.isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.user.isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }
}
