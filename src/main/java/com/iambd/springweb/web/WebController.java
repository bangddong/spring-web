package com.iambd.springweb.web;

import com.iambd.springweb.service.PostsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class WebController {

    private PostsService postsService;

    // 테스트 메인
    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("posts",postsService.getPosts());
        return "main";
    }

    // 테스트 게시글 목록
    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("posts",postsService.getPosts());
        model.addAttribute("totCnt",postsService.getPosts().size());

        return "index";
    }

    // 테스트 게시글
    @GetMapping("/post/{id}")
    public String getPost(Model model, @PathVariable long id) {
        model.addAttribute("post",postsService.getPost(id));

        return "index";
    }
}
