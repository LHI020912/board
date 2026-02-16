package service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import entity.Board;
import entity.BoardList;
import entity.BoardOne;
import repository.BoardRepository;

@Service // 핵심로직 명시
@Transactional
public class BoardService {
	private final BoardRepository boardRepository;
	
	
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	
	// BoardForm(form)에 담긴 데이터를 Board(entity)에 옮겨 DB에 담기
	public void writeBoard(BoardForm boardForm) {
		Board board = new Board(); // DB 저장용 객체 생성
		board.setTitle(boardForm.getTitle());
		board.setAuthor(boardForm.getAuthor());
		board.setContent(boardForm.getContent());
		
		boardRepository.save(board); // 영구저장
	}
	
	// 모든 게시글 조회
	public Page<BoardList> findBoardList(int page){
		// PageRequest.of(페이지 번호, 한 페이지 개수)
		Pageable pageble = PageRequest.of(page, 10);
		// Repository에 정의한 메서드 호출 (findBoardListWithAuthorLoginId)
        return boardRepository.findAllBy(pageable);
    }

    // 특정 게시글 조회
    public BoardOne findBoardOne(Long boardId) {
        return boardRepository.findByBoardId(boardId);
    }

    // 조회수 업데이트
    public void viewBoardOne(Long boardId) {
        boardRepository.incrementViewCount(boardId);
        // incrementViewCount: 조회수 1씩 증가 + 동시성 처리 + 캐시활용
    }
    
    // 글 수정
    public void updateBoard(Long boardId, BoardForm boardForm) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found"));
        // orElseThrow: Optional의 인자가 null일 경우 예외처리
        board.setTitle(boardForm.getTitle());
        board.setContent(boardForm.getContent());
        board.setAuthor(boardForm.getAuthor());

        boardRepository.save(board);
    }
    
    // 글 삿제
    public void delete(int boardId) {
    	boardRepository.deleteById(boardId);
    }
}
