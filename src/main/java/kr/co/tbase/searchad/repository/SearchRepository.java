package kr.co.tbase.searchad.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.tbase.searchad.entity.Keywords;

@Repository
public interface SearchRepository extends JpaRepository<Keywords, Integer>{

	public Keywords findByKeyword(String keyword);

	public Keywords save(Keywords newKeyword);

	public Page<Keywords> findByKeywordContaining(String keyword, Pageable pageable);

}
