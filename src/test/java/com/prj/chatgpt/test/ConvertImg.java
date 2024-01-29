package com.prj.chatgpt.test;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ConvertImg {
    public static void main(String[] args) throws IOException {
        // 输入的RGB图像路径
        File inputImage = new File("E:/Codes/JavaBeginner/Projects/chatgpt-sdk-java_v2/docs/images/2.png");

        // 转换后的RGBA图像路径
        File outputImage = new File("E:/Codes/JavaBeginner/Projects/chatgpt-sdk-java_v2/docs/images/2.png");

        // 读取RGB图像
        BufferedImage bufferedImage = ImageIO.read(inputImage);

        // 创建一个RGBA图像
        BufferedImage rgbaImage = new BufferedImage(
                bufferedImage.getWidth(), bufferedImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );

        // 将RGB图像绘制到RGBA图像上
        Graphics2D g = rgbaImage.createGraphics();
        g.drawImage(bufferedImage, 0, 0, null);
        g.dispose();

        // 保存转换后的RGBA图像
        ImageIO.write(rgbaImage, "png", outputImage);

        System.out.println("Image converted successfully.");
    }
}
