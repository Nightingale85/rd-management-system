package com.epam.rd.frontend.security;


import com.epam.rd.frontend.service.info.impl.UserDetailsServiceImpl;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private UserDetailsServiceImpl userDetailsService;
    private PasswordEncoder passwordEncoder;

    public UserDetailsAuthenticationProvider(
            UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void additionalAuthenticationChecks(
            UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            throw new BadCredentialsException(messages.getMessage(
                    "UserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), null);
        }

        String presentedPassword = authentication.getCredentials().toString();
        if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            throw new BadCredentialsException(messages.getMessage(
                    "UserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), null);
        }
    }

    @Override
    protected UserDetails retrieveUser(
            String s, UsernamePasswordAuthenticationToken authenticationToken) throws AuthenticationException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(s);
        return userDetails;
    }
}
