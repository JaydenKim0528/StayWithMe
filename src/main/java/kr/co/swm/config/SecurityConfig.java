package kr.co.swm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 설정파일
@EnableWebSecurity // 웹사이트 보안 활성화 (모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 어노테이션)
public class SecurityConfig {

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http    //authorizeHttpRequests 어떤 요청에 대해 어떤 권한이 필요한지 설정
            .authorizeHttpRequests(authorizeRequests ->
                    authorizeRequests  //.requestMatchers : 특정 URL 지정
                            .requestMatchers("/", "/sms/send", "/login", "/signUp", "/idCheck", "/signForm",
                                    "/css/**", "/js/**", "/images/**", "/fonts/**", "/scss/**").permitAll() // 특정 경로에 대한 접근 권한 허용
                            .anyRequest().authenticated()
                            // .anyRequest() : 위 URL 이외 요청은 .authenticated() 인증 필요
            )
            .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화 (테스트용)
            .formLogin((formLogin) -> formLogin
                    .loginPage("/login") // 로그인 페이지 url 설정
                    .defaultSuccessUrl("/")) // 로그인 성공시 "/"
            .logout((logout) -> logout
                    .logoutSuccessUrl("/")); // 로그아웃 성공시 "/"
    return http.build();
}


@Bean
public PasswordEncoder passwordEncoder() { // 비밀번호 암호화
        return new BCryptPasswordEncoder();
}

}

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorizeRequests ->
//                        authorizeRequests
//                                .requestMatchers("/sms/send").permitAll() // 특정 경로에 대한 접근 권한 허용
//                                .anyRequest().permitAll() // 모든 요청을 허용
//                )
//                .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화 (테스트용)
//                .formLogin((formLogin) -> formLogin
//                        .loginPage("/login") // 로그인 페이지 url 설정
//                        .defaultSuccessUrl("/")); // 로그인 성공시 "/"
//
//        return http.build();
//    }

//@Bean
// WebSecurityCustomizer : 특정 요청을 보안 처리에서 완전히 제외시키고 싶을 때 사용,
// 소셜 로그인과 같은 보안 기능이 적용되는 경로에 대해서는 이 설정을 사용하지 않는 것이 좋다
//public WebSecurityCustomizer webSecurityCustomizer() {
//    return (web) -> web.ignoring()
//            .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//}

