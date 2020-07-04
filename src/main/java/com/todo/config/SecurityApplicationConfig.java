package com.todo.config;

import com.todo.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityApplicationConfig extends WebSecurityConfigurerAdapter
{

    private final UserService userService;
    private final AuthenticationFailureHandler failureHandler;
    private final PasswordEncoder passwordEncoder;

    public SecurityApplicationConfig(UserService userService,
                                     @Qualifier("customAuthenticationFailureHandler") AuthenticationFailureHandler failureHandler,
                                     PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.failureHandler = failureHandler;
        this.passwordEncoder = passwordEncoder;
    }



    /**
     * Override this method to configure the {@link HttpSecurity}. Typically subclasses
     * should not invoke this method by calling super as it may override their
     * configuration. The default configuration is:
     *
     * <pre>
     * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
     * </pre>
     *
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception if an error occurs
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                // указываем правила запросов
                // по которым будет определятся доступ к ресурсам и остальным данным
                .authorizeRequests()
                    .antMatchers("/resources/**").permitAll()
                    .antMatchers( "/login", "/registration", "/active", "/resetPassword", "/forgotPassword", "/changePassword", "/updatePassword").permitAll()
                .anyRequest().authenticated()
                .and()

                .formLogin()
//                // указываем страницу с формой логина
                    .loginPage("/login").permitAll()
//                    // указываем action с формы логина
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/hello")
                    .failureHandler(failureHandler)
//                    .failureUrl("/login?error")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .permitAll()
                .and()
                .logout()
                    .permitAll()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }

//    @Override
//    protected void configure(final AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(authProvider());
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authProvider() {
//        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//


}
