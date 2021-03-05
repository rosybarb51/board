package board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import board.dto.BoardDto;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
//	아래의 주소랑 그 밑의 메서드랑 연결해주는 것이 RequestMapping 임.
	@RequestMapping("/board/openBoardList.do")
	public ModelAndView openBoardList() throws Exception {
//		/board/boardList 는 우리가 src/main/resources 의 templates 밑에 만들 것임. 
		ModelAndView mv = new ModelAndView("/board/boardList");
		
//		List 는 오류 추가할 때 java.util cnrkgkrl
		List<BoardDto> list = boardService.selectBoardList();
		mv.addObject("list", list);
		
//		리턴해서 화면에 보여줌
		return mv;
	}
}
