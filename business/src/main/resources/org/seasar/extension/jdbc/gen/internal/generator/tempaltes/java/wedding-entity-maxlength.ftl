<#import "/lib.ftl" as lib>
<#if lib.copyright??>
${lib.copyright}
</#if>
<#if !lib.copyright??>
<#include "/copyright.ftl">
</#if>
<#if packageName??>
package ${packageName};
</#if>

/**
 * ${shortClassName}エンティティのMaxLength クラス
 *
 * 自動生成のため原則修正禁止!!
 * 
 * @author iseyoshitaka
 */
public class ${shortClassName} {

    /** 日付の共通maxlength */
    public static final int DATE = 10;

    /** 手入力日付の共通maxlength */
    public static final int MANUAL_ENTRY_DATE = 8;

    /** DBに存在しない価格入力の共通maxlength */
    public static final int PRICE = 8;

    /** 残り期間の共通maxlength */
    public static final int LIMIT_DATE = 2;
    
    /** オプション申請の掲載枠数の共通maxlength */
    public static final int OPTION_REQUEST_COUNT = 2;

    /** 郵便番号の共通maxlength */
    public static final int POST_CODE = 8;
    
    /** パスワードの共通maxlength */
    public static final int PASSWORD_MAX = 15;
    /** パスワードの共通minlength */
    public static final int PASSWORD_MIN = 8;

<#list attributeModelList as attr>
  <#if attr.length??>
    <#if attr.comment??>
    /** ${attr.comment} */
    </#if>
    public static final int ${attr.name?upper_case} = ${attr.length};
  </#if>  
  <#if attr.precision??>
    <#if attr.comment??>
    /** ${attr.comment} */
    </#if>
    public static final int ${attr.name?upper_case} = ${attr.precision};
  </#if>
</#list>
}