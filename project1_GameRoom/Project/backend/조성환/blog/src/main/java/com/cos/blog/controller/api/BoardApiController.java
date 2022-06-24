package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.UserService;

@RestController
public class BoardApiController {

	@Autowired
	private BoardService boardService;
	
	
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {		
		boardService.글쓰기(board,principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);	//자바오브젝트를 JSON으로 변환해서 리턴
	}
	
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id, @AuthenticationPrincipal PrincipalDetail
			 principal){
		boardService.글삭제하기(id,principal);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 1 return 하면 정상
	}
	
	@PutMapping("/api/board/{id}")
	public   ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board){
		System.out.println("BoardApiController : update : id : " + id);
		System.out.println("BoardApiController : update : id : " + board.getTitle());
		System.out.println("BoardApiController : update : id : " + board.getContent());
		boardService.글수정하기(id, board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);	
	}

	// 데이터를 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다. 
	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto) {		
		boardService.댓글쓰기(replySaveRequestDto);	
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);	//자바오브젝트를 JSON으로 변환해서 리턴
	}
	
	@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
	public ResponseDto<Integer> replyDelete(@PathVariable int replyId){
		boardService.댓글삭제(replyId);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 1 return 하면 정상
	}
	
	
	@PutMapping("/api/board/{boardId}/reply/{replyId}")
	public  ResponseDto<Integer> replyUpdate(@PathVariable int replyId, @RequestBody Reply reply){	
		System.out.println("api들어옴");
		boardService.댓글수정하기(replyId, reply);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
}

