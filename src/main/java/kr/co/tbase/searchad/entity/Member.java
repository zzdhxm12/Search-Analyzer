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
@Table(name = "member")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 10, nullable = false)
    private String uid;

    @Column(nullable = false)
    private String pw;
    
    @Column(nullable = false)
    private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    
}
