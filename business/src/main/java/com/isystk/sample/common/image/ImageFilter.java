package com.isystk.sample.common.image;
///**
// * Copyright(c) team-lab</br>
// */
//package com.isystk.sample.common.image;
//
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//
//import javax.imageio.ImageIO;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.jhlabs.image.ContrastFilter;
//
///**
// * 画像フィルターのクラス.<br>
// * 
// * @author iseyoshitaka
// */
//public class ImageFilter {
//
//    private static final Logger logger = LoggerFactory.getLogger(ImageFilter.class);
//
//    /**
//     * 画像をFTPでサーバに一時的にアップロードする。プレビュー用
//     * 
//     * @param image 画像データ
//     * @return 画像のパス
//     */
//    public static String filter(File img) {
//	BufferedImage src;
//	BufferedImage dst = null;
//	try {
//	    src = ImageIO.read(new File("C:\\img/29323614.jpg"));
//
//	    // border
//	    //BorderFilter filter = new BorderFilter();
//	    //filter.setTopBorder(10);
//	    //filter.setLeftBorder(10);
//	    //filter.setRightBorder(10);
//	    //filter.setBottomBorder(10);
//
//	    // gray
//	    //GrayscaleFilter filter = new GrayscaleFilter();
//
//	    // glow
//	    //GlowFilter filter = new GlowFilter();
//	    //filter.setAmount(0.15f);
//	    //filter.setRadius(10);
//	    //filter.setUseAlpha(true);
//
//	    // コントラスト
//	    ContrastFilter filter = new ContrastFilter();
//	    filter.setBrightness(1.3f);
//	    filter.setContrast(1.5f);
//
//	    dst = filter.filter(src, dst);
//	    ImageIO.write(dst, "jpg", new File("C:\\img/sample4.jpg"));
//
//	    //Thumbnails.of("C:\\img/sample2.jpg").crop(Positions.CENTER).size(800, 200).keepAspectRatio(true).toFile("C:\\img/thumbnails.jpg");
//
//	} catch (IOException e1) {
//	    logger.warn("失敗したよ！");
//	}
//
//	return "";
//    }
//
//    /** ユーティリティクラスのためコンストラクタを封殺。 */
//    private ImageFilter() {
//    }
//}
