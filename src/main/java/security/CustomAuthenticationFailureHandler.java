package security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import repository.UserRepository;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationSuccessHandler{

	private final UserRepository userRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO 로그인 실패 시 에러 메시지와 함께 리다이렉트
        response.sendRedirect("/login?error=true");
		
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
	}
	
	
	
}
