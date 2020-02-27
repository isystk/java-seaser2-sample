package com.isystk.sample.web.userpc.s2.dto.member.post;


import java.util.List;

/**
 * 先輩カップル(投稿型) タグDto
 * 
 */
public class PostUserCategoryDto {

    /** 先輩カップル(投稿型)カテゴリーID */
    public Integer peopleUserCategoryId;

    /** カテゴリーID */
    public Integer peopleCategoryId;

    /** カテゴリー(その他) */
    public String peopleCategoryOther;
    
    /** カテゴリー名称 */
    public String peopleCategoryName;

    /** カテゴリー画像ID */
    public List<Integer> categoryImageIdList;

    /** 本文 */
    public String categoryText;

    /** 並び順 */
    public Integer sort;

    /** タグのリスト */
    public List<PostUserTagDto> categoryTagList;
}