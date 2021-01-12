package com.iambd.springweb.web;

import com.iambd.springweb.service.PostsService;
import lombok.AllArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public String selectPostList(Model model) {
        model.addAttribute("posts",postsService.getPosts());
        model.addAttribute("totCnt",postsService.getPosts().size());

        return "index";
    }

    // 테스트 게시글
    @GetMapping("/post/{id}")
    public String selectPost(Model model, @PathVariable long id) {
        model.addAttribute("post",postsService.getPost(id));

        return "post/post";
    }

    // 테스트 게시글 쓰기
    @GetMapping("/post/newPost")
    public String newPost() {
        return "post/newPost";
    }

    @GetMapping("/testqwe")
    public void testqwe() {
        // 현재 package의 workspace 경로, Windows는 [ chromedriver.exe ]
        Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/chromedriver.exe");  // 현재 package의

        // WebDriver 경로 설정
        System.setProperty("webdriver.chrome.driver", path.toString());

        // WebDriver 옵션 설정
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption ( "excludeSwitches", Collections.singletonList ( "enable-automation"));
        options.setExperimentalOption ( "useAutomationExtension", false);
        options.addArguments("--start-maximized");            // 전체화면으로 실행
        options.addArguments("--disable-popup-blocking");    // 팝업 무시
        options.addArguments("--disable-default-apps");     // 기본앱 사용안함
        options.addArguments("--headless"); // 헤드리스

        // WebDriver 객체 생성
        ChromeDriver driver = new ChromeDriver( options );

        // 빈 탭 생성
        driver.executeScript("window.open('about:blank','_blank');");

        // 탭 목록 가져오기
        List<String> tabs = new ArrayList<String>(driver.getWindowHandles());

        // 첫번째 탭으로 전환
        driver.switchTo().window(tabs.get(0));

        // 웹페이지 요청
        driver.get("https://www.google.com/search?q=%EC%97%B0%EC%98%88%EC%9D%B8+%EC%82%AC%EC%A7%84&sxsrf=ALeKk0186xJO5RWW1omp0Nd1QBqZzVt2mw:1609677555560&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjJoJuX5P_tAhVyw4sBHQLLBO0Q_AUoAXoECBgQAw&biw=1920&bih=937");

        // 웹페이지에서 글제목 가져오기
//        WebElement page1_title = driver.findElementByXPath("//*[@id=\"content\"]/div[1]/div[1]/div/h1");
//        if( page1_title != null  ) {
//            System.out.println( page1_title.getText() );
//        }

        List<WebElement> imgs = driver.findElementsByTagName("img");
        for (WebElement img : imgs) {
            System.out.println(img);
        }
        // 웹페이지 소스 출력
        //System.out.println( driver.getPageSource() );

        // 탭 종료
        driver.close();

        // 두번째 탭으로 전환
        driver.switchTo().window(tabs.get(1));

        // 웹페이지 요청
//        driver.get("https://heodolf.tistory.com/102");

//        // 웹페이지에서 글제목 가져오기
//        WebElement page2_title = driver.findElementByXPath("//*[@id=\"content\"]/div[1]/div[1]/div/h1");
//        if( page1_title != null  ) {
//            System.out.println( page2_title.getText() );
//        }

        // 웹페이지 소스 출력
        //System.out.println( driver.getPageSource() );

        // 탭 종료
//        driver.close();

        // 5초 후에 WebDriver 종료
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // WebDriver 종료
            driver.quit();
        }
    }


}
