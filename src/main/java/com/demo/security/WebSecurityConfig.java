package com.demo.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${user.password}")
    private String password1;
    @Value("${admin.password}")
    private String password2;
    @Value("${user&admin.password}")
    private String password3;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //here, we autenticate and filter the user using username,password and based on role.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("sohel")
                .password(passwordEncoder().encode(password1))
                .roles("USER")
           .and()
                .withUser("alamin")
                .password(passwordEncoder().encode(password2))
                .roles("ADMIN")
           .and()
                .withUser("arfin")
                .password(passwordEncoder().encode(password3))
                .roles("USER","ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //give the permission everyone to show this page,here doesn"t need to authenticate
                .antMatchers("/","/home").permitAll()
                /*depending on role a authenticate user can see this page.here,
                like who have admin role they can enter this page*/
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user").hasRole("USER")
                /*depending on role who have any role between user and admin or Both role ,
                they can see this page and have to be a authenticate user.*/
                .antMatchers("user&admin").hasAnyRole("USER","ADMIN")
                //any request have to be authenticated
                .anyRequest().authenticated()
           .and()
/*                Here, we using httpBasic authentication. means that untill exit the web browser
                 ,it remain same authentication user.*/
                .httpBasic()
           .and()
                //when we access/authorized the page with wrong role ,that tmie to handale error we use 403 page.
                .exceptionHandling()
                .accessDeniedPage("/403");




    }
}
