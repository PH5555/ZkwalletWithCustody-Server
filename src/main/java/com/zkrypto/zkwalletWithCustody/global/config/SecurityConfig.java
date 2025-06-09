package com.zkrypto.zkwalletWithCustody.global.config;

import com.zkrypto.zkwalletWithCustody.domain.member.domain.constant.Role;
import com.zkrypto.zkwalletWithCustody.global.jwt.JwtAuthenticationFilter;
import com.zkrypto.zkwalletWithCustody.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http    .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable) // 폼 로그인 비활성화
                .sessionManagement((sm) -> { // 세션 사용 안함
                    sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authorizeHttpRequests((authorizeRequest) -> {
                    // 회원가입, 로그인 관련 API는 Jwt 인증 없이 접근 가능
                    authorizeRequest.requestMatchers("/auth/**").permitAll();
                    authorizeRequest.requestMatchers("/error").permitAll();

                    // 법인 관련 API는 ADMIN만 접근 가능
//                    authorizeRequest.requestMatchers("/corporation").hasAuthority(Role.ROLE_ADMIN.toString());
//                    authorizeRequest.requestMatchers("/corporation/wallet").hasAuthority(Role.ROLE_ADMIN.toString());
                    authorizeRequest.requestMatchers("/corporation").permitAll();
                    authorizeRequest.requestMatchers("/corporation/wallet").permitAll();

                    // 트랜잭션 생성 API는 USER만 가능
                    authorizeRequest.requestMatchers(HttpMethod.POST, "/transaction").hasAuthority(Role.ROLE_USER.toString());

                    // 트랜잭션 완료 API는 ADMIN만 가능
//                    authorizeRequest.requestMatchers(HttpMethod.PUT, "/transaction").hasAuthority(Role.ROLE_ADMIN.toString());
                    authorizeRequest.requestMatchers(HttpMethod.PUT, "/transaction").permitAll();
                    authorizeRequest.requestMatchers(HttpMethod.GET, "/transaction").permitAll();

                    // Swagger 관련 설정
                    authorizeRequest.requestMatchers("/v3/api-docs/**").permitAll();
                    authorizeRequest.requestMatchers("/swagger-resources/**").permitAll();
                    authorizeRequest.requestMatchers("/swagger-ui/**").permitAll();

                    // 나머지 모든 API는 Jwt 인증 필요
                    authorizeRequest.anyRequest().authenticated();
                })
                // Http 요청에 대한 Jwt 유효성 선 검사
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}