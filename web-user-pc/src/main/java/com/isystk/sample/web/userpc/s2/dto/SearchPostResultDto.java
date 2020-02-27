package com.isystk.sample.web.userpc.s2.dto;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 投稿検索結果 表示用 DTO
 *
 * @author iseyoshitaka
 */
public class SearchPostResultDto implements Serializable {

    /** シリアルバージョンID */
    private static final long serialVersionUID = -3996244721417234712L;

    /** 投稿ID */
    public Integer postId;

    /** タイトル */
    public String title;

    /** 本文 */
    public String text;

    /** 画像IDリスト */
    public List<Integer> postImageIdList;

    /** タグID名リスト */
    public List<String> postTagNameList;

    /** 投稿日 */
    public Date registTime;

}
