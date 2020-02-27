package com.isystk.sample.web.userpc.s2.dto.member.people;

/**
 * 先輩カップル(投稿型) 会場セレクトボックス用 Dto
 * 
 */
public class PeopleUserWeddingDto {

    /** 式場ID OR チャペルID */
    public Integer id;

    /** 式場名 OR チャペル名 */
    public String name;

    /** チャペルフラグ */
    public boolean isChapel;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isChapel() {
        return isChapel;
    }

}