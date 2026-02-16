package config;
// 보안 및 설정
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import security.CustomAuthenticationFailureHandler;
import security.CustomAuthenticationSuccessHandler;

@Configuration // 스프링 컨테이너의 설정 클래스 명시
@EnableWebSecurity // 웹 보안기능 (인증 및 권한부여)활성화
@RequiredArgsConstructor // [final]이 붙은 필드에 대해 생성자 자동 의존성 주입
public class SecurityConfig {
	

    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;
    
    @Bean // 개발자가 직접 제어할 수없는 외부라이브러리(DBcrypt)스프링 컨테이너 등록
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
    	http.formLogin(auth -> auth // 폼 기반 로그인 사용의미
    			// 람다식 (스프링 시큐가 제공하는 객체 -> 세부명령 과정);
    			// (주문서 -> 주문서.메뉴선택("피자").사이즈("L")
    			
    			// 로그인 페이지 경로(Get)
    			.loginPage("/login")
    			
    			// 로그인 처리 경로(POST)
    			.loginProcessingUrl("/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
    			
    			.permitAll() // 모든사용자 접근 허용의미
    			);
    	
    	http.logout(auth -> auth
    			// 로그아웃 경로(POST)
    			.logoutUrl("/logout")
    			.logoutSuccessUrl("/boards")
    			.permitAll(false)
    			);
    	
    	return http.build();
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
}
