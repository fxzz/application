package com.example.application.security;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;


import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final DataSource dataSource;
    private final OAuth2UserService oAuth2UserService;
    private final BucketFilter bucketFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(bucketFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests().antMatchers("/", "/login" , "/signup", "/community", "/user/*/activity", "/user/*/activityData").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login").permitAll();

        http.logout().logoutSuccessUrl("/");



        http.rememberMe()
                .userDetailsService(userDetailsService)
                .tokenRepository(tokenRepository());


        http.oauth2Login().loginPage("/oauth").userInfoEndpoint().userService(oAuth2UserService);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers("/node_modules/**", "/actuator/**", "/sb_admin/**", "/error")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }


}
