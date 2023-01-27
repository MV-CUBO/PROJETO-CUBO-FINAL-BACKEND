package br.com.mv.APIHealth.config;

import br.com.mv.APIHealth.exception.JwtAuthenticationEntryPoint;
import br.com.mv.APIHealth.security.libs.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig {
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()


                //configs routes patients authorization
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/patients/**").hasAnyRole("PATIENT", "DOCTOR", "NURSE", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/patients/**").hasAnyRole("DOCTOR", "NURSE", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/patients/**").hasAnyRole("DOCTOR", "NURSE", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/patients/**").hasAnyRole("DOCTOR", "NURSE", "ADMIN")

                //configs routes doctor authorization
                .antMatchers(HttpMethod.GET,"/api/doctor/**").hasAnyRole("DOCTOR", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/doctor/**").hasAnyRole("DOCTOR", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/doctor/**").hasAnyRole("DOCTOR", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/doctor/**").hasAnyRole("DOCTOR", "ADMIN")

                //configs routes nurse authorization
                .antMatchers(HttpMethod.GET,"/api/nurse/**").hasAnyRole("DOCTOR","NURSE", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/doctor/**").hasAnyRole("DOCTOR", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/doctor/**").hasAnyRole("DOCTOR", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/doctor/**").hasAnyRole("DOCTOR", "ADMIN")

                //configs routes pep authorization
                .antMatchers(HttpMethod.GET,"/api/pep/**").hasAnyRole("DOCTOR", "PATIENT", "NURSE", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/pep/**").hasAnyRole("DOCTOR", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/pep/**").hasAnyRole("DOCTOR", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/pep/**").hasAnyRole("DOCTOR", "ADMIN")

                .antMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .antMatchers("/api/roles/**", "/api/users/**").permitAll()
                .anyRequest().authenticated()
                .and().cors()
                .and().exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
