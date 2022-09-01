package com.lojavirtual.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@Order(2)
public class WebSecurityConfig {


    @Autowired
    private DataSource dataSource;



    @Bean
    @Primary
    public AuthenticationManagerBuilder managerBuilder(AuthenticationManagerBuilder auth)throws Exception{
        auth.jdbcAuthentication()
                        .usersByUsernameQuery("select email as username, senha as password, 1 as enable from funcionario where email=?")
                        .authoritiesByUsernameQuery("select funcionario.email as username, papel.nome as authority from permissao inner join funcionario on funcionario.id=permissao.funcionario_id inner join papel on permissao.papel_id=papel.id where funcionario.email=?")
                        .dataSource(dataSource)
                        .passwordEncoder(new BCryptPasswordEncoder());
        return auth;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/login").permitAll()
                .antMatchers("/funcionarios/**")
                .hasAuthority("gerente").antMatchers("/admin").authenticated()
                .antMatchers("/papel/**")
                .hasAuthority("gerente").antMatchers("/admin").authenticated()
                .antMatchers("/permissao/**")
                .hasAuthority("gerente").antMatchers("/admin").authenticated()
                .antMatchers("/produtos/**")
                .hasAuthority("gerente").antMatchers("/admin").authenticated()
                .antMatchers("/entrada/**")
                .hasAuthority("gerente").antMatchers("/admin").authenticated()
                .and().formLogin()
                .loginPage("/login").failureUrl("/login").loginProcessingUrl("/admin")
                .defaultSuccessUrl("/admin").usernameParameter("username").passwordParameter("password").and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout"))
                .logoutSuccessUrl("/login").deleteCookies("JSESSIONID").and().exceptionHandling()
                .accessDeniedPage("/negado").and().csrf().disable();
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }




}
