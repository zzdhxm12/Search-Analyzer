package kr.co.tbase.searchad.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.tbase.searchad.entity.Member;
import kr.co.tbase.searchad.service.MemberService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {
	@Autowired
	MemberService service;
	
	@RequestMapping(value = "/join")
	public String mvJoin() {
		return "/member/join";
	}
	
	@RequestMapping(value = "/login")
	public String mvLogin() {
		return "/member/login";
	}
	
	@PostMapping(value = "/member/save")
	public String save(Member member) {
		service.save(member);
		
		return "/member/login";
	}
	
	@GetMapping(value = "/member/login")
	public String login(@RequestParam("uid") String uid, @RequestParam("pw") String pw, @PageableDefault(page = 0, size = 10) Pageable pageable, Model model, HttpSession session, HttpServletResponse response) {
		Member member = service.findByUid(uid);

		if(member != null && member.getPw().equals(pw)) {
			// session 설정
			session.setAttribute("userInfo", member);
			
			//cookie 설정
			Cookie cookie = new Cookie("uid", member.getUid());
			cookie.setPath("/");
			if("saveok".equals(uid)) {
				cookie.setMaxAge(60 * 60 * 24 * 365 * 40); // 40년간 저장
			} else {
				cookie.setMaxAge(0);
			}
			response.addCookie(cookie);

			// 관리자(admin/1234) 계정 로그인
			if(member.getUid().equals("admin")) {
				Page<Member> list = service.findAll(pageable);
				
				int pageNumber=list.getPageable().getPageNumber();
				int totalPages=list.getTotalPages();
				int pageBlock = 10;
				int startBlockPage = ((pageNumber)/pageBlock)*pageBlock+1;
				int endBlockPage = startBlockPage+pageBlock-1;  
				endBlockPage= totalPages<endBlockPage? totalPages:endBlockPage;

				model.addAttribute("startBlockPage", startBlockPage); 
				model.addAttribute("endBlockPage", endBlockPage);
				model.addAttribute("list", list);
				
				return "member/list";
			} else return "search/search";

		} else return "/member/login";
	}
	
	@GetMapping(value = "/admin/users")
	public String searchAllUser(@PageableDefault(page = 0, size = 10) Pageable pageable, Model model) {
		Page<Member> list = service.findAll(pageable);
		
		int pageNumber=list.getPageable().getPageNumber();
		int totalPages=list.getTotalPages();
		int pageBlock = 10;
		int startBlockPage = ((pageNumber)/pageBlock)*pageBlock+1;
		int endBlockPage = startBlockPage+pageBlock-1;  
		endBlockPage= totalPages<endBlockPage? totalPages:endBlockPage;

		model.addAttribute("startBlockPage", startBlockPage); 
		model.addAttribute("endBlockPage", endBlockPage);
		model.addAttribute("list", list);
		
		return "member/list";
	}
	
	@GetMapping(value = "/admin/users/search")
	public String searchUser(@RequestParam("name") String name, @PageableDefault(size = 10) Pageable pageable, Model model) {
		Page<Member> list = service.findByNameContaining(name, pageable);
		
		int pageNumber=list.getPageable().getPageNumber();
		int totalPages=list.getTotalPages();
		int pageBlock = 10;
		int startBlockPage = ((pageNumber)/pageBlock)*pageBlock+1;
		int endBlockPage = startBlockPage+pageBlock-1;  
		endBlockPage= totalPages<endBlockPage? totalPages:endBlockPage;

		model.addAttribute("startBlockPage", startBlockPage); 
		model.addAttribute("endBlockPage", endBlockPage);

		model.addAttribute("list", list);
		model.addAttribute("keyword", name);
		
		return "member/list";
	}
	
	@GetMapping(value = "/member/delete")
	public String deleteUser(@RequestParam("id") int id, @PageableDefault(page = 0, size = 10) Pageable pageable, Model model) {
		service.deleteById(id);
		
		model.addAttribute("list", service.findAll(pageable));
		
		return "member/list";
	}
}
