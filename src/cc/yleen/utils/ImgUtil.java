package cc.yleen.utils;

//import com.sun.tools.javac.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImgUtil {
    public static Image getImage (String dir){                      // 导入图片的方法
        URL url = ImgUtil.class.getClassLoader().getResource(dir);// 创建一个链接，用于引导图片的位置
        BufferedImage img = null;                                           // 图片缓冲/加载的过渡图片
        try {
            assert url != null;
            img = ImageIO.read(url);                                          // 图片读取到文件链接
            img.setRGB(0,0,BufferedImage.TYPE_BYTE_INDEXED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;                                                                     // 返回图片
    }
//    private static void zoomImageUtils(File imageFile, String newPath, BufferedImage bufferedImage, int width, int height)
//            throws IOException{
//
//        String suffix = StringUtils.substringAfterLast(imageFile.getName(), ".");
//
//        // 处理 png 背景变黑的问题
//        if(suffix != null && (suffix.trim().toLowerCase().endsWith("png") || suffix.trim().toLowerCase().endsWith("gif"))){
//            BufferedImage to= new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//            Graphics2D g2d = to.createGraphics();
//            to = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
//            g2d.dispose();
//
//            g2d = to.createGraphics();
//            Image from = bufferedImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
//            g2d.drawImage(from, 0, 0, null);
//            g2d.dispose();
//
//            ImageIO.write(to, suffix, new File(newPath));
//        }else{
//            // 高质量压缩，其实对清晰度而言没有太多的帮助
////            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
////            tag.getGraphics().drawImage(bufferedImage, 0, 0, width, height, null);
////
////            FileOutputStream out = new FileOutputStream(newPath);    // 将图片写入 newPath
////            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
////            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
////            jep.setQuality(1f, true);    //压缩质量, 1 是最高值
////            encoder.encode(tag, jep);
////            out.close();
//
//            BufferedImage newImage = new BufferedImage(width, height, bufferedImage.getType());
//            Graphics g = newImage.getGraphics();
//            g.drawImage(bufferedImage, 0, 0, width, height, null);
//            g.dispose();
//            ImageIO.write(newImage, suffix, new File(newPath));
//        }
//    }
    public ImgUtil(){

    }
}
