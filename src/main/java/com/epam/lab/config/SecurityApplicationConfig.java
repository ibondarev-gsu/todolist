package com.epam.lab.config;

import com.epam.lab.security.CustomAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
public class SecurityApplicationConfig extends WebSecurityConfigurerAdapter
{

    private final UserDetailsService userDetailsService;
    private final AuthenticationFailureHandler failureHandler;

    public SecurityApplicationConfig(UserDetailsService userDetailsService,
                                     @Qualifier("customAuthenticationFailureHandler") AuthenticationFailureHandler failureHandler) {
        this.userDetailsService = userDetailsService;
        this.failureHandler = failureHandler;
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
                    .antMatchers( "/login", "/registration", "/regitrationConfirm", "/badUser", "/forgotPassword").permitAll()
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
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
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
    @Bean(name = "bCryptPasswordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

}
