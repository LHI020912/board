package service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import entity.User;
import form.RegisterForm;
import repository.UserRepository;

@Service // 핵심로직 명시
@Transactional
public class UserService {
	
    private final UserRepository userRepository;
    // BCryptPasswordEncoder: 비밀번호를 암호화(해시)하는 데에 사용
    private final BCryptPasswordEncoder passwordEncoder;
    
    // config/Security.java 클래스에서 @Bean으로 등록된 BCryptPasswordEncoder 의존성 주입
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean register(RegisterForm registerForm) {
    	// 아이디 중복 확인
        if (userRepository.existsByLoginId(registerForm.getLoginId()) || userRepository.existsByEmail(registerForm.getEmail())) {
            return false;
        }

        User user = new User(); // DB에 저장할 엔티티 객체생성하여 DB로 옮기기
        user.setLoginId(registerForm.getLoginId());
        user.setPassword(passwordEncoder.encode(registerForm.getPassword())); // Encoder 암호화 생성 세팅
        user.setName(registerForm.getName());
        user.setEmail(registerForm.getEmail());

        userRepository.save(user); // 최종 저장(Insert문 실행)
        return true; // 가입성공을 컨트롤러에게 전송
    }
}
