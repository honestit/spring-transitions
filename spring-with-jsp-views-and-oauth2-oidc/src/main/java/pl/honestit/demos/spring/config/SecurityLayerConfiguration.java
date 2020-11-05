package pl.honestit.demos.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.honestit.demos.spring.security.mvc.CustomUserDetailsService;
import pl.honestit.demos.spring.security.oauth2.CustomOAuth2UserService;
import pl.honestit.demos.spring.security.oidc.CustomOIDCUserService;

@Configuration
/*
    Nie dodajemy adnotacji @EnableWebSecurity, bo to zostało już zrobione
    przez Spring Boot
 */
@Slf4j
public class SecurityLayerConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public CustomOAuth2UserService customOAuth2UserService() {
        return new CustomOAuth2UserService();
    }

    @Bean
    public CustomOIDCUserService customOIDCUserService() {
        return new CustomOIDCUserService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ADMIN")
                .antMatchers("/").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/media/**").permitAll()
                .antMatchers("/register", "register/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/logout").authenticated()
                .antMatchers("/account", "/account/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                .antMatchers("/user", "/user/**").hasRole("USER")
                .antMatchers("/manager", "/manager/**").hasRole("MANAGER")
                .antMatchers("/admin", "/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/", false)
                .and()
            .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessUrl("/login?logout")
                .and()
            .httpBasic()
                .disable()
            .oauth2Login()
                .loginPage("/login")
                .defaultSuccessUrl("/account", true)
                .userInfoEndpoint()
                    .userService(customOAuth2UserService())
                    .oidcUserService(customOIDCUserService());
    }
}
