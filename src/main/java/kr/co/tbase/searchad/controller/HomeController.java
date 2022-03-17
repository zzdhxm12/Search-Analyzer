package kr.co.tbase.searchad.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {

    @GetMapping(value = {"", "/"})
    public String index() {

        return "/member/login";
    }
}
