package com.iambd.springweb.web;

import com.iambd.springweb.domain.posts.PostsRepository;
import com.iambd.springweb.dto.posts.PostsSaveRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;

@RestController
@AllArgsConstructor // 모든 필드의 인자값으로 하는 생성자 어노테이션
public class WebRestController {

    private PostsRepository postsRepository;

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!!";
    }

    @PostMapping("/posts")
    public void savePosts(@RequestBody PostsSaveRequestDto dto) {
        postsRepository.save(dto.toEntity());
    }

    // 테스트 게시글 쓰기
    @PostMapping("/post/newPost/image")
    public String uploadImage(@RequestParam("upload")MultipartFile file) {
        String returnString = "업로드되었습니다.";

        String rootPath = "D://spring-web";
        String basePath = rootPath + "/" + "postImage";

        String filePath = basePath + "/" + file.getOriginalFilename();

        try {
            File dest = new File(filePath);
            file.transferTo(dest);
        } catch (IOException e) {
            returnString = "파일생성 실패!!!";
            e.printStackTrace();
        }

        return returnString;
    }

}
