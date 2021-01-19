package com.iambd.springweb.web;

import com.google.gson.JsonObject;
import com.iambd.springweb.common.Constants;
import com.iambd.springweb.dto.posts.PostsSaveRequestDto;
import com.iambd.springweb.service.PostsService;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor // 모든 필드의 인자값으로 하는 생성자 어노테이션
public class WebRestController {

    private PostsService postsService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!!";
    }

    @PostMapping("/post")
    public void savePosts(@RequestBody PostsSaveRequestDto dto) {
        postsService.savePost(dto);
    }

    @DeleteMapping("/post/{postId}")
    public void deletePosts(@PathVariable long postId) {
        postsService.deletePost(postId);
    }

    @PostMapping(value="/uploadSummernoteImageFile", produces = "application/json")
    @ResponseBody
    public JsonObject uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {

        JsonObject jsonObject = new JsonObject();

        String fileRoot = Constants.TEMP_POST_DIR_PATH;	//저장될 외부 파일 경로
        log.info("fileRoot : " + fileRoot);
        String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
        Optional<String> filName = Optional.ofNullable(originalFileName);
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자

        String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명

        File targetFile = new File(fileRoot + savedFileName);

        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장
            jsonObject.addProperty("url", "/summernoteImage/"+savedFileName);
            jsonObject.addProperty("responseCode", "success");

        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
            jsonObject.addProperty("responseCode", "error");
            e.printStackTrace();
        }

        return jsonObject;
    }

}
