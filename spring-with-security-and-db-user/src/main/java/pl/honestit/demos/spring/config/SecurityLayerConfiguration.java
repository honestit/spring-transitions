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
                // Role nie są obsługiwane więc mamy "fakowe" zapytanie, które dla każdego użytkownika
                // zwraca ustaloną z góry rolę 'ROLE_USER'
                .authoritiesByUsernameQuery("SELECT username, 'ROLE_USER' FROM example_users WHERE username = ?");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }
}
