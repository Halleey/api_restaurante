package card.cardapio.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@EnableWebSecurity
@Configuration
public class WebConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity  security) throws Exception {
        security
                .csrf(AbstractHttpConfigurer::disable).
                cors().and().
                authorizeHttpRequests(authorize ->
                        authorize.
                                requestMatchers(HttpMethod.GET, "/food" ).permitAll()
                                .requestMatchers(HttpMethod.POST,"/public/**").permitAll()
                                .requestMatchers(HttpMethod.PATCH,"/public/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/food/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.POST, "/enter/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE,"/public/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/paypal/**").authenticated()
                                .requestMatchers(HttpMethod.PATCH, "/api/paypal/**").authenticated()
                                .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthorizationFilter(),
                        UsernamePasswordAuthenticationFilter.class);

        return security.build();

    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authentication) throws Exception {
        return  authentication.getAuthenticationManager();
    }
}
