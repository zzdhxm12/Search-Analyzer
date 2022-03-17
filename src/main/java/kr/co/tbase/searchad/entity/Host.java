package kr.co.tbase.searchad.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "host")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Host {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	@Column(nullable = false)
    private String keyword;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int cnt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
    
    
    
}
