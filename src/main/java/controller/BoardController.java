package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import entity.BoardOne;
import service.BoardService;

@Controller
@RequestMapping("/boards")
public class BoardController {
	private final BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	// 글쓰기 페이지 조회
	@GetMapping("/write")
	public String writeForm(Model model) {
		model.addAllAttributes("writeForm",new BoardForm());
		return "writeForm";
	}
	
	@PostMapping("/write")
	public String write(@ModelAttribute BoardForm boardForm) {
		boardService.writeBoard(boardForm);
		return "redirect:/boards";
	}
	
	// 게시판 페이지 조회
	@GetMapping
	public String boardPage(@RequestParam(defaultValue = "1") int page, Model model) {
		model.addAllAttributes("boardList",boardService.findBoardList(page-1));
		return "board";
	}
	
	// 특정 게시글 조회
	@GetMapping("/{boardId}")
	public String boardOne(@PathVariable int boardId, Model model) {
		BoardOne board = boardService.findOneBoard(boardId);
		
		if (board == null) {
			return "rediect:/boards";
		}
		
		// 조회수 증가
		boardService.viewBoardOne(boardId);
		model.addAllAttributes("board", board);
		return "boardOne";
		
	}
	
	// 수정페이지 조회
	@GetMapping("/{boardId}/edit")
	public String eidtForm(@PathVariable int boardId, Model model) {
		BoardOne board = boardService.findBoardList(boardId);
		model.addAttribute("board",board);
		return "editForm";
	}
	
	// 글 수정
	@PostMapping("/{boardId}/edit")
	public String edit(@PathVariable int boardId, @ModelAttribute BoardForm boardForm, RedirectAttributes redirectAttributes) {
        boardService.updateBoard(boardId, boardForm);

        redirectAttributes.addAttribute(boardId);
        return "redirect:/boards/{boardId}";
    }
	
	// 글 삭제
    @PostMapping("/{boardId}/delete")
    public String deleteOne(@PathVariable Long boardId) {
        boardService.delete(boardId);
        return "redirect:/boards";
    }
    
    // 글 북마크
	
}
