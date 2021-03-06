package kz.ffinlife.ws.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String REALM_NAME = "ffinlife";

    @Value("${security.user.name.ffin}")
    private String ffinUsername;

    @Value("${security.user.password.ffin}")
    private String ffinPassword;

    @Value("${security.user.name.admin}")
    private String username;

    @Value("${security.user.password.admin}")
    private String password;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .realmName(REALM_NAME)
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser(ffinUsername).password(ffinPassword).roles("FFIN")
                .and()
                .withUser(username).password(password).roles("ADMIN");
    }
}