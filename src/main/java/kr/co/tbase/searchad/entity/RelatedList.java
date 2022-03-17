package kr.co.tbase.searchad.entity;

public class RelatedList {
    private String keyword;
    
    private String related;
    
    public RelatedList() {}
	public RelatedList(String keyword, String related) {
		super();
		this.keyword = keyword;
		this.related = related;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getRelated() {
		return related;
	}
	public void setRelated(String related) {
		this.related = related;
	}
 
}
