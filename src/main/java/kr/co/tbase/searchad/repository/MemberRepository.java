package kr.co.tbase.searchad.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.tbase.searchad.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>{
	public Member save(Member member);
	
	public Page<Member> findAll(Pageable pageable);
	
	public Member findByUid(String uid);

	public Page<Member> findByNameContaining(String name, Pageable pageable);

	public void deleteById(int id);
}
