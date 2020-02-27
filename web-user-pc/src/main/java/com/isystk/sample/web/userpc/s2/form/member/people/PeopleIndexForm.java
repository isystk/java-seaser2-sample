package com.isystk.sample.web.userpc.s2.form.member.people;

import java.io.Serializable;
import org.apache.struts.upload.FormFile;

/**
 * 先輩カップルの共通処理アクションアクションフォーム
 * 
 * @author iseyoshitaka
 */
public class PeopleIndexForm implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /** アップロードする画像ファイル */
    public FormFile imageFile;

}