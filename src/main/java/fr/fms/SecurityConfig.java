package fr.fms;

import fr.fms.entities.AppUser;
import fr.fms.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.util.ArrayList;
import java.util.Collection;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200");
            }
        };
    }
    @Autowired
    private UserDetailsService userDetailsService;
    @Bean
    public UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }
    @Autowired
    private AccountServiceImpl accountService;
    @Override
    protected void configure(HttpSecurity http) throws Exception{
       // http.formLogin();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/trainings/**","/api/categories/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/login/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/photo/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/photo/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/trainings/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/trainings/**").hasAuthority("ADMIN");

        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/users").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/users").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/role").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/roles").permitAll();
                //hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/roleUser").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.csrf().disable();
        http.cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService((new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                AppUser user = accountService.findUserByUsername(username);
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                user.getRoles().forEach( role -> {
                    authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
                });
                return new User(user.getUsername(), user.getPassword(), authorities);
            }
        }));}
}
