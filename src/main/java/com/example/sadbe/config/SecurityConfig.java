package com.example.sadbe.config;

import com.example.sadbe.repository.UsersRepository;
import com.example.sadbe.tables.pojos.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        // Разрешаем читать доки без авторизации
        web.ignoring().antMatchers("/swagger-ui/**", "/api-docs/**", "/actuator/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((request, response, authentication) -> response.setStatus(HttpServletResponse.SC_OK))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .deleteCookies("userId")
                .and()
                .authorizeRequests()
                // Разрешаем изменение данных только админам
                .antMatchers(HttpMethod.POST, "/teacher").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .failureHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }).successHandler((request, response, authentication) -> {
                    String login = authentication.getName();
                    Users user = usersRepository.getActual(login);
                    response.setContentType("application/json");
                    response.getWriter().write(String.format("{\"isAdmin\": \"%s\"}", user.getIsAdmin()));
                    response.setStatus(HttpServletResponse.SC_OK);
                })
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select login, password, True from sad.users " +
                        "where login=?")
                .authoritiesByUsernameQuery("select login, case when is_admin is true then 'ROLE_ADMIN' else 'ROLE_USER' end from sad.users " +
                        "where login=?");
    }

}
