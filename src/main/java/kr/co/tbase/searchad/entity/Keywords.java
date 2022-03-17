package kr.co.tbase.searchad.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Id;

import javax.persistence.*;

@Getter
@Table(name = "keyword")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Keywords {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 256, nullable = false)
    private String keyword;

    @Column(nullable = false)
    private int cnt;
    
    public Keywords() {}
	public Keywords(int id, String keyword, int cnt) {
		super();
		this.id = id;
		this.keyword = keyword;
		this.cnt = cnt;
	}


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

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
    
    
}
