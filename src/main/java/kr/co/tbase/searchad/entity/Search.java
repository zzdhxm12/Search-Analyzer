package kr.co.tbase.searchad.entity;

import lombok.Getter;

@Getter
public class Search {
	private int id;
	
    private String url;
    
    private String title;
    
    private int freq;
    
    public Search() {}
	public Search(int id, String url, String title, int freq) {
		this.id = id;
		this.url = url;
		this.title = title;
		this.freq = freq;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}
    
    
}
