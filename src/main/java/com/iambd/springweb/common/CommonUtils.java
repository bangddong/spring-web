package com.iambd.springweb.common;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class CommonUtils {

    public static void getPostThumbnail(String originalFilePath, String thumbnailFilePath) throws Exception {
        String thumbanilPath = Constants.TEMP_POST_DIR_PATH + originalFilePath.substring(originalFilePath.lastIndexOf("/"));

        String thumbnailExtension = originalFilePath.substring(originalFilePath.lastIndexOf("."));
        File originalFile = new File(thumbanilPath);

        double ratio = 2;
        BufferedImage oImage = ImageIO.read(originalFile); // 원본이미지
        int tWidth = (int) (oImage.getWidth() / ratio); // 생성할 썸네일이미지의 너비
        int tHeight = (int) (oImage.getHeight() / ratio); // 생성할 썸네일이미지의 높이

        BufferedImage tImage = new BufferedImage(tWidth, tHeight, BufferedImage.TYPE_3BYTE_BGR); // 썸네일이미지
        Graphics2D graphic = tImage.createGraphics();
        Image image = oImage.getScaledInstance(tWidth, tHeight, Image.SCALE_SMOOTH);
        graphic.drawImage(image, 0, 0, tWidth, tHeight, null);
        graphic.dispose(); // 리소스를 모두 해제

         File thumbnailFile = new File(Constants.POST_DIR_PATH + thumbnailFilePath + thumbnailExtension);
         ImageIO.write(tImage, thumbnailExtension,thumbnailFile); // 썸네일 생성

    }
}
