/**
 * Copyright(c) team-lab</br>
 */
package com.isystk.sample.common.constants;

import java.io.File;

import com.isystk.sample.common.image.ImageInfo;
import com.isystk.sample.common.image.ImageSuffix;
import com.isystk.sample.common.image.procedure.BasicImageProcedure;
import com.isystk.sample.common.image.procedure.FixedWidthImageProcedure;
import com.isystk.sample.common.image.procedure.ImageProcedure;

/**
 * 表示する画像の種類を表すenum.<br>
 *
 * @author iseyoshitaka
 */
public enum ImageType {

    // オリジナル縦横比
    ORIGINAL(new FixedWidthImageProcedure(0, 0, ImageSuffix.ORIGINAL.getSuffix())),
    ORIGINAL_20(new FixedWidthImageProcedure(0, 20, ImageSuffix.ORIGINAL.getSuffix())),
    ORIGINAL_30(new FixedWidthImageProcedure(0, 30, ImageSuffix.ORIGINAL.getSuffix())),
    ORIGINAL_194(new FixedWidthImageProcedure(194, 0, ImageSuffix.ORIGINAL.getSuffix())),
    ORIGINAL_200(new FixedWidthImageProcedure(200, 0, ImageSuffix.ORIGINAL.getSuffix())),
    ORIGINAL_230(new FixedWidthImageProcedure(230, 0, ImageSuffix.ORIGINAL.getSuffix())),
    ORIGINAL_300(new FixedWidthImageProcedure(300, 0, ImageSuffix.ORIGINAL.getSuffix())),
    ORIGINAL_310(new FixedWidthImageProcedure(310, 0, ImageSuffix.ORIGINAL.getSuffix())),
    ORIGINAL_400(new FixedWidthImageProcedure(400, 0, ImageSuffix.ORIGINAL.getSuffix())),
    ORIGINAL_600(new FixedWidthImageProcedure(600, 0, ImageSuffix.ORIGINAL.getSuffix())),
    ORIGINAL_700(new FixedWidthImageProcedure(700, 0, ImageSuffix.ORIGINAL.getSuffix())),
    ORIGINAL_960(new FixedWidthImageProcedure(960, 0, ImageSuffix.ORIGINAL.getSuffix())),

