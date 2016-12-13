package com.epam.rd.frontend.config;

import com.epam.rd.frontend.security.UserDetailsAuthenticationProvider;
import com.epam.rd.frontend.service.info.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Import(AuthorizationConfig.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/rd/", "/rd/dashboard", "/rd/info/**").hasRole("MENTEE")
                .antMatchers("/rd/admin/**").hasRole("LECTURER")
                .anyRequest().fullyAuthenticated()
                .and().formLogin()
                .defaultSuccessUrl("/rd/")
                .and().exceptionHandling().accessDeniedPage("/rd/denied");
    }

    @Bean
    public UserDetailsAuthenticationProvider authenticationProvider() {
        UserDetailsAuthenticationProvider provider = new UserDetailsAuthenticationProvider(
                userDetailsServiceImpl(), encoder());
        return provider;
    }

    @Bean
    public UserDetailsServiceImpl userDetailsServiceImpl() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
