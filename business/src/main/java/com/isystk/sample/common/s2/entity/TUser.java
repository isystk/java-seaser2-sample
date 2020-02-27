package com.isystk.sample.common.s2.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * TUserエンティティクラス
 * 
 */
@Entity
@Generated(value = {"S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.EntityModelFactoryImpl"})
public class TUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /** userIdプロパティ */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(precision = 10, nullable = false, unique = true)
    public Integer userId;

    /** mailプロパティ */
    @Column(length = 256, nullable = false, unique = false)
    public String mail;

    /** passwordプロパティ */
    @Column(length = 40, nullable = false, unique = false)
    public byte[] password;

    /** familyNameプロパティ */
    @Column(length = 20, nullable = false, unique = false)
    public String familyName;

    /** nameプロパティ */
    @Column(length = 20, nullable = false, unique = false)
    public String name;

    /** familyNameKanaプロパティ */
    @Column(length = 20, nullable = false, unique = false)
    public String familyNameKana;

    /** nameKanaプロパティ */
    @Column(length = 20, nullable = false, unique = false)
    public String nameKana;

    /** zipプロパティ */
    @Column(length = 11, nullable = true, unique = false)
    public String zip;

    /** prefectureIdプロパティ */
    @Column(precision = 10, nullable = true, unique = false)
    public Integer prefectureId;

    /** areaプロパティ */
    @Column(length = 100, nullable = true, unique = false)
    public String area;

    /** addressプロパティ */
    @Column(length = 100, nullable = true, unique = false)
    public String address;

    /** buildingプロパティ */
    @Column(length = 100, nullable = true, unique = false)
    public String building;

    /** telプロパティ */
    @Column(length = 13, nullable = true, unique = false)
    public String tel;

    /** sexプロパティ */
    @Column(precision = 10, nullable = true, unique = false)
    public Integer sex;

    /** birthdayプロパティ */
    @Temporal(TemporalType.DATE)
    @Column(nullable = true, unique = false)
    public Date birthday;

    /** lastLoginTimeプロパティ */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true, unique = false)
    public Date lastLoginTime;

    /** statusプロパティ */
    @Column(precision = 10, nullable = false, unique = false)
    public Integer status;

    /** registTimeプロパティ */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, unique = false)
    public Date registTime;

    /** updateTimeプロパティ */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, unique = false)
    public Date updateTime;

    /** deleteFlgプロパティ */
    @Column(nullable = false, unique = false)
    public Boolean deleteFlg;

    /** versionプロパティ */
    @Version
    @Column(precision = 19, nullable = false, unique = false)
    public Long version;

    /** TPostList関連プロパティ */
    @OneToMany(mappedBy = "TUser")
    public List<TPost> TPostList;

    /** TUserOnetimePass関連プロパティ */
    @OneToOne(mappedBy = "TUser")
    public TUserOnetimePass TUserOnetimePass;

    /** TUserOnetimeValid関連プロパティ */
    @OneToOne(mappedBy = "TUser")
    public TUserOnetimeValid TUserOnetimeValid;

    /**
     * userIdを返します。
     * 
     * @param userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * userIdを設定します。
     * 
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * mailを返します。
     * 
     * @param mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * mailを設定します。
     * 
     * @param mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * passwordを返します。
     * 
     * @param password
     */
    public byte[] getPassword() {
        return password;
    }

    /**
     * passwordを設定します。
     * 
     * @param password
     */
    public void setPassword(byte[] password) {
        this.password = password;
    }

    /**
     * familyNameを返します。
     * 
     * @param familyName
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * familyNameを設定します。
     * 
     * @param familyName
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * nameを返します。
     * 
     * @param name
     */
    public String getName() {
        return name;
    }

    /**
     * nameを設定します。
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * familyNameKanaを返します。
     * 
     * @param familyNameKana
     */
    public String getFamilyNameKana() {
        return familyNameKana;
    }

    /**
     * familyNameKanaを設定します。
     * 
     * @param familyNameKana
     */
    public void setFamilyNameKana(String familyNameKana) {
        this.familyNameKana = familyNameKana;
    }

    /**
     * nameKanaを返します。
     * 
     * @param nameKana
     */
    public String getNameKana() {
        return nameKana;
    }

    /**
     * nameKanaを設定します。
     * 
     * @param nameKana
     */
    public void setNameKana(String nameKana) {
        this.nameKana = nameKana;
    }

    /**
     * zipを返します。
     * 
     * @param zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * zipを設定します。
     * 
     * @param zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * prefectureIdを返します。
     * 
     * @param prefectureId
     */
    public Integer getPrefectureId() {
        return prefectureId;
    }

    /**
     * prefectureIdを設定します。
     * 
     * @param prefectureId
     */
    public void setPrefectureId(Integer prefectureId) {
        this.prefectureId = prefectureId;
    }

    /**
     * areaを返します。
     * 
     * @param area
     */
    public String getArea() {
        return area;
    }

    /**
     * areaを設定します。
     * 
     * @param area
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * addressを返します。
     * 
     * @param address
     */
    public String getAddress() {
        return address;
    }

    /**
     * addressを設定します。
     * 
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * buildingを返します。
     * 
     * @param building
     */
    public String getBuilding() {
        return building;
    }

    /**
     * buildingを設定します。
     * 
     * @param building
     */
    public void setBuilding(String building) {
        this.building = building;
    }

    /**
     * telを返します。
     * 
     * @param tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * telを設定します。
     * 
     * @param tel
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * sexを返します。
     * 
     * @param sex
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * sexを設定します。
     * 
     * @param sex
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * birthdayを返します。
     * 
     * @param birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * birthdayを設定します。
     * 
     * @param birthday
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * lastLoginTimeを返します。
     * 
     * @param lastLoginTime
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * lastLoginTimeを設定します。
     * 
     * @param lastLoginTime
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * statusを返します。
     * 
     * @param status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * statusを設定します。
     * 
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * registTimeを返します。
     * 
     * @param registTime
     */
    public Date getRegistTime() {
        return registTime;
    }

    /**
     * registTimeを設定します。
     * 
     * @param registTime
     */
    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    /**
     * updateTimeを返します。
     * 
     * @param updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * updateTimeを設定します。
     * 
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * deleteFlgを返します。
     * 
     * @param deleteFlg
     */
    public Boolean isDeleteFlg() {
        return deleteFlg;
    }

    /**
     * deleteFlgを設定します。
     * 
     * @param deleteFlg
     */
    public void setDeleteFlg(Boolean deleteFlg) {
        this.deleteFlg = deleteFlg;
    }

    /**
     * versionを返します。
     * 
     * @param version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * versionを設定します。
     * 
     * @param version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * TPostListを返します。
     * 
     * @param TPostList
     */
    public List<TPost> getTPostList() {
        return TPostList;
    }

    /**
     * TPostListを設定します。
     * 
     * @param TPostList
     */
    public void setTPostList(List<TPost> TPostList) {
        this.TPostList = TPostList;
    }

    /**
     * TUserOnetimePassを返します。
     * 
     * @param TUserOnetimePass
     */
    public TUserOnetimePass getTUserOnetimePass() {
        return TUserOnetimePass;
    }

    /**
     * TUserOnetimePassを設定します。
     * 
     * @param TUserOnetimePass
     */
    public void setTUserOnetimePass(TUserOnetimePass TUserOnetimePass) {
        this.TUserOnetimePass = TUserOnetimePass;
    }

    /**
     * TUserOnetimeValidを返します。
     * 
     * @param TUserOnetimeValid
     */
    public TUserOnetimeValid getTUserOnetimeValid() {
        return TUserOnetimeValid;
    }

    /**
     * TUserOnetimeValidを設定します。
     * 
     * @param TUserOnetimeValid
     */
    public void setTUserOnetimeValid(TUserOnetimeValid TUserOnetimeValid) {
        this.TUserOnetimeValid = TUserOnetimeValid;
    }
}