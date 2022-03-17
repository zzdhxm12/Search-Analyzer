package kr.co.tbase.searchad.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.tbase.searchad.entity.Host;

@Repository
public interface HostRepository extends JpaRepository<Host, Long> {

	Host findByName(String displayLink);

	Page<Host> findByKeywordContainingOrderByCntAsc(String keyword, Pageable pageable);

	Page<Host> findByKeywordContainingOrderByCntDesc(String keyword, Pageable pageable);

}
