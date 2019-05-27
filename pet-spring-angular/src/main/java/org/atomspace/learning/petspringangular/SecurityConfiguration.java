package org.atomspace.learning.petspringangular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

/**
 * Created by sergey.derevyanko on 27.05.19.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String USER_BY_USERNAME_QUERY = "select login, password, 1 " +
            "from user where login=?";
    private static final String AUTHORITIES_BY_USERNAME_QUERY = "select login, \"ADMIN\" from user where login = ?";



    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(USER_BY_USERNAME_QUERY)
                .authoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME_QUERY)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().authorizeRequests()
                .antMatchers("admin/**").hasRole("ADMIN")
                .antMatchers("/login*").permitAll()
                .antMatchers("/status").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
//                .loginProcessingUrl("/perform_login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/admin/userdetails")
                .failureForwardUrl("/auth_error")
                .and().logout().logoutUrl("/logout").deleteCookies("JSESSIONID");
    }

}
