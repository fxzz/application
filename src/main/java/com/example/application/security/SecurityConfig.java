package com.example.application.security;


import com.example.application.account.dto.Account;
import com.example.application.util.CustomSessionExpiredStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;


import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final DataSource dataSource;
    private final OAuth2UserService oAuth2UserService;
    private final BucketFilter bucketFilter;
    private final LoginFailureHandler loginFailureHandler;
    private final RecaptchaFilter recaptchaFilter;
    private final LoginSuccessHandler loginSuccessHandler;
    private final SessionRegistry sessionRegistry;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(bucketFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(recaptchaFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests().antMatchers("/", "/login" , "/signup", "/community", "/user/*/activity", "/user/*/activityData").permitAll()
                .anyRequest().hasRole(Account.Role.USER.toString())
                        .and().exceptionHandling().accessDeniedPage("/block"); // 권한이 없는 요청이 발생하면 시스템은 리다이렉트

        http.formLogin()
                .loginPage("/login")
                .failureHandler(loginFailureHandler)
                .successHandler(loginSuccessHandler)
                .permitAll();

        http.logout().logoutSuccessUrl("/");

        http.sessionManagement()
            .maximumSessions(1)  // 최대 세션 수
            .sessionRegistry(sessionRegistry)
            .expiredSessionStrategy(sessionInformationExpiredStrategy()); // 세션이 만료될 때 사용할 전략 설정

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

    // 루트 페이지로 리다이렉트
    @Bean
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new CustomSessionExpiredStrategy("/");
    }

}
