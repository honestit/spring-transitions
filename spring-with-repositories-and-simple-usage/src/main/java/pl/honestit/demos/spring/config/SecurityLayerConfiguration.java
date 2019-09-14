package pl.honestit.demos.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
/*
    Nie dodajemy adnotacji @EnableWebSecurity, bo to zostało już zrobione
    przez Spring Boot
 */
public class SecurityLayerConfiguration extends WebSecurityConfigurerAdapter {

    /*
        Wstrzykiwane jest podstawowe źródło danych, skonfigurowane w pliku application.properties
     */
    private final DataSource dataSource;

    public SecurityLayerConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("SELECT username, password, enabled FROM example_users WHERE username = ?")
                /*
                    Zapytanie jest wykonywane bezpośrednio do jednej tabeli, ponieważ mamy w niej zarówno informacje
                    o nazwie użytkownika jak i jego role.
                 */
                .authoritiesByUsernameQuery("SELECT username, role_name FROM example_users_roles WHERE username = ?");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // Strony związane z rejestracją dostępne dla wszystkich
                .antMatchers("/register", "register/**").permitAll()
                // Strony związane z logowaniem i wylogowaniem dostępne dla
                // użytkowników uwierzytelnionych (po zalogowaniu)
                .antMatchers("/login", "/logout").authenticated()
                // Strony zaczynające się od /user dostępne dla użytkowników
                // uwierzytelnionych z rolą USER
                .antMatchers("/user", "/user/**").hasRole("USER")
                // Strony zaczynające się od /manager dostępne dla użytkowników
                // uwierzytelnionych z rolą MANAGER
                .antMatchers("/manager", "/manager/**").hasRole("MANAGER")
                // Strony zaczynające się od /admin dostępne dla użytkowników
                // uwierzytelnionych z rolą ADMIN
                .antMatchers("/admin", "/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }
}
