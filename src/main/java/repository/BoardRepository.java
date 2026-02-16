package repository;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import entity.Board;
import entity.BoardList;
import entity.BoardOne;

@Repository
public interface BoardRepository  extends JpaRepository<Board, Integer>{
	
	@Query("SELECT b.boardId AS boardId, b.title AS title, u.loginId AS loginId, b.createdDate AS createdDate, b.viewCount AS viewCount"+
			"FROM Board b"+ "JOIN User u on b.authorId = u.userId")
	Page<BoardList> findBoardListWithAuthorLoginId(Pageable pageable); // 페이지네이션 같은 기능
	// 정렬/페이징 조건을 전달받는 파라미터
	
	BoardOne findByBoardId(Integer boardId); // 게시물 상세보기
	
	@Modifying // 단순 조회가 아닌 데이터 변경 작업 명시
    @Query("UPDATE Board b SET b.viewCount = b.viewCount + 1 WHERE b.boardId = :boardId")
    @Transactional // 실행 중 오류시 DB를 이전 상태로 되돌림(원자성 / Rollback)
    void incrementViewCount(@Param("boardId") Integer boardId);// :boardId 자리에 메서드 Param을 이용해 id 숫자 넣기
}
