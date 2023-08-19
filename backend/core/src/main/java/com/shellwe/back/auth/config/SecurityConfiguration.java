package com.shellwe.back.auth.config;

import com.shellwe.back.auth.filter.JwtAuthenticationFilter;
import com.shellwe.back.auth.filter.JwtVerificationFilter;
import com.shellwe.back.auth.handler.jwt.LoginAuthenticationFailureHandler;
import com.shellwe.back.auth.handler.jwt.LoginAuthenticationSuccessHandler;
import com.shellwe.back.auth.handler.jwt.TokenVerificationFailedHandler;
import com.shellwe.back.auth.handler.jwt.VerificationAuthFailedHandler;
import com.shellwe.back.auth.handler.oauth.OAuth2MemberFailureHandler;
import com.shellwe.back.auth.handler.oauth.OAuth2MemberSuccessHandler;
import com.shellwe.back.auth.jwt.JwtTokenizer;
import com.shellwe.back.auth.sevice.OAuthMemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {

    private final JwtTokenizer jwtTokenizer;
    private final OAuthMemberService oAuthMemberService;
    private final OAuth2MemberFailureHandler oAuth2MemberFailureHandler;

    public SecurityConfiguration(JwtTokenizer jwtTokenizer, OAuthMemberService oAuthMemberService, OAuth2MemberFailureHandler oAuth2MemberFailureHandler) {
        this.jwtTokenizer = jwtTokenizer;
        this.oAuthMemberService = oAuthMemberService;
        this.oAuth2MemberFailureHandler = oAuth2MemberFailureHandler;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);
            jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new LoginAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new LoginAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer);

            builder
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class);
        }
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                    .headers().frameOptions().sameOrigin()
                .and()
                    .csrf().disable()
                    .cors().configurationSource(corsConfigurationSource())
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .formLogin().disable()
                    .httpBasic().disable()
                    .exceptionHandling()
                    .authenticationEntryPoint(new TokenVerificationFailedHandler())
                    .accessDeniedHandler(new VerificationAuthFailedHandler())
                .and()
                    .apply(new CustomFilterConfigurer())
                .and()
                    .authorizeRequests(authorize -> authorize
                            .mvcMatchers(HttpMethod.GET, "/**").permitAll()
                            .mvcMatchers(HttpMethod.POST, "/members").permitAll()
                            .mvcMatchers(HttpMethod.POST, "/auth/login").permitAll()
                            .mvcMatchers("/**").hasAuthority("EMAIL_VERIFIED")
                            .anyRequest().authenticated()
                    )
                    .oauth2Login(oauth2 -> oauth2
                            .loginPage("/oauth2/authorization/google")
                            .successHandler(new OAuth2MemberSuccessHandler(jwtTokenizer, oAuthMemberService, oAuth2MemberFailureHandler))
                            .failureHandler(oAuth2MemberFailureHandler)
                    );

        return http.build();
    }
}
