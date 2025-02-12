package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.BoastBoardReplySaveRequestDto;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.BoastBoard;
import com.cos.blog.model.BoastBoardReply;
import com.cos.blog.service.BoastBoardService;

@RestController
public class BoastBoardApiController {

	@Autowired
	private BoastBoardService boastBoardService;
	
	
	@PostMapping("/api/boastBoard")
	public ResponseDto<Integer> save(@RequestBody BoastBoard boastBoard, @AuthenticationPrincipal PrincipalDetail principal) {		
		boastBoardService.글쓰기(boastBoard, principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);	//자바오브젝트를 JSON으로 변환해서 리턴
	}
	
	@DeleteMapping("/api/boastBoard/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id, @AuthenticationPrincipal PrincipalDetail
			 principal){
		boastBoardService.글삭제하기(id,principal);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 1 return 하면 정상
	}
	
	@PutMapping("/api/boastBoard/{id}")
	public   ResponseDto<Integer> update(@PathVariable int id, @RequestBody BoastBoard boastBoard, @AuthenticationPrincipal PrincipalDetail principal){
		System.out.println("BoastBoardApiController : update : id : " + id);
		System.out.println("BoastBoardApiController : update : id : " + boastBoard.getTitle());
		System.out.println("BoastBoardApiController : update : id : " + boastBoard.getContent());
		boastBoardService.글수정하기(id, boastBoard, principal);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);	
	}

	// 데이터를 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다. 
	@PostMapping("/api/boastBoard/{boastBoardId}/reply")
	public ResponseDto<Integer> replySave(@RequestBody BoastBoardReplySaveRequestDto boastboardreplySaveRequestDto, @AuthenticationPrincipal PrincipalDetail principal) {
		boastboardreplySaveRequestDto.setUserId(principal.getUser().getId());
		System.out.println("boastboardreplySaveRequestDto userId : " + boastboardreplySaveRequestDto.getUserId());
		System.out.println("boastboardreplySaveRequestDto boastBoardId : " + boastboardreplySaveRequestDto.getBoastBoardId());
		System.out.println("boastboardreplySaveRequestDto Content : " + boastboardreplySaveRequestDto.getContent());
		boastBoardService.댓글쓰기(boastboardreplySaveRequestDto);	
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);	//자바오브젝트를 JSON으로 변환해서 리턴
	}
	
	@DeleteMapping("/api/boastBoard/{boastBoardId}/reply/{replyId}")
	public ResponseDto<Integer> replyDelete(@PathVariable int replyId, @AuthenticationPrincipal PrincipalDetail principal){
		boastBoardService.댓글삭제(replyId, principal);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 1 return 하면 정상
	}
	
	
	@PutMapping("/api/boastBoard/{boastBoardId}/reply/{replyId}")
	public  ResponseDto<Integer> replyUpdate(@PathVariable int replyId, @RequestBody BoastBoardReply boastboardreply, @AuthenticationPrincipal PrincipalDetail principal){	
		System.out.println("api들어옴");
		boastBoardService.댓글수정하기(replyId, boastboardreply, principal);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
}

