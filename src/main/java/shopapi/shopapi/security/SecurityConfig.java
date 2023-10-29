package shopapi.shopapi.security;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import shopapi.shopapi.filter.JwtAuthenticationFilter;

import java.util.Arrays;

@Configuration
//@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;


//    @Bean
//    public static PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((auth) -> auth
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults());
//        return http.build();
//    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://localhost:3000","*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
//                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.disable())
//                .cors(cors -> cors.disable())
                .authorizeHttpRequests((authz) -> authz
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
//                        .requestMatchers("/api/v1/article/user/**").hasRole("USER")
//                        .requestMatchers("/api/v1/user/seller/**","/api/v1/product/seller/**","/api/v1/article/seller/**","/api/v1/color/manager/**").hasRole("SELLER")

                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated()

                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().anyRequest().requestMatchers("/*");
//    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors(c->{
//            CorsConfigurationSource source = request -> {
//                CorsConfiguration config = new CorsConfiguration();
//                config.setAllowedOrigins(
//                        List.of("/**","http://localhost:3000","*"));
//                config.setAllowedMethods(
//                        List.of("GET","POST","PUT","DELETE"));
//                config.setAllowedHeaders(
//                        List.of("Authorization","Content-Type","Cache-Control","Access-Control-Allow-Origin","Access-Control-Allow-Methods","Access-Control-Allow-Headers"));
//
//                config.setExposedHeaders(
//                        List.of("Authorization","Content-Type","Cache-Control","Access-Control-Allow-Origin","Access-Control-Allow-Methods","Access-Control-Allow-Headers"));
//
//                return config;
//            };
//            c.configurationSource(source);
//        });
//        http.csrf().disable();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.authorizeRequests().antMatchers("/**").permitAll();
//        http.authorizeRequests().antMatchers("/login/**").permitAll();
//        http.authorizeRequests().antMatchers("/api/product/**").permitAll();
//        http.authorizeRequests().anyRequest().authenticated();
//        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
//        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception{
//        return super.authenticationManagerBean();
//    }
}


