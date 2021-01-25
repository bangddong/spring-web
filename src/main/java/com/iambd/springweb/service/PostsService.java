package com.iambd.springweb.service;

import com.iambd.springweb.common.Constants;
import com.iambd.springweb.domain.posts.Posts;
import com.iambd.springweb.domain.posts.PostsRepository;
import com.iambd.springweb.dto.posts.PostsMainResponseDto;
import com.iambd.springweb.dto.posts.PostsSaveRequestDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        String today = LocalDate.now().toString();
        String todayPostPath = today.substring(0,today.indexOf("-")) + "/" + today.substring(today.indexOf("-") + 1).replaceAll("-","") + "/";

        Pattern pattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>"); //img 태그 src 추출 정규표현식
        Matcher matcher = pattern.matcher(dto.getPostContent());
        ArrayList<String> contentImg = new ArrayList<>(); // 게시글 이미지 경로 리스트

        Long postId = postsRepository.save(dto.toEntity()).getPostId(); // DB Save

        while (matcher.find()) {
            contentImg.add(matcher.group(1));
        }
        // /summernoteImage/39da825d-c098-40d2-9cb8-5e8141dbb9b9.JPG
        Optional<String> thumbnailPathCheck = Optional.ofNullable(contentImg.get(0));
        thumbnailPathCheck.ifPresent(thumbanilPath -> {
            thumbanilPath = Constants.TEMP_POST_DIR_PATH + thumbanilPath.substring(thumbanilPath.lastIndexOf("/"));

            String thumbnailExtension = thumbanilPath.substring(thumbanilPath.lastIndexOf("."));
            File originalFile = new File(thumbanilPath);
            double ratio = 2;
            try {
                BufferedImage oImage = ImageIO.read(originalFile); // 원본이미지
                int tWidth = (int) (oImage.getWidth() / ratio); // 생성할 썸네일이미지의 너비
                int tHeight = (int) (oImage.getHeight() / ratio); // 생성할 썸네일이미지의 높이

                BufferedImage tImage = new BufferedImage(tWidth, tHeight, BufferedImage.TYPE_3BYTE_BGR); // 썸네일이미지
                Graphics2D graphic = tImage.createGraphics();
                Image image = oImage.getScaledInstance(tWidth, tHeight, Image.SCALE_SMOOTH);
                graphic.drawImage(image, 0, 0, tWidth, tHeight, null);
                graphic.dispose(); // 리소스를 모두 해제

                File thumbnailFile = new File(Constants.POST_DIR_PATH + todayPostPath + postId);
                ImageIO.write(tImage, thumbnailExtension,thumbnailFile); // 썸네일 생성

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        File postFolder = new File(Constants.POST_DIR_PATH + todayPostPath + postId);

        // 게시글에서 이미지 경로 추출하여 오늘날짜 + 게시글 아이디 폴더로 이동
        if(postFolder.mkdirs()) {
            for (String imgPath : contentImg) {
                log.info("imgPath : " + imgPath);
                File imgSrc = new File("C://" + imgPath);
                if (imgSrc.renameTo(new File(postFolder + "/" + imgPath.substring(imgPath.lastIndexOf("/") ) ) ) ) {
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
