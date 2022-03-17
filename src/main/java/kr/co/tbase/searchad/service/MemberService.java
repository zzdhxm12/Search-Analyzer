package kr.co.tbase.searchad.service;

import java.util.HashSet;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.tbase.searchad.entity.Member;
import kr.co.tbase.searchad.repository.MemberRepository;

@Service
public class MemberService {
	@Autowired
	private MemberRepository repository;
	
//	회원가입
	public boolean save(Member member) {
		if(repository.findByUid(member.getUid()) != null) return false; 
		
		// ID 영문 유효성 검사
		boolean checkIDEng = Pattern.matches("^[a-zA-Z]*$", member.getUid());
		if(!checkIDEng) return false;
		if(member.getUid().length() > 10) return false;
		
		// Password 유효성 검사
		boolean checkPW = Pattern.matches("^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", member.getPw());
		if(!checkPW) return false;
		if(member.getPw().length() < 8) return false;
		String[] arrPw = member.getPw().split("");
		HashSet<String> setPw = new HashSet<>();
		for(int i=0;i<arrPw.length;i++) {
			setPw.add(arrPw[i]);
		}
		if(arrPw.length != setPw.size()) return false;
		
		repository.save(member);
		
		return true;
	}

	public Page<Member> findAll(Pageable pageable) {
		Page<Member> list = repository.findAll(pageable);
		
		return list;
	}
	
	public Member findByUid(String uid) {
		Member member = repository.findByUid(uid);
		
		return member;
	}

	public Page<Member> findByNameContaining(String name, Pageable pageable) {
		Page<Member> list = repository.findByNameContaining(name ,pageable);

		return list;
	}

	public void deleteById(int id) {
		repository.deleteById(id);
		
	}

}
