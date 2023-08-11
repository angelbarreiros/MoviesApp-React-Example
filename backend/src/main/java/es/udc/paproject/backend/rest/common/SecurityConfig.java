package es.udc.paproject.backend.rest.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtGenerator jwtGenerator;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilter(new JwtFilter(authenticationManager(), jwtGenerator))
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users/signUp").permitAll()
                .antMatchers(HttpMethod.POST, "/users/login").permitAll()
                .antMatchers(HttpMethod.POST, "/users/loginFromServiceToken").permitAll()
                .antMatchers(HttpMethod.GET, "/cinema/billboard").permitAll()
                .antMatchers(HttpMethod.GET, "/cinema/session/*").permitAll()
                .antMatchers(HttpMethod.GET, "/cinema/movie/*").permitAll()


                .antMatchers(HttpMethod.PUT, "/users/*").hasAnyRole("USER","TICKER")
                .antMatchers(HttpMethod.POST, "/users/*/changePassword").hasAnyRole("USER","TICKER")
                .antMatchers(HttpMethod.GET, "/purchase/history").hasRole("USER")
                .antMatchers(HttpMethod.POST,"/purchase/*/buy").hasRole("USER")

                .antMatchers(HttpMethod.POST, "/purchase/delivery").hasRole("TICKER")

                .anyRequest().denyAll();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of("*"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", config);

        return source;

    }

}
