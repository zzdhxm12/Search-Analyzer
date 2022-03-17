package kr.co.tbase.searchad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.google.gson.JsonElement;

import kr.co.tbase.searchad.entity.Keywords;
import kr.co.tbase.searchad.entity.Relatedwords;

@Repository
public interface RelatedRepository extends JpaRepository<Relatedwords, Integer> {

	public List<Relatedwords> findByKeywordContaining(String keyword);

	public Relatedwords findByKeywordAndRelated(String keyword, String string);

	public List<Relatedwords> findByKeyword(String keyword);

}