    // 正方形_1
    SQUARE(new BasicImageProcedure(0, 0, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_60(new BasicImageProcedure(60, 60, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_70(new BasicImageProcedure(70, 70, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_72(new BasicImageProcedure(72, 72, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_80(new BasicImageProcedure(80, 80, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_84(new BasicImageProcedure(84, 84, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_85(new BasicImageProcedure(85, 85, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_86(new BasicImageProcedure(86, 86, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_88(new BasicImageProcedure(88, 88, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_90(new BasicImageProcedure(90, 90, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_92(new BasicImageProcedure(92, 92, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_98(new BasicImageProcedure(98, 98, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_100(new BasicImageProcedure(100, 100, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_110(new BasicImageProcedure(110, 110, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_115(new BasicImageProcedure(115, 115, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_120(new BasicImageProcedure(120, 120, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_130(new BasicImageProcedure(130, 130, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_135(new BasicImageProcedure(135, 135, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_140(new BasicImageProcedure(140, 140, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_143(new BasicImageProcedure(143, 143, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_144(new BasicImageProcedure(144, 144, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_150(new BasicImageProcedure(150, 150, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_154(new BasicImageProcedure(154, 154, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_155(new BasicImageProcedure(155, 155, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_160(new BasicImageProcedure(160, 160, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_165(new BasicImageProcedure(165, 165, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_170(new BasicImageProcedure(170, 170, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_175(new BasicImageProcedure(175, 175, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_176(new BasicImageProcedure(176, 176, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_190(new BasicImageProcedure(190, 190, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_200(new BasicImageProcedure(200, 200, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_225(new BasicImageProcedure(225, 225, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_230(new BasicImageProcedure(230, 230, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_260(new BasicImageProcedure(260, 260, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_270(new BasicImageProcedure(270, 270, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_280(new BasicImageProcedure(280, 280, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_290(new BasicImageProcedure(290, 290, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_400(new BasicImageProcedure(400, 400, ImageSuffix.SQUARE.getSuffix())),
    SQUARE_600(new BasicImageProcedure(600, 600, ImageSuffix.SQUARE.getSuffix())),

    // 正方形_1(L)
    SQUARE_L(new BasicImageProcedure(500, 500, ImageSuffix.SQUARE_L.getSuffix())),

    // 正方形_1(M)
    SQUARE_M(new BasicImageProcedure(200, 200, ImageSuffix.SQUARE_M.getSuffix())),
    SQUARE_M_150(new BasicImageProcedure(150, 150, ImageSuffix.SQUARE_M.getSuffix())),

    // 正方形_1(S)
    SQUARE_S(new BasicImageProcedure(100, 100, ImageSuffix.SQUARE_S.getSuffix())),
    SQUARE_S_85(new BasicImageProcedure(85, 85, ImageSuffix.SQUARE_S.getSuffix())),
    SQUARE_S_88(new BasicImageProcedure(88, 88, ImageSuffix.SQUARE_S.getSuffix())),
    SQUARE_S_92(new BasicImageProcedure(92, 92, ImageSuffix.SQUARE_S.getSuffix())),
    SQUARE_S_130(new BasicImageProcedure(130, 130, ImageSuffix.SQUARE_S.getSuffix())),


    // 4対3
    // スマホ(横幅360)
    SD(new BasicImageProcedure(0, 0, ImageSuffix.SD.getSuffix())),
    SD_60(new BasicImageProcedure(60, 45, ImageSuffix.SD.getSuffix())),
    SD_70(new BasicImageProcedure(70, 52, ImageSuffix.SD.getSuffix())),
    SD_72(new BasicImageProcedure(72, 54, ImageSuffix.SD.getSuffix())),
    SD_78(new BasicImageProcedure(78, 59, ImageSuffix.SD.getSuffix())),
    SD_80(new BasicImageProcedure(80, 60, ImageSuffix.SD.getSuffix())),
    SD_88(new BasicImageProcedure(88, 66, ImageSuffix.SD.getSuffix())),
    SD_90(new BasicImageProcedure(90, 77, ImageSuffix.SD.getSuffix())),
    SD_100(new BasicImageProcedure(100, 75, ImageSuffix.SD.getSuffix())),
    SD_105(new BasicImageProcedure(105, 79, ImageSuffix.SD.getSuffix())),
    SD_112(new BasicImageProcedure(112, 84, ImageSuffix.SD.getSuffix())),
    SD_120(new BasicImageProcedure(120, 90,  ImageSuffix.SD.getSuffix())),
    SD_124(new BasicImageProcedure(124, 93,  ImageSuffix.SD.getSuffix())),
    SD_128(new BasicImageProcedure(128, 96,  ImageSuffix.SD.getSuffix())),
    SD_134(new BasicImageProcedure(134, 100, ImageSuffix.SD.getSuffix())),
    SD_136(new BasicImageProcedure(136, 102, ImageSuffix.SD.getSuffix())),
    SD_138(new BasicImageProcedure(138, 106, ImageSuffix.SD.getSuffix())),
    SD_140(new BasicImageProcedure(140, 105, ImageSuffix.SD.getSuffix())),
    SD_143(new BasicImageProcedure(143, 107, ImageSuffix.SD.getSuffix())),
    SD_144(new BasicImageProcedure(144, 108, ImageSuffix.SD.getSuffix())),
    SD_145(new BasicImageProcedure(145, 109, ImageSuffix.SD.getSuffix())),
    SD_148(new BasicImageProcedure(148, 111, ImageSuffix.SD.getSuffix())),
    SD_151(new BasicImageProcedure(151, 113, ImageSuffix.SD.getSuffix())),
    SD_152(new BasicImageProcedure(152, 114, ImageSuffix.SD.getSuffix())),
    SD_155(new BasicImageProcedure(155, 116, ImageSuffix.SD.getSuffix())),
    SD_158(new BasicImageProcedure(158, 119, ImageSuffix.SD.getSuffix())),
    SD_160(new BasicImageProcedure(160, 120, ImageSuffix.SD.getSuffix())),
    SD_168(new BasicImageProcedure(168, 126, ImageSuffix.SD.getSuffix())),
    SD_170(new BasicImageProcedure(170, 126, ImageSuffix.SD.getSuffix())),
    SD_173(new BasicImageProcedure(173, 130, ImageSuffix.SD.getSuffix())),
    SD_176(new BasicImageProcedure(176, 131, ImageSuffix.SD.getSuffix())),
    SD_180(new BasicImageProcedure(180, 135, ImageSuffix.SD.getSuffix())),
    SD_186(new BasicImageProcedure(186, 138, ImageSuffix.SD.getSuffix())),
    SD_195(new BasicImageProcedure(195, 146, ImageSuffix.SD.getSuffix())),
    SD_200(new BasicImageProcedure(200, 150, ImageSuffix.SD.getSuffix())),
    SD_210(new BasicImageProcedure(210, 160, ImageSuffix.SD.getSuffix())),
    SD_212(new BasicImageProcedure(212, 159, ImageSuffix.SD.getSuffix())),
    SD_220(new BasicImageProcedure(220, 165, ImageSuffix.SD.getSuffix())),
    SD_238(new BasicImageProcedure(238, 178, ImageSuffix.SD.getSuffix())),
    SD_240(new BasicImageProcedure(240, 180, ImageSuffix.SD.getSuffix())),
    SD_248(new BasicImageProcedure(248, 186, ImageSuffix.SD.getSuffix())),
    SD_250(new BasicImageProcedure(250, 187, ImageSuffix.SD.getSuffix())),
    SD_252(new BasicImageProcedure(252, 188, ImageSuffix.SD.getSuffix())),
    SD_255(new BasicImageProcedure(255, 192, ImageSuffix.SD.getSuffix())),
    SD_260(new BasicImageProcedure(260, 195, ImageSuffix.SD.getSuffix())),
    SD_265(new BasicImageProcedure(265, 199, ImageSuffix.SD.getSuffix())),
    SD_270(new BasicImageProcedure(270, 202, ImageSuffix.SD.getSuffix())),
    SD_288(new BasicImageProcedure(288, 216, ImageSuffix.SD.getSuffix())),
    SD_292(new BasicImageProcedure(292, 219, ImageSuffix.SD.getSuffix())),
    SD_297(new BasicImageProcedure(297, 220, ImageSuffix.SD.getSuffix())),
    SD_300(new BasicImageProcedure(300, 225, ImageSuffix.SD.getSuffix())),
    SD_304(new BasicImageProcedure(304, 228, ImageSuffix.SD.getSuffix())),
    SD_320(new BasicImageProcedure(320, 240, ImageSuffix.SD.getSuffix())),
    SD_360(new BasicImageProcedure(360, 270, ImageSuffix.SD.getSuffix())),
    SD_380(new BasicImageProcedure(380, 285, ImageSuffix.SD.getSuffix())),
    SD_400(new BasicImageProcedure(400, 300, ImageSuffix.SD.getSuffix())),
    SD_448(new BasicImageProcedure(448, 336, ImageSuffix.SD.getSuffix())),
    SD_460(new BasicImageProcedure(460, 345, ImageSuffix.SD.getSuffix())),
    SD_600(new BasicImageProcedure(600, 450, ImageSuffix.SD.getSuffix())),
    SD_696(new BasicImageProcedure(696, 522, ImageSuffix.SD.getSuffix())),

    // 4対3(S)
    SD_S(new BasicImageProcedure(176, 132, ImageSuffix.SD_S.getSuffix())),
    SD_S_56(new BasicImageProcedure(56, 42, ImageSuffix.SD_S.getSuffix())),
    SD_S_60(new BasicImageProcedure(60, 45, ImageSuffix.SD_S.getSuffix())),
    SD_S_61(new BasicImageProcedure(61, 45, ImageSuffix.SD_S.getSuffix())),
    SD_S_70(new BasicImageProcedure(70, 52, ImageSuffix.SD_S.getSuffix())),
    SD_S_72(new BasicImageProcedure(72, 54, ImageSuffix.SD_S.getSuffix())),
    SD_S_80(new BasicImageProcedure(80, 60, ImageSuffix.SD_S.getSuffix())),
    SD_S_88(new BasicImageProcedure(88, 66, ImageSuffix.SD_S.getSuffix())),
    SD_S_92(new BasicImageProcedure(92, 68, ImageSuffix.SD_S.getSuffix())),
    SD_S_94(new BasicImageProcedure(94, 71, ImageSuffix.SD_S.getSuffix())),
    SD_S_100(new BasicImageProcedure(100, 75, ImageSuffix.SD_S.getSuffix())),
    SD_S_110(new BasicImageProcedure(110, 82, ImageSuffix.SD_S.getSuffix())),
    SD_S_105(new BasicImageProcedure(105, 79, ImageSuffix.SD_S.getSuffix())),
    SD_S_120(new BasicImageProcedure(120, 90, ImageSuffix.SD_S.getSuffix())),
    SD_S_128(new BasicImageProcedure(128, 96,  ImageSuffix.SD_S.getSuffix())),
    SD_S_130(new BasicImageProcedure(130, 98,  ImageSuffix.SD_S.getSuffix())),
    SD_S_134(new BasicImageProcedure(134, 100, ImageSuffix.SD_S.getSuffix())),
    SD_S_140(new BasicImageProcedure(140, 105, ImageSuffix.SD_S.getSuffix())),
    SD_S_144(new BasicImageProcedure(144, 108, ImageSuffix.SD_S.getSuffix())),
    SD_S_145(new BasicImageProcedure(145, 109, ImageSuffix.SD_S.getSuffix())),
    SD_S_148(new BasicImageProcedure(148, 111, ImageSuffix.SD_S.getSuffix())),
    SD_S_150(new BasicImageProcedure(150, 112, ImageSuffix.SD_S.getSuffix())),
    SD_S_151(new BasicImageProcedure(151, 113, ImageSuffix.SD_S.getSuffix())),
    SD_S_152(new BasicImageProcedure(152, 114, ImageSuffix.SD_S.getSuffix())),
    SD_S_154(new BasicImageProcedure(154, 116, ImageSuffix.SD_S.getSuffix())),
    SD_S_160(new BasicImageProcedure(160, 120, ImageSuffix.SD_S.getSuffix())),
    SD_S_163(new BasicImageProcedure(163, 122, ImageSuffix.SD_S.getSuffix())),
    SD_S_168(new BasicImageProcedure(168, 126, ImageSuffix.SD_S.getSuffix())),
    SD_S_200(new BasicImageProcedure(200, 150, ImageSuffix.SD_S.getSuffix())),
    SD_S_400(new BasicImageProcedure(400, 300, ImageSuffix.SD_S.getSuffix())),

    // 4対3(L)
    SD_L(new BasicImageProcedure(696, 522, ImageSuffix.SD_L.getSuffix())),
    SD_L_212(new BasicImageProcedure(212, 158, ImageSuffix.SD_L.getSuffix())),
    SD_L_297(new BasicImageProcedure(297, 220, ImageSuffix.SD_L.getSuffix())),
    SD_L_300(new BasicImageProcedure(300, 225, ImageSuffix.SD_L.getSuffix())),
    SD_L_310(new BasicImageProcedure(310, 230, ImageSuffix.SD_L.getSuffix())),
    SD_L_320(new BasicImageProcedure(320, 240, ImageSuffix.SD_L.getSuffix())),
    SD_L_448(new BasicImageProcedure(448, 336, ImageSuffix.SD_L.getSuffix())),
    SD_L_500(new BasicImageProcedure(500, 370, ImageSuffix.SD_L.getSuffix())),
    SD_L_560(new BasicImageProcedure(560, 420, ImageSuffix.SD_L.getSuffix())),
    SD_L_696(new BasicImageProcedure(696, 522, ImageSuffix.SD_L.getSuffix())),

    // 4対3(LL)
    SD_LL(new BasicImageProcedure(900, 675, ImageSuffix.SD_LL.getSuffix())),
    SD_LL_292(new BasicImageProcedure(292, 219, ImageSuffix.SD_LL.getSuffix())),
    SD_LL_120(new BasicImageProcedure(120, 90, ImageSuffix.SD_LL.getSuffix())),
    SD_LL_200(new BasicImageProcedure(200, 150, ImageSuffix.SD_LL.getSuffix())),
    SD_LL_300(new BasicImageProcedure(300, 225, ImageSuffix.SD_LL.getSuffix()));

    /**
     * コンストラクタ。
     *
     * @param imageProcedure プロシージャ
     */
    ImageType(final ImageProcedure imageProcedure) {
	this.imageProcedure = imageProcedure;
    }

    public void cropZoom(File file, File dst, Integer x1, Integer y1, Integer x2, Integer y2, Integer width, Integer height) throws Exception {
	this.imageProcedure.cropZoom(file, dst, x1, y1, x2, y2, width, height);
    }

    /**
     * @see com.isystk.sample.common.image.procedure.ImageProcedure#convert(com.isystk.sample.common.image.ImageInfo,
     *      java.io.File) {@inheritDoc}
     */
    public void convert(ImageInfo img, File workFile) throws Exception {
        this.imageProcedure.convert(img, workFile);
    }

    /**
     * @see com.isystk.sample.common.image.procedure.ImageProcedure#getFileName(com.isystk.sample.common.image.ImageInfo)
     *      {@inheritDoc}
     */
    public String getFileName(ImageInfo img) {
        return this.imageProcedure.getFileName(img);
    }

    /**
     * @see com.isystk.sample.common.image.procedure.ImageProcedure#getFilePath(com.isystk.sample.common.image.ImageInfo)
     *      {@inheritDoc}
     */
    public String getFilePath(ImageInfo img) {
        return this.imageProcedure.getFilePath(img);
    }

    /**
     * @see com.isystk.sample.common.image.procedure.ImageProcedure#getHeight()
     *      {@inheritDoc}
     */
    public int getHeight() {
        return this.imageProcedure.getHeight();
    }

    /**
     * NO IMAGE画像のパスを取得する。
     * @return NO IMAGE画像のパス
     */
    public String getNoImagePath() {
	return "/img/img_noimage_m.jpg";
    }

    /**
     * @see com.isystk.sample.common.image.procedure.ImageProcedure#getSuffix()
     *      {@inheritDoc}
     */
    public String getSuffix() {
        return this.imageProcedure.getSuffix();
    }

    /**
     * @see com.isystk.sample.common.image.procedure.ImageProcedure#getUri(com.isystk.sample.common.image.ImageInfo)
     *      {@inheritDoc}
     */
    public String getUri(ImageInfo img) {
        return this.imageProcedure.getUri(img);
    }

    /**
     * @see com.isystk.sample.common.image.procedure.ImageProcedure#getWidth()
     *      {@inheritDoc}
     */
    public int getWidth() {
        return this.imageProcedure.getWidth();
    }

    /**
     * @see com.isystk.sample.common.image.procedure.ImageProcedure#supported(com.isystk.sample.common.image.ImageInfo)
     * {@inheritDoc}
     */
    public boolean supported(ImageInfo img) throws Exception {
        return imageProcedure.supported(img);
    }

    /**
     * プロシージャ
     */
    private final ImageProcedure imageProcedure;

}
