package kr.co.tbase.searchad.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kr.co.tbase.searchad.entity.Host;
import kr.co.tbase.searchad.entity.Keywords;
import kr.co.tbase.searchad.entity.RelatedList;
import kr.co.tbase.searchad.entity.Relatedwords;
import kr.co.tbase.searchad.entity.Search;
import kr.co.tbase.searchad.repository.HostRepository;
import kr.co.tbase.searchad.repository.RelatedRepository;
import kr.co.tbase.searchad.repository.SearchRepository;

@Service
public class SearchService {
	@Autowired
	private SearchRepository searchRepository;
	
	@Autowired
	private HostRepository hostRepository;
	
	@Autowired
	private RelatedRepository relatedRepository;

	public List<Search> searchKeyword(String keyword) {
		// 누적 검색수 갱신
		Keywords oriKeyword = searchRepository.findByKeyword(keyword);
		if(oriKeyword == null) {
			Keywords newKeyword = new Keywords();
			newKeyword.setKeyword(keyword);
			newKeyword.setCnt(1);
			searchRepository.save(newKeyword);
		} else {
			int oriCnt = oriKeyword.getCnt();
			oriKeyword.setCnt(oriCnt+1);
			searchRepository.save(oriKeyword);
		}
		
		List<Search> list = new ArrayList<Search>();
		
		String addr = "https://www.googleapis.com/customsearch/v1?key={GoogleAPIKEY}="+keyword;
		try {
			URL url = new URL(addr);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Accept", "application/json");
	        BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));

	        String line = "";
	        String result = "";
	        
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
	        
	        System.out.println(result);
	        
	        JsonParser parser = new JsonParser();
	        JsonElement element = parser.parse(result);
	        JsonObject object = element.getAsJsonObject();
	        JsonArray items = object.getAsJsonArray("items");
	        
	        for(int i=0;i<items.size();i++) {
	        	object = (JsonObject) items.get(i);
	        	String title = object.get("title").getAsString();
	        	String link = object.get("formattedUrl").getAsString();
	        	String snippet = object.get("snippet").getAsString();
	        	String displayLink = object.get("displayLink").getAsString();
	        	
	        	Host host = hostRepository.findByName(displayLink);
	        	if(host == null) {
	        		Host newHost = new Host();
	        		newHost.setKeyword(keyword);
	        		newHost.setName(displayLink);
	        		newHost.setCnt(1);
	        		hostRepository.save(newHost);
	        	} else {
	        		int oriCnt = host.getCnt();
	        		host.setCnt(oriCnt+1);
	        		hostRepository.save(host);
	        	}
	        	
	        	String[] over = snippet.split(" ");
	        	int overCnt = 0;
	        	for(int j=0;j<over.length;j++) {
	        		over[j] = over[j].replace(",", "");
	        		
	        		Relatedwords rWord = relatedRepository.findByKeywordAndRelated(keyword, over[j]);
	        		if(rWord == null) {
	        			Relatedwords newRWord = new Relatedwords();
	        			newRWord.setKeyword(keyword);
	        			newRWord.setRelated(over[j]);
	        			newRWord.setCnt(1);
	        			relatedRepository.save(newRWord);
	        		} else {
	        			int oriRwordCnt = rWord.getCnt();
	        			rWord.setCnt(oriRwordCnt+1);
	        			relatedRepository.save(rWord);
	        		}
	        		
	        		if(over[j].contains(keyword)) overCnt++;
	        	}
	        	
	        	list.add(new Search(i+1, link, title, overCnt));
	        }

	        conn.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public Page<Host> searchHost(String keyword, String sort, Pageable pageable) {
		Page<Host> list = hostRepository.findByKeywordContainingOrderByCntAsc(keyword, pageable);
		
		if(sort.equals("asc")) {
			list = hostRepository.findByKeywordContainingOrderByCntAsc(keyword, pageable);
		} else if(sort.equals("desc")) {
			list = hostRepository.findByKeywordContainingOrderByCntDesc(keyword, pageable);
		}

		return list;
	}

	public List<RelatedList> searchRelated(String keyword, Pageable pageable) {
		List<RelatedList> list = new ArrayList<RelatedList>();
		
		Page<Keywords> keywordPage = searchRepository.findByKeywordContaining(keyword, pageable);
		List<Keywords> keywordList = keywordPage.getContent();
		
		for(int i=0;i<keywordList.size();i++) {
			StringBuilder sb = new StringBuilder();
			
			List<Relatedwords> relatedList = new ArrayList<Relatedwords>();
			relatedRepository.findByKeyword(keywordList.get(i).getKeyword()).forEach(e -> relatedList.add(e));
			
			for(int j=0;j<relatedList.size();j++) {
				if(relatedList.get(j).getCnt()/keywordList.get(i).getCnt() > 0.5) {
					sb.append(relatedList.get(j).getRelated());
					if(j < relatedList.size()-1) sb.append(", ");
				}
			}
			
			list.add(new RelatedList(keywordList.get(i).getKeyword(), sb.toString()));
		}
		
		return list;
	}

	public Page<Keywords> pageRelated(String keyword, Pageable pageable) {
		Page<Keywords> keywordPage = searchRepository.findByKeywordContaining(keyword, pageable);
		
		return keywordPage;
	}
	
	
	
}
