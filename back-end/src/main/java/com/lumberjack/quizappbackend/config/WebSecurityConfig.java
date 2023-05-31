package com.lumberjack.quizappbackend.config;

import com.lumberjack.quizappbackend.filter.JwtAuthenticationFilter;
import com.lumberjack.quizappbackend.filter.JwtAuthorizationFilter;
import com.lumberjack.quizappbackend.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

//    @Bean
//    public UserDetailsService users() {
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("$2a$10$uBWEtln11mxxFDiCNQ/jm.0nwQ/1WqJqKEw06BhXgQonD.whpCs3O")
//                .roles("ADMIN", "USER")
//                .build();
//        UserDetails user = User.builder()
//                .username("john")
//                .password("john")
//                .roles("USER")
//                .build();
//        log.info(passwordEncoder().encode("admin"));
//        return new InMemoryUserDetailsManager(admin, user);
//    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.authorizeHttpRequests().antMatchers("/authenticate/**", "/login/**", "/registration/**", "/isAuthenticated/**").permitAll();
        httpSecurity.authorizeHttpRequests().antMatchers("/api/v1/**").hasAnyAuthority("ROLE_USER");
        httpSecurity.authorizeHttpRequests().antMatchers("/api/admin/**").hasAnyAuthority("ROLE_ADMIN");
        httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
        httpSecurity.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
        httpSecurity.addFilter(new JwtAuthenticationFilter(authenticationManagerBean(), jwtTokenUtil, userService));

        // Add filter to validate the tokens with every request
        httpSecurity.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public JwtTokenUtil getJwtTokenUtil() {
        return new JwtTokenUtil();
    }

}
