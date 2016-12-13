package com.epam.rd.frontend.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;


public class CurrentUser implements UserDetails {
    private String password;
    private String userName;
    private static final long serialVersionUID = 1L;
    private Collection<? extends GrantedAuthority> authorities;

    public CurrentUser(String password, String userName, Collection<? extends GrantedAuthority> authorities) {
        this.password = password;
        this.userName = userName;
        this.authorities = authorities;
    }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override public String getPassword() {
        return password;
    }

    @Override public String getUsername() {
        return userName;
    }

    @Override public boolean isAccountNonExpired() {
        return true;
    }

    @Override public boolean isAccountNonLocked() {
        return true;
    }

    @Override public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override public boolean isEnabled() {
        return true;
    }
}
