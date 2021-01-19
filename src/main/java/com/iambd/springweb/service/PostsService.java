package com.iambd.springweb.service;

import com.iambd.springweb.common.Constants;
import com.iambd.springweb.domain.posts.Posts;
import com.iambd.springweb.domain.posts.PostsRepository;
import com.iambd.springweb.dto.posts.PostsMainResponseDto;
import com.iambd.springweb.dto.posts.PostsSaveRequestDto;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostsService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private PostsRepository postsRepository;

    @Transactional // 트랜잰션, 로직 실행 중 에러 발생시 모든 작업 롤백, 즉 커밋을 하지 않음.
    public void savePost(PostsSaveRequestDto dto) {
        LocalDate today = LocalDate.now();
        String todayPostPath = today.getYear() + "/" + today.getMonthValue() + "/" + today.getDayOfMonth() + "/";
        Long postId = postsRepository.save(dto.toEntity()).getId();
        File postFolder = new File(Constants.POST_DIR_PATH + todayPostPath + postId);

        Pattern pattern = Pattern.compile("(?i)<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>"); //img 태그 src 추출 정규표현식
        Matcher matcher = pattern.matcher(dto.getContent());
        ArrayList<String> contentImg = new ArrayList<>(); // 게시글 이미지 경로 리스트

        int i = 0;
        while (matcher.find()) {
            contentImg.add(matcher.group(i));
            i++;
        }

        // 게시글에서 이미지 경로 추출하여 오늘날짜 + 게시글 아이디 폴더로 이동
        if(postFolder.mkdirs()) {
            for (String imgPath : contentImg) {
                log.info("imgPath : " + imgPath);
                File imgSrc = new File("C://" + imgPath);
                if (imgSrc.renameTo(new File(postFolder + "/" + imgPath.substring(imgPath.lastIndexOf("/"))))) {
                    log.info("파일 이동 성공!!!");
                } else {
                    log.info("파일 이동 실패!!!");
                }
            }
            log.info("게시글 폴더 생성!!!");
        } else {
            log.info("게시글 폴더 생성실패!!!");
        }
    }

    public void deletePost(long postId) {
        postsRepository.deleteById(postId);
    }

    @Transactional(readOnly = true)
    public List<PostsMainResponseDto> getPosts() {

        return postsRepository.findAll()
                .stream()
                .map(PostsMainResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostsMainResponseDto getPost(long id) {
        // findById의 return post가 없으면 에러 발생
        Posts post = postsRepository.findById(id).orElseThrow(() -> new IllegalAccessError("[postId : " + id + "] 해당 게시글이 존재하지 않습니다."));

        return new PostsMainResponseDto(post);
    }

}
