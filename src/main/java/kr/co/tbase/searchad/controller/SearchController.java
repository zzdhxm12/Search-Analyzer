package kr.co.tbase.searchad.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.tbase.searchad.entity.Host;
import kr.co.tbase.searchad.entity.Keywords;
import kr.co.tbase.searchad.entity.RelatedList;
import kr.co.tbase.searchad.entity.Search;
import kr.co.tbase.searchad.service.SearchService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	
	@RequestMapping(value = "/search")
	public String mvSearch() {
		return "/search/search";
	}
	
	@RequestMapping(value = "/host")
	public String mvHost() {
		return "/search/host";
	}
	
	@RequestMapping(value = "/related")
	public String mvRelated() {
		return "/search/relatedwords";
	}
	
	@GetMapping(value = "/search/search")
	public String search(@RequestParam("keyword") String keyword, Model model) {
		List<Search> list = new ArrayList<Search>();
		
		list = searchService.searchKeyword(keyword);
		
		model.addAttribute("list", list);
		
		return "search/search";
	}
	
	@GetMapping(value = "/search/host")
	public String host(@RequestParam("keyword") String keyword, @RequestParam("sort") String sort, @PageableDefault(size = 10) Pageable pageable, Model model) {
		Page<Host> list = searchService.searchHost(keyword, sort, pageable);
		
		int pageNumber=list.getPageable().getPageNumber();
		int totalPages=list.getTotalPages();
		int pageBlock = 10;
		int startBlockPage = ((pageNumber)/pageBlock)*pageBlock+1;
		int endBlockPage = startBlockPage+pageBlock-1;  
		endBlockPage= totalPages<endBlockPage? totalPages:endBlockPage;

		model.addAttribute("startBlockPage", startBlockPage); 
		model.addAttribute("endBlockPage", endBlockPage);

		model.addAttribute("list", list);
		model.addAttribute("sort", sort);
		model.addAttribute("keyword", keyword);
		
		return "search/host";
	}
	
	@GetMapping(value = "/search/related")
	public String related(@RequestParam("keyword") String keyword, @PageableDefault(size = 10) Pageable pageable, Model model) {
		List<RelatedList> list = searchService.searchRelated(keyword, pageable);
		
		Page<Keywords> pageList = searchService.pageRelated(keyword, pageable);

		int pageNumber=pageList.getPageable().getPageNumber();
		int totalPages=pageList.getTotalPages();
		int pageBlock = 10;
		int startBlockPage = ((pageNumber)/pageBlock)*pageBlock+1;
		int endBlockPage = startBlockPage+pageBlock-1;  
		endBlockPage= totalPages<endBlockPage? totalPages:endBlockPage;

		model.addAttribute("startBlockPage", startBlockPage); 
		model.addAttribute("endBlockPage", endBlockPage);

		model.addAttribute("pageList", pageList);
		model.addAttribute("list", list);
		model.addAttribute("keyword", keyword);
		
		return "search/relatedwords";
	}
}
