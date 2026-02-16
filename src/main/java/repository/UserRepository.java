package repository;
// 쿼리 메소드만 이용한 인터페이스
// SQL 작성X 메서드 이름만으로 자동 쿼리 생성실행
import org.springframework.stereotype.Repository;

import entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
								// User 엔터티를 관리하고 그 엔터티의 기본키 타입은 Integer
								// 이 코드로 save,findById,delete기본 DB작업 바로사용
	// t/f 타입으로 DB에 id가 존재(exists)하는지 확인
	boolean existsByLoginId(String loginId);
	
    boolean existsByEmail(String email);
    // 로그인아이디로 사용자 전체 정보 찾아오기
    User findByLoginId(String loginId);
}
